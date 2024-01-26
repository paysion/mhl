package com.zoc.mhl.service;

import com.zoc.mhl.dao.BillDao;
import com.zoc.mhl.domain.Bill;

import java.util.List;
import java.util.UUID;

public class BillService {

    private BillDao billDao = new BillDao();
    // 注入 MenuService 属性
    private MenuService menuService = new MenuService();
    // 注入 DiningTableService 属性
    private DiningTableService diningTableService = new DiningTableService();


    /**
     * 思考: 编写点餐的方法
     * 1. 生成账单
     * 2. 需要更新对应餐桌的状态
     * 3. 如果成功返回true, 否则返回false
     */
    public boolean orderMenu(int menuId,int nums,int diningTableId) {
        String billId = UUID.randomUUID().toString();
        // 账单金额
        double money = menuService.getMenu(menuId).getPrice()*nums;
        int update = billDao.update("insert into bill values(null,?,?,?,?,?,now(),'未结账')", billId, menuId, nums, money, diningTableId);
        if (update <= 0 ) {
            return false;
        }
        // 需要更新对应餐桌的状态
        return diningTableService.updateDiningTableState(diningTableId);
    }

    /**
     * 显示账单
     */
    public List<Bill> list() {
        List<Bill> bills = billDao.queryMulti("select * from bill", Bill.class);
        return bills;
    }

}
