package com.hung.dao;

import com.hung.pojo.User;
import com.hung.util.orm.annotations.Insert;
import com.hung.util.orm.annotations.Select;
import com.hung.util.orm.annotations.Update;

/**
 * @author Hung
 */
public interface UserDao {
    /**
     * 根据uid查询用户信息
     *
     * @param uid
     * @return
     */
    @Select("select * from user where id=?")
    User queryUserById(Integer uid);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Update("update user set nickName=#{nickName},gender=#{gender},birthday=#{birthday},desc=#{desc} where id=#{id}")
    Integer updateUser(User user);

    /**
     * 根据id查询昵称
     *
     * @param uid
     * @return
     */
    @Select("select nickName from user where id=?")
    String queryNickNameById(Integer uid);

    /**
     * 注册账号成功后顺便注册user以便维持统一id
     *
     * @param name
     * @return
     */
    @Insert("insert into user (id,nickName) value(default,?)")
    Integer registerUser(String name);
}
