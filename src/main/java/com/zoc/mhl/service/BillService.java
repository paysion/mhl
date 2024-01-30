package com.zoc.mhl.service;

import com.zoc.mhl.dao.BillDao;
import com.zoc.mhl.domain.Bill;
import com.zoc.mhl.domain.DiningTable;

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
    public boolean orderMenu(int menuId, int nums, int diningTableId) {
        String billId = UUID.randomUUID().toString();
        // 账单金额
        double money = menuService.getMenu(menuId).getPrice() * nums;
        int update = billDao.update("insert into bill values(null,?,?,?,?,?,now(),'未结账')", billId, menuId, nums, money, diningTableId);
        if (update <= 0) {
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

    /**
     * 查看某个餐桌是否有未结账的账单
     * [为什么要限制只返回一条语句呢？一张餐桌可以有多个账单啊]
     */
    public boolean hasPayBillByDiningTableId(int diningTableId) {
        Bill bill = billDao.querySingle("select * from bill where diningTableId=? and state='未结账' limit 0, 1", Bill.class, diningTableId);
        return bill != null;
    }

    /**
     * 完成结账[如果餐桌存在，并且该餐桌有未结账的账单]
     * 如果成功，返回true, 失败返回 false
     */
    public boolean payBill(int diningTableId, String payMode) {
        int update = billDao.update("update bill set state=? where diningTableId=?", payMode, diningTableId);
        if (update <=0 ) {
            return false;
        }
        // 结账重置餐桌属性
        if (!diningTableService.resetDiningTable(diningTableId)) {
            return false;
        }
        return true;
    }


}
