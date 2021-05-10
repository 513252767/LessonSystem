package com.hung.dao;

import com.hung.pojo.Menu;
import com.hung.util.orm.annotations.Select;
import com.hung.util.spring.annotation.Repository;

import java.util.List;

/**
 * @author Hung
 */
@Repository("menuDao")
public interface MenuDao {
    /**
     * 查询menu
     * @param parentId
     * @param roleId
     * @return
     */
    @Select("select id,name,url,parentId from menu where parentId = ? and id in (select menuId from role_menu_id where roleId=?);")
    List<Menu> menuInfo(Integer parentId,Integer roleId);
}
