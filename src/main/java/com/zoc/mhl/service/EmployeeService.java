package com.zoc.mhl.service;

import com.zoc.mhl.dao.EmployeeDao;
import com.zoc.mhl.domain.Employee;

public class EmployeeService {

    // 定义一个 EmployeeDao属性
    private EmployeeDao employeeDao = new EmployeeDao();

    // 方法，根据empId和pwd返回EmployeeDao对象
    public Employee getEmployee(String empId,String pwd) {

        Employee employee = employeeDao.querySingle("select * from employee where empId=? and pwd=md5(?)", Employee.class, empId, pwd);
        return employee;
    }
}
