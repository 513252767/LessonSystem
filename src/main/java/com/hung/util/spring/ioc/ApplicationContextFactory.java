package com.hung.util.spring.ioc;

/**
 * @author Hung
 */
public class ApplicationContextFactory {

    static ApplicationContext applicationContext;

    public static ApplicationContext getApplication(){
        if (applicationContext==null){
            applicationContext=new ApplicationContext();
        }
        return applicationContext;
    }

}
