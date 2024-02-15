package net.main;
import net.coreprotect.database.Database;
import org.bukkit.command.CommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.*;
import java.util.Objects;

import static org.bukkit.Bukkit.getLogger;

public class GCTables{
    public void tableInit() {
        getLogger().info("Checking if table exists...");

        try (Connection connection = Database.getConnection(true, 1000)) {
            if (connection != null) {
                DatabaseMetaData meta = connection.getMetaData();
                ResultSet resultSet = meta.getTables(null, null, "commitManagement", null);

                if (!resultSet.next()) {
                    // Tabelle existiert nicht, erstellen Sie die Tabelle
                    createTable((Connection) connection);
                } else {
                    getLogger().info("Table already exists!");
                }
                if (!checkIfColumnsExist(meta, "co_block", "commitID", "commitName")) {
                    // Spalten existieren nicht, fügen Sie sie hinzu
                    addColumns(connection, "co_block");
                } else {
                    getLogger().info("Columns already exist!");
                }
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTable(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Hier sollten Sie den Code zum Erstellen Ihrer Tabelle einfügen
            // Beispiel:
            String createTableQuery = "CREATE TABLE IF NOT EXISTS commitManagement (" +
                    "playerName VARCHAR(255)," +
                    "currentID INT," +
                    "highestID INT" +
                    ")";

            statement.executeUpdate(createTableQuery);

            getLogger().info("Table created!");
        }
    }
    private boolean checkIfColumnsExist(DatabaseMetaData meta, String tableName, String... columnNames) throws SQLException {
        ResultSet columns = meta.getColumns(null, null, tableName, null);

        while (columns.next()) {
            String columnName = columns.getString("COLUMN_NAME");
            for (String requiredColumnName : columnNames) {
                if (columnName.equalsIgnoreCase(requiredColumnName)) {
                    getLogger().info("Column " + requiredColumnName + " found!");
                    return true;
                }
            }
        }

        getLogger().info("Columns not found!");
        return false;
    }

    private void addColumns(Connection connection, String tableName) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Fügen Sie hier die Spalteninformationen hinzu
            statement.executeUpdate("ALTER TABLE " + tableName + " ADD COLUMN commitID INT");
            statement.executeUpdate("ALTER TABLE " + tableName + " ADD COLUMN commitName VARCHAR(255)");


            getLogger().info("Columns added to " + tableName + "!");
        }
    }
}

