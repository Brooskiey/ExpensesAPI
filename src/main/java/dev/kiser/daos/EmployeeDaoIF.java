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
     * get employee by id
     */
    Employee getEmployeeById(int empId, String username, String password);

    /**
     * update the employee
     */
    Employee updateEmployee(Employee employee);

    /** delete the employee */
    boolean deleteEmployee(int empId);

}
