package com.zoc.mhl.service;

import com.zoc.mhl.dao.DiningTableDao;
import com.zoc.mhl.domain.DiningTable;

import java.util.List;

public class DiningTableService {

    private DiningTableDao diningTableDao = new DiningTableDao();

    public List<DiningTable> list(){
        return diningTableDao.queryMulti("select id,state from diningTable",DiningTable.class);
    }

    // 根据id获取对应餐桌对象
    public DiningTable getDingTableById(int orderId) {
        return diningTableDao.querySingle("select * from diningTable where id=?",DiningTable.class,orderId);
    }

    // 预定餐桌
    public boolean orderDiningTable(int orderId,String orderName,String orderTel) {
        int update = diningTableDao.update("update diningTable set state='已经预定',orderName=?,orderTel=? where id=?", orderName, orderTel, orderId);
        return update > 0;
    }

    // 更新餐桌状态为”就餐中“
    public boolean updateDiningTableState(int diningTableId){
        return diningTableDao.update("update diningTable set state='就餐中' where id=?",diningTableId) > 0;
    }

    // 结账完重置餐桌状态
    public boolean resetDiningTable(int diningTableId){
        return diningTableDao.update("update diningTable set state='空',orderName='',orderTel='' where id=?",diningTableId) > 0;
    }

}
