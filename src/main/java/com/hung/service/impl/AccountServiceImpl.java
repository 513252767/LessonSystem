package com.hung.service.impl;

import com.hung.dao.AccountDao;
import com.hung.dao.UserDao;
import com.hung.pojo.Account;
import com.hung.service.AccountService;
import com.hung.util.aes.AesUtil;
import com.hung.util.orm.sqlsession.SqlSession;
import com.hung.util.orm.sqlsession.defaults.DefaultSqlSession;

/**
 * @author Hung
 */
public class AccountServiceImpl implements AccountService {
    /**
     * 使用工厂创建sqlSession对象
     */
    SqlSession sqlSession = new DefaultSqlSession();
    /**
     * 使用SqlSession创建Dao接口的代理对象
     */
    AccountDao accountDao = (AccountDao) sqlSession.getMapper(AccountDao.class);
    UserDao userDao = (UserDao) new DefaultSqlSession<>().getMapper(UserDao.class);

    /**
     * 登录
     *
     * @param account
     * @return
     */
    @Override
    public Account loginAccount(Account account) {
        String encryptStr = AesUtil.encryptStr(account.getPassword(), account.getName());
        account.setPassword(encryptStr);
        Account loginAccount = accountDao.loginAccount(account);
        if (loginAccount!=null){
            String setPassword = AesUtil.decryptStr(loginAccount.getPassword(), loginAccount.getName());
            loginAccount.setPassword(setPassword);
        }
        return loginAccount;
    }

    /**
     * 注册
     *
     * @param account
     * @return
     */
    @Override
    public Integer registerAccount(Account account) {
        account.setPassword(AesUtil.encryptStr(account.getPassword(),account.getName()));
        int flag = 0;
        if (accountDao.registerAccount(account) > 0) {
            //注册成功,顺便注册用户
            if (userDao.registerUser(account.getId()) > 0) {
                flag = 1;
            }
        }
        return flag;
    }

    /**
     * 更新账户信息
     *
     * @param account
     * @return
     */
    @Override
    public boolean updateAccount(Account account) {
        account.setPassword(AesUtil.encryptStr(account.getPassword(),account.getName()));
        int i = accountDao.updateAccount(account);
        return i > 0;
    }
}
