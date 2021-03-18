package dev.kiser.daos;

import dev.kiser.entities.Employee;
import dev.kiser.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

public class EmployeeDaoHibernate implements EmployeeDaoIF{
    Logger logger = Logger.getLogger(EmployeeDaoPostgres.class);

    /**
     * get all employees
     */
    @Override
    public Set<Employee> getAllEmployees() {
        return null;
    }

    /**
     * get employee by username and password
     *
     * @param username employee username
     * @param password employee password
     */
    @Override
    public Employee getEmployeeByName(String username, String password) {
        return null;
    }

    /**
     * get employee by id
     *
     * @param empId employee id
     */
    @Override
    public Employee getEmployeeById(int empId) {
        return null;
    }

    /**
     * update the employee
     *
     * @param employee update employee
     */
    @Override
    public Employee updateEmployee(Employee employee) {
        return null;
    }

    /**
     * delete the employee
     *
     * @param empId employee id
     */
    @Override
    public boolean deleteEmployee(int empId) {
        return false;
    }
}
