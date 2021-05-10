package com.hung.service.impl;

import com.hung.dao.UserDao;
import com.hung.pojo.User;
import com.hung.service.UserService;
import com.hung.util.spring.annotation.Autowired;
import com.hung.util.spring.annotation.Service;

/**
 * @author Hung
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

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

    /**
     * 根据id查询昵称
     *
     * @param userId
     * @return
     */
    @Override
    public String queryNickNameById(Integer userId) {
        return userDao.queryNickNameById(userId);
    }

}
