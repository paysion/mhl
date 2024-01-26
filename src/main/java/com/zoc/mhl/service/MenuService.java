package com.zoc.mhl.service;

import com.zoc.mhl.dao.MenuDao;
import com.zoc.mhl.domain.Menu;

import java.util.List;

public class MenuService {

    private MenuDao menuDao = new MenuDao();

    public List<Menu> list() {
        List<Menu> menus = menuDao.queryMulti("select * from menu", Menu.class);
        return menus;
    }

    public Menu getMenu(int id){
        return menuDao.querySingle("select * from menu where id=?",Menu.class,id);
    }
}
