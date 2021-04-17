package com.hung.service.impl;

import com.hung.dao.UserDao;
import com.hung.pojo.User;
import com.hung.service.UserService;
import com.hung.util.orm.sqlsession.SqlSession;
import com.hung.util.orm.sqlsession.defaults.DefaultSqlSession;

/**
 * @author Hung
 */
public class UserServiceImpl implements UserService {
    /**
     * 使用工厂创建sqlSession对象
     */
    SqlSession sqlSession = new DefaultSqlSession();
    /**
     * 使用SqlSession创建Dao接口的代理对象
     */
    UserDao userDao = (UserDao) sqlSession.getMapper(UserDao.class);

    /**
     * 根据id查询user
     *
     * @param uid
     * @return
     */
    @Override
    public User queryUserById(Integer uid) {
        return userDao.queryUserById(uid);
    }

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Override
    public boolean updateUser(User user) {
        int i = userDao.updateUser(user);
        return i > 0;
    }
}
