package dev.kiser.daos;

import dev.kiser.entities.Employee;

import java.util.Set;

public class EmployeeDaoPostgres implements EmployeeDaoIF{

    /**
     * get all employees
     *
     * @param empId
     */
    @Override
    public Set<Employee> getAllEmployees(int empId) {
        return null;
    }

    /**
     * get employee by id
     *
     * @param empId
     */
    @Override
    public Employee getEmployeeById(int empId) {
        return null;
    }

    /**
     * update the employee
     *
     * @param empId
     */
    @Override
    public Employee updateEmployee(int empId) {
        return null;
    }

    /**
     * delete the employee
     *
     * @param empId
     */
    @Override
    public boolean deleteEmployee(int empId) {
        return false;
    }
}
