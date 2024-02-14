package net.main;

import java.sql.*;

public class MySQL {
    final String username="mc";
    final String password="passwd";
    final String url="jdbc:mysql://172.17.0.2:3306/mcdb";
    public static Connection connection;

    public void openConnection() {
        try {
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connection established");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Connection closed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
