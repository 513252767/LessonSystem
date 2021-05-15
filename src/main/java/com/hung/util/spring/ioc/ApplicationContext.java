package com.hung.util.spring.ioc;


import com.hung.util.spring.annotation.*;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Hung
 */
public class ApplicationContext {
    private Class<?> configClass;
    final String SINGLETON = "singleton";
    final String ENDWITH = ".class";
    String[] paths = {"com/hung/dao", "com/hung/util/spring/ioc", "com/hung/service/impl", "com/hung/servlet","com/hung/view/login","com/hung/util"};

    Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);

    Map<String, Object> singletonBeanMap = new ConcurrentHashMap<>(256);

    List<BeanPostProcessor> beanPostProcessorList = new ArrayList<>();

    public ApplicationContext() {
        //获取类加载器
        ClassLoader classLoader = ApplicationContext.class.getClassLoader();
        for (String path : paths) {
            URL resource = classLoader.getResource(path);
            File file = new File(resource.getFile());
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                //遍历文件
                for (File f : files) {
                    fileSet(classLoader, f);
                }
            }
        }

        //加入单例beanMap
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            String beanName = entry.getKey();
            BeanDefinition beanDefinition = entry.getValue();
            if ("singleton".equals(beanDefinition.getScope())) {
                Object bean = createBean(beanName, beanDefinition);
                singletonBeanMap.put(beanName, bean);
            }
        }
    }

    /**
     * 对获取的文件进行配置
     *
     * @param classLoader 类加载器
     * @param file        文件
     */
    private void fileSet(ClassLoader classLoader, File file) {
        String filePath = file.getAbsolutePath();
        //找到以class结尾的文件
        if (filePath.endsWith(ENDWITH)) {
            String clazzName = filePath.substring(filePath.indexOf("com"), filePath.indexOf(ENDWITH));
            clazzName = clazzName.replace("\\", ".");
            try {
                Class<?> clazz = classLoader.loadClass(clazzName);
                //Component组件添加
                if (clazz.isAnnotationPresent(Component.class) || clazz.isAnnotationPresent(Controller.class) ||
                        clazz.isAnnotationPresent(Service.class) || clazz.isAnnotationPresent(Repository.class)) {

                    //对beanPostProcessor配置
                    if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                        BeanPostProcessor instance = (BeanPostProcessor) clazz.getDeclaredConstructor().newInstance();
                        beanPostProcessorList.add(instance);
                    }

                    BeanDefinition beanDefinition = new BeanDefinition();
                    //对beanDefinition属性进行配置
                    String beanName = beanDefinitionSet(clazz, beanDefinition);

                    //加入bean定义map
                    beanDefinitionMap.put(beanName, beanDefinition);
                }
            } catch (ClassNotFoundException | InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    @Nullable
    private String beanDefinitionSet(Class<?> clazz, BeanDefinition beanDefinition) {
        String beanName = null;
        if (clazz.isAnnotationPresent(Component.class)) {
            Component componentAnnotation = clazz.getDeclaredAnnotation(Component.class);
            beanName = componentAnnotation.value();
            beanDefinition.setClazz(clazz);
        } else if (clazz.isAnnotationPresent(Controller.class)) {
            Controller controllerAnnotation = clazz.getDeclaredAnnotation(Controller.class);
            beanName = controllerAnnotation.value();
            beanDefinition.setClazz(clazz);
        } else if (clazz.isAnnotationPresent(Service.class)) {
            Service serviceAnnotation = clazz.getDeclaredAnnotation(Service.class);
            beanName = serviceAnnotation.value();
            beanDefinition.setClazz(clazz);
        } else if (clazz.isAnnotationPresent(Repository.class)) {
            Repository repositoryAnnotation = clazz.getDeclaredAnnotation(Repository.class);
            beanName = repositoryAnnotation.value();
            beanDefinition.setClazz(clazz);
        }

        //Scope配置
        if (clazz.isAnnotationPresent(Scope.class)) {
            Scope scopeAnnotation = clazz.getDeclaredAnnotation(Scope.class);
            beanDefinition.setScope(scopeAnnotation.value());
        } else {
            beanDefinition.setScope("singleton");
        }
        return beanName;
    }

    public Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object instance = null;
        try {
            Class<?> clazz = beanDefinition.getClazz();
            if (!clazz.isInterface()) {
                instance = clazz.newInstance();
            }


            //依赖注入
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Autowired.class)) {
                    Object bean = getBean(field.getName());
                    //如果没有这个bean，先创建这个bean的单例
                    if (bean == null) {
                        singletonBeanMap.put(field.getName(),createBean(field.getName(), beanDefinitionMap.get(field.getName())));
                        bean = getBean(field.getName());
                    }
                    field.setAccessible(true);
                    field.set(instance, bean);
                }
            }

            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            //初始化前
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessBeforeInitialization(instance, beanName, clazz);
            }

            //初始化
            if (instance instanceof InitializingBean) {
                try {
                    ((InitializingBean) instance).afterPropertiesSet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            //初始化配置
            for (Method method : clazz.getMethods()) {
                if (method.isAnnotationPresent(PostConstruct.class)) {
                    method.invoke(instance);
                }
            }

            //初始化后
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorList) {
                instance = beanPostProcessor.postProcessAfterInitialization(instance, beanName, clazz);
            }

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return instance;
    }

    /**
     * 获取bean
     *
     * @param beanName
     * @return bean单例
     */
    public Object getBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (SINGLETON.equals(beanDefinition.getScope())) {
                return singletonBeanMap.get(beanName);
            } else {
                return createBean(beanName, beanDefinition);
            }
        } else {
            //不存在这个bean
            throw new NullPointerException("不存在这个bean");
        }
    }

    public Class<?> getConfigClass() {
        return configClass;
    }

    public void setConfigClass(Class<?> configClass) {
        this.configClass = configClass;
    }
}
