package com.hung.dao;

import com.hung.pojo.Account;
import com.hung.util.orm.annotations.Insert;
import com.hung.util.orm.annotations.Select;
import com.hung.util.orm.annotations.Update;
import com.hung.util.spring.annotation.Repository;


/**
 * @author Hung
 */
@Repository("accountDao")
public interface AccountDao {
    /**
     * 登录
     *
     * @param account
     * @return
     */
    @Select("select * from account where name = #{name} and password = #{password}")
    Account loginAccount(Account account);

    /**
     * 注册
     *
     * @param account
     * @return
     */
    @Insert("insert into account(id,NAME,PASSWORD,roleId) value(default,#{name},#{password},1)")
    Integer registerAccount(Account account);

    /**
     * 修改信息
     *
     * @param account
     * @return
     */
    @Update("update account set password =#{password} where name=#{name}")
    Integer updateAccount(Account account);
}
