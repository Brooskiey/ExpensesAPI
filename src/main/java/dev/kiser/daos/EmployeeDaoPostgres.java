package dev.kiser.daos;

import dev.kiser.entities.Employee;

import java.util.Set;

public class EmployeeDaoPostgres implements EmployeeDaoIF{

    /**
     * get all employees
     */
    @Override
    public Set<Employee> getAllEmployees() {
        return null;
    }

    /**
     * get employee by id
     *
     * @param empId:    employee id
     * @param username: employee username
     * @param password: employee password
     */
    @Override
    public Employee getEmployeeById(int empId, String username, String password) {
        return null;
    }

    /**
     * update the employee
     *
     * @param empId: employee id
     */
    @Override
    public Employee updateEmployee(int empId) {
        return null;
    }

    /**
     * delete the employee
     *
     * @param empId: employee id
     */
    @Override
    public boolean deleteEmployee(int empId) {
        return false;
    }
}
