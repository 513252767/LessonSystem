package com.hung.service.impl;

import com.hung.dao.AccountDao;
import com.hung.dao.MenuDao;
import com.hung.dao.UserDao;
import com.hung.pojo.Account;
import com.hung.pojo.Menu;
import com.hung.service.AccountService;
import com.hung.util.aes.AesUtil;
import com.hung.util.spring.annotation.Autowired;
import com.hung.util.spring.annotation.Service;

import java.util.List;

/**
 * @author Hung
 */
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountDao accountDao;
    @Autowired
    UserDao userDao;
    @Autowired
    MenuDao menuDao;

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
    public Boolean registerAccount(Account account) {
        account.setPassword(AesUtil.encryptStr(account.getPassword(),account.getName()));
        int flag = 0;
        if (accountDao.registerAccount(account) > 0) {
            //注册成功,顺便注册用户
            if (userDao.registerUser() > 0) {
                flag = 1;
            }
        }
        return flag>0;
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
        return accountDao.updateAccount(account) > 0;
    }

    /**
     * 查询菜单
     *
     * @param roleId
     * @return
     */
    @Override
    public List<Menu> menuInfo(Integer parentId,Integer roleId) {
        return menuDao.menuInfo(parentId,roleId);
    }
}
