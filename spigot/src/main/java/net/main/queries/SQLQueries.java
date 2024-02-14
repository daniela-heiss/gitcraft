package net.main.queries;

import net.main.MySQL;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class SQLQueries {

    public void getUser() {
        String sql = "SELECT * FROM co_user";
        PreparedStatement stmt = null;
        try {
            stmt = MySQL.connection.prepareStatement(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet results = null;
        try {
            results = stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSetMetaData rsmd = null;
        try {
            rsmd = results.getMetaData();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int columnsNumber = 0;
        try {
            columnsNumber = rsmd.getColumnCount();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        while (true) {
            try {
                if (!results.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            for (int i = 1; i <= columnsNumber; i++) {
                if (i > 1) System.out.print(",  ");
                String columnValue = null;
                try {
                    columnValue = results.getString(i);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                try {
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
            System.out.println("");
        }
    }
}
