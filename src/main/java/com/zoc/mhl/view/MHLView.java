package com.zoc.mhl.view;

import com.zoc.mhl.domain.Employee;
import com.zoc.mhl.service.EmployeeService;
import com.zoc.mhl.utils.Utility;

public class MHLView {

    // 控制是否退出菜单
    private boolean loop = true;
    // 接收用户的选择
    private String key = "";
    // 定义EmployeeService属性
    private EmployeeService employeeService = new EmployeeService();

    public static void main(String[] args) {
        new MHLView().mainMenu();
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
                    // todo 数据库做判断
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
                                    // todo 显示餐桌状态
                                    System.out.println("显示餐桌状态");
                                    break;
                                case "2":
                                    // todo 预定餐桌
                                    System.out.println("预定餐桌");
                                    break;
                                case "3":
                                    // todo 显示所有菜品
                                    System.out.println("显示所有菜品");
                                    break;
                                case "4":
                                    // todo 点餐服务
                                    System.out.println("点餐服务");
                                    break;
                                case "5":
                                    // todo  查看账单
                                    System.out.println("查看账单");
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
