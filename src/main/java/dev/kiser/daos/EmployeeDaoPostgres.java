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

public class EmployeeDaoPostgres implements EmployeeDaoIF {

    Logger logger = Logger.getLogger(EmployeeDaoPostgres.class);

    /**
     * get all employees
     */
    @Override
    public Set<Employee> getAllEmployees() {
        try (Connection conn = ConnectionUtil.createConnection()) {

            String sql = "select * from employee";

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            Set<Employee> employees = new HashSet<>();

            // while accounts exist
            while (rs.next()) {

                Employee employee = new Employee();
                employee.setEmpId(rs.getInt("employee_id"));
                employee.setfName(rs.getString("first_name"));
                employee.setlName(rs.getString("last_name"));
                employee.setEmail(rs.getString("email"));
                employee.setUsername(rs.getString("username"));
                employee.setPassword(rs.getString("em_password"));

                employees.add(employee);
            }

            logger.info("Get all employees");

            return employees;

        } catch (SQLException sqlException) {
            logger.error("Error in getting all employees\n\t\t\t\t\t" + sqlException);
            return null;
        }
    }

    /**
     * get employee by id
     *
     * @param empId    employee id
     * @param username employee username
     * @param password employee password
     */
    @Override
    public Employee getEmployeeById(int empId, String username, String password) {
        try (Connection conn = ConnectionUtil.createConnection()) {

            String sql = "select * from employee where employee_id = ? and username = ? and em_password = ?";


            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, empId);
            ps.setString(2, username);
            ps.setString(3, password);
            ResultSet rs = ps.executeQuery();

            rs.next();

            Employee employee = new Employee();
            employee.setEmpId(rs.getInt("employee_id"));
            employee.setfName(rs.getString("first_name"));
            employee.setlName(rs.getString("last_name"));
            employee.setEmail(rs.getString("email"));
            employee.setUsername(rs.getString("username"));
            employee.setPassword(rs.getString("em_password"));

            logger.info("Get employee with ID " + empId);

            return employee;

        } catch (SQLException sqlException) {
//            logger.debug(empId + " : " + username + " : " + password);
            logger.error("Error in getting employee with id " + empId + "\n\t\t\t\t\t" + sqlException);
            return null;
        }
    }

    /**
     * update the employee
     *
     * @param employee update employee
     */
    @Override
    public Employee updateEmployee(Employee employee) {
        try (Connection conn = ConnectionUtil.createConnection()) {

            String sql = "update employee set first_name = ?, last_name = ?, em_password = ?, username = ? where employee_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, employee.getfName());
            ps.setString(2, employee.getlName());
            ps.setString(3, employee.getPassword());
            ps.setString(4, employee.getUsername());
            ps.setInt(5, employee.getEmpId());
            ps.execute();

            logger.info("Updated employee #" + employee.getEmpId());

            return employee;

        } catch (SQLException sqlException) {
            logger.info("Error in updating employee with id " + employee.getEmpId() + "\n\t\t\t\t\t" + sqlException);
            return null;
        }
    }

    /**
     * delete the employee
     *
     * @param empId employee id
     */
    @Override
    public boolean deleteEmployee(int empId) {
        try (Connection conn = ConnectionUtil.createConnection()) {

            String sql = "delete from employee where employee_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, empId);
            ps.execute();

            logger.info("Delete employee #" + empId);

            return true;

        } catch (SQLException sqlException) {
            logger.info("Error in deleting employee with id " + empId + "\n\t\t\t\t\t" + sqlException);
            return false;
        }
    }
}
