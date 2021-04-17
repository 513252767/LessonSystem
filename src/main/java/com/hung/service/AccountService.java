package com.hung.service;

import com.hung.pojo.Account;

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
    Integer registerAccount(Account account);

    /**
     * 更新账户信息
     *
     * @param account
     * @return
     */
    boolean updateAccount(Account account);
}
