package com.bank.app.service.EmployeeService;

import com.bank.app.model.Employee;

public interface EmployeeServiceImpl {
    public Employee getEmployee(String employeeID);

    public void addEmployee(Employee infoEmployee);

}
