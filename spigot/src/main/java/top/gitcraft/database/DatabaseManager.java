package top.gitcraft.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import top.gitcraft.database.daos.MaterialMapDao;
import top.gitcraft.database.daos.PlayerDao;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.entities.PlayerEntity;

import java.sql.SQLException;

public class DatabaseManager {
    private static final String DATABASE_URL = "jdbc:mysql://172.17.0.2:3306/mcdb";
    private static final String DATABASE_USERNAME = "mc";
    private static final String DATABASE_PASSWORD = "passwd";

    private ConnectionSource connectionSource;

    public void initializeDatabase() throws SQLException {
        connectionSource = new JdbcConnectionSource(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
        // Create tables if they don't exist
        TableUtils.createTableIfNotExists(connectionSource, PlayerEntity.class);
    }

    public void closeConnection() throws Exception {
        if (connectionSource != null) {
            connectionSource.close();
        }
    }

    public PlayerDao getPlayerDao() throws SQLException {
        return new PlayerDao(connectionSource);
    }

    public UserDao getUserDao() throws  SQLException {
        return new UserDao(connectionSource);
    }

    public MaterialMapDao getMaterialMapDao() throws SQLException {
        return new MaterialMapDao(connectionSource);
    }
}
