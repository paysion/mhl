package com.zoc.mhl.view;

import com.zoc.mhl.domain.Bill;
import com.zoc.mhl.domain.DiningTable;
import com.zoc.mhl.domain.Employee;
import com.zoc.mhl.domain.Menu;
import com.zoc.mhl.service.BillService;
import com.zoc.mhl.service.DiningTableService;
import com.zoc.mhl.service.EmployeeService;
import com.zoc.mhl.service.MenuService;
import com.zoc.mhl.utils.Utility;

import java.util.List;

public class MHLView {

    // 控制是否退出菜单
    private boolean loop = true;
    // 接收用户的选择
    private String key = "";
    // 定义EmployeeService属性
    private EmployeeService employeeService = new EmployeeService();
    // 定义DiningTableService属性
    private DiningTableService diningTableService = new DiningTableService();
    // 定义MenuService属性
    private MenuService menuService = new MenuService();
    // 定义BillService属性
    private BillService billService = new BillService();

    public static void main(String[] args) {
        new MHLView().mainMenu();
    }
    // 显示餐桌状态
    public void listDiningTable() {
        List<DiningTable> list = diningTableService.list();
        System.out.println("\n餐桌编号\t\t餐桌状态");
        for (DiningTable diningTable : list) {
            System.out.println(diningTable.getId()+"\t\t    "+diningTable.getState());
        }
        System.out.println("==============显示完毕============");
    }

    // 预定餐桌
    public void orderDiningTable() {
        System.out.println("==============预定餐桌============");
        System.out.print("请选择要预定的餐桌编号(-1退出): ");
        int oderId = Utility.readInt(1);
        if (oderId == -1) {
            System.out.println("==============取消预订餐桌============");
            return;
        }
        char key = Utility.readConfirmSelection();
        if (key == 'Y') {
            DiningTable diningTable = diningTableService.getDingTableById(oderId);
            if (!("空".equals(diningTable.getState()))) {
                System.out.println("==============该餐桌已经预定或者就餐中============");
                return;
            }
            if (diningTable == null){
                System.out.println("==============预订餐桌不存在============");
                return;
            }

            // 接收预定信息
            System.out.print("预定人的名字: ");
            String orderName = Utility.readString(50);
            System.out.print("预定人的电话: ");
            String orderTel = Utility.readString(50);

            //更新餐桌状态
            if (diningTableService.orderDiningTable(oderId, orderName, orderTel)) {
                System.out.println("==============预订餐桌成功============");
            } else {
                System.out.println("==============预订餐桌失败============");
            }
        } else {
            System.out.println("==============取消预订餐桌============");
        }
    }

    //显示所有菜品
    public void listMenu() {
        List<Menu> list = menuService.list();
        System.out.println("\n菜品编号\t\t菜品名\t\t类别\t\t价格");
        for (Menu menu : list) {
            System.out.println(menu);
        }
        System.out.println("==============显示完毕============");
    }

    // 点餐服务
    public void orderMenu() {
        System.out.println("==============点餐服务============");
        System.out.print("请输入点餐的桌号(-1退出): ");
        int orderDiningTableId = Utility.readInt();
        if (orderDiningTableId == -1) {
            System.out.println("==============取消点餐============");
            return;
        }
        System.out.print("请输入点餐的菜品号(-1退出): ");
        int menuId =  Utility.readInt();
        if (menuId == -1) {
            System.out.println("==============取消点餐============");
            return;
        }
        System.out.print("请输入点餐的菜品量(-1退出): ");
        int nums = Utility.readInt(); // 这里对数量做一个限制是不是会更好
        if (nums == -1) {
            System.out.println("==============取消点餐============");
            return;
        }

        // 验证餐桌号是否存在.
        if (diningTableService.getDingTableById(orderDiningTableId) == null){
            System.out.println("==============餐桌不存在============");
            return;
        }
        //验证菜品编号
        if (menuService.getMenu(menuId) == null) {
            System.out.println("==============菜品不存在============");
            return;
        }
        //点餐
        if (billService.orderMenu(menuId,nums,orderDiningTableId)) {
            System.out.println("==============点餐成功============");
            return;
        }
    }
    
    public void listBill() {
        List<Bill> bills = billService.list();
        System.out.println("\n编号\t\t菜品号\t\t菜品量\t\t金额\t\t桌号\t\t日期\t\t\t\t\t\t\t状态");
        for (Bill bill : bills) {
            System.out.println(bill);
        }
    }

    // 显示主菜单
    public void mainMenu() {
        while (loop) {
            System.out.println("\n===============满汉楼================");
            System.out.println("\t\t 1 登录满汉楼");
            System.out.println("\t\t 2 退出满汉楼");
            System.out.print("请输入你的选择: ");
            key = Utility.readString(1);
            switch (key){
                case "1":
                    System.out.print("输入员工号: ");
                    String empId = Utility.readString(50);
                    System.out.print("输入密  码: ");
                    String pwd = Utility.readString(50);
                    Employee employee = employeeService.getEmployee(empId, pwd);
                    if (employee != null) {
                        System.out.println("===============登录成功["+employee.getName()+"]================\n");
                        //显示二级菜单, 这里二级菜单是循环操作，所以做成while
                        while(loop) {
                            System.out.println("\n===============满汉楼(二级菜单)================");
                            System.out.println("\t\t 1 显示餐桌状态");
                            System.out.println("\t\t 2 预定餐桌");
                            System.out.println("\t\t 3 显示所有菜品");
                            System.out.println("\t\t 4 点餐服务");
                            System.out.println("\t\t 5 查看账单");
                            System.out.println("\t\t 6 结账");
                            System.out.println("\t\t 9 退出满汉楼");
                            System.out.print("请输入你的选择: ");
                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    listDiningTable();
                                    break;
                                case "2":
                                    // 预定餐桌
                                    orderDiningTable();
                                    break;
                                case "3":
                                    // 显示所有菜品
                                    listMenu();
                                    break;
                                case "4":
                                    // 点餐服务
                                    orderMenu();
                                    break;
                                case "5":
                                    // 查看账单
                                    listBill();
                                    break;
                                case "6":
                                    // todo 结账
                                    System.out.println("结账");
                                    break;
                                case "9":
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("你的输入有误，请重新输入");
                                    break;
                            }
                        }
                    } else {
                        System.out.println("===============登录失败================");
                    }
                    break;
                case "2":
                    // 退出登录
                    loop = false;
                    break;
                default:
                    System.out.println("你输入有误，请重新输入.");
            }
        }
    }
}
