package com.hung.service;

import com.hung.pojo.Account;
import com.hung.pojo.Menu;

import java.util.List;

/**
 * @author Hung
 */
public interface AccountService {
    /**
     * 登录
     *
     * @param account
     * @return
     */
    Account loginAccount(Account account);

    /**
     * 注册
     *
     * @param account
     * @return
     */
    Boolean registerAccount(Account account);

    /**
     * 更新账户信息
     *
     * @param account
     * @return
     */
    boolean updateAccount(Account account);

    /**
     * 查询菜单
     * @param parentId
     * @param roleId
     * @return
     */
    List<Menu> menuInfo(Integer parentId,Integer roleId);
}
