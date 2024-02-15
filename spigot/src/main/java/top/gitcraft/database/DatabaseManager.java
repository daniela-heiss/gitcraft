package top.gitcraft.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.daos.BlockDao;

import java.sql.SQLException;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/minecraft";
    private static final String DATABASE_USERNAME = "mc";
    private static final String DATABASE_PASSWORD = "passwd";

    private ConnectionSource connectionSource;

    public void initializeDatabase() throws SQLException {
        connectionSource = new JdbcConnectionSource(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

    }

    public void closeConnection() throws Exception {
        if (connectionSource != null) {
            connectionSource.close();
        }
    }

    public BlockDao getBlockDao() throws SQLException {
        return new BlockDao(connectionSource);
    }
}
