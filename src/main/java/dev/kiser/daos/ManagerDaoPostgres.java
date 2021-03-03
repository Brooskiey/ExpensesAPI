package dev.kiser.daos;

import dev.kiser.entities.Manager;
import dev.kiser.utils.ConnectionUtil;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ManagerDaoPostgres implements ManagerDaoIF {

    Logger logger = Logger.getLogger(EmployeeDaoPostgres.class);

    /**
     * get the manager based on the employee id
     *
     * @param empId employee id
     */
    @Override
    public Manager getManagerByID(int empId) {
        try (Connection conn = ConnectionUtil.createConnection()) {

            String sql = "select * from manager where employee_id = ?";


            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, empId);
            ResultSet rs = ps.executeQuery();

            rs.next();

            Manager manager = new Manager();
            manager.setEmpId(rs.getInt("employee_id"));
            manager.setManId(rs.getInt("manager_id"));


            logger.info("Get manager with employee ID " + empId);

            return manager;

        } catch (SQLException sqlException) {
            logger.error("Error in getting employee with id " + empId + "\n\t\t\t\t\t" + sqlException);
            return null;
        }
    }
}
