package com.hung.service;

import com.hung.dao.UserDao;
import com.hung.pojo.Part;
import com.hung.service.serviceImpl.PartServiceImpl;
import com.hung.util.orm.sqlsession.SqlSession;
import com.hung.util.orm.sqlsession.defaults.DefaultSqlSession;
import com.hung.util.orm.sqlsession.defaults.ServiceFactory;
import org.junit.Test;

import java.util.List;

/**
 * @author Hung
 */
public class ServiceTest {
    @Test
    public void daoTest() {
        //使用工厂创建sqlSession对象
        SqlSession sqlSession = new DefaultSqlSession();
        //使用SqlSession创建Dao接口的代理对象
        UserDao userDao = (UserDao) sqlSession.getMapper(UserDao.class);
        String s = userDao.queryNickNameById(1);
        //使用代理对象执行方法
        //User user = userDao.queryUserById(1);
        System.out.println(s);
        //释放资源
        sqlSession.close();
    }

    @Test
    public void proxyTest() {
        ServiceFactory<PartServiceImpl> partService = new ServiceFactory<>(new PartServiceImpl());
        PartService service = partService.getService();
        List<Part> parts = service.queryAllParts();
        System.out.println(parts);
    }
}
