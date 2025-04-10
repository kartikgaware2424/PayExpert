package org.example.Util;

import org.example.Exceptions.DatabaseConnectionException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ConnectionHelper {
    public static Connection getConnection() throws SQLException, ClassNotFoundException, DatabaseConnectionException {
        try {
            ResourceBundle rb = ResourceBundle.getBundle("db");
            String driver = rb.getString("driver");
            String url = rb.getString("url");
            String user = rb.getString("user");
            String pwd = rb.getString("password");

            Class.forName(driver);
            return DriverManager.getConnection(url, user, pwd);

        } catch (SQLException | ClassNotFoundException e) {
            throw new DatabaseConnectionException("Failed to establish database connection.", e);
        }
    }

}
