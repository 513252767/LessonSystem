package com.hung.dao;

import com.hung.pojo.User;
import com.hung.util.orm.annotations.Insert;
import com.hung.util.orm.annotations.Select;
import com.hung.util.orm.annotations.Update;
import com.hung.util.spring.annotation.Repository;

/**
 * @author Hung
 */
@Repository("userDao")
public interface UserDao {
    /**
     * 根据uid查询用户信息
     *
     * @param uid
     * @return
     */
    @Select("select * from user where accountId=?;")
    User queryUserById(Integer uid);

    /**
     * 更新用户信息
     *
     * @param user
     * @return
     */
    @Update("update user set name=#{name},gender=#{gender},major=#{major},team=#{team},introduction=#{introduction} where id=#{id};")
    Integer updateUser(User user);

    /**
     * 根据id查询昵称
     *
     * @param userId
     * @return
     */
    @Select("select name from user where id=?;")
    String queryNickNameById(Integer userId);

    /**
     * 注册账号成功后顺便注册user以便维持统一id
     *
     * @param accountId
     * @return
     */
    @Insert("insert into user (id) values (default);")
    Integer registerUser();
}
