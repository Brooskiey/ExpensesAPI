package dev.kiser.daos;

import dev.kiser.entities.Employee;

import java.util.Set;

public interface EmployeeDaoIF {

    // No create

    /**
     * get all employees
     */
    Set<Employee> getAllEmployees();

    /**
     * get employee by username and password
     */
    Employee getEmployeeByName(String username, String password);

    /**
     * get employee by id
     */
    Employee getEmployeeById(int empId);

    /**
     * update the employee
     */
    Employee updateEmployee(Employee employee);

    /**
     * delete the employee
     */
    boolean deleteEmployee(int empId);

}
