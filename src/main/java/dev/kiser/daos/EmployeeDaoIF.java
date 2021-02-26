package dev.kiser.daos;

import dev.kiser.entities.Employee;

import java.util.Set;

public interface EmployeeDaoIF {

    // No create

    /** get all employees */
    Set<Employee> getAllEmployees(int empId);

    /** get employee by id */
    Employee getEmployeeById(int empId);

    /** update the employee */
    Employee updateEmployee(int empId);

    /** delete the employee */
    boolean deleteEmployee(int empId);

}
