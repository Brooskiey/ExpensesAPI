package dev.kiser.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

    public static Connection createConnection() {

        // it is really bad practice to store any sensitive information in your code
        // environment variables key value pairs on your computer you can make whenever
        String details = System.getenv("CONN_DETAILS");

        try {
            Connection conn = DriverManager.getConnection(details);// a factory. pass in string details for any type of database
            // anywhere and the driverManager factory will give you back a connection implementation specifically for postgres
            return conn;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }

}
