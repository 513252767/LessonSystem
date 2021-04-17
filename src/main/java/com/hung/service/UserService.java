package com.hung.service;

import com.hung.pojo.User;

/**
 * @author Hung
 */
public interface UserService {
    /**
     * 根据id查询user
     *
     * @param uid
     * @return
     */
    User queryUserById(Integer uid);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    boolean updateUser(User user);
}
