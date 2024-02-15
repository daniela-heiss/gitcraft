package top.gitcraft.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import top.gitcraft.database.daos.CommitManagementDao;
import top.gitcraft.database.daos.MaterialMapDao;

import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.entities.CommitManagementEntity;

import java.sql.SQLException;

public class DatabaseManager {
    //private static final String DATABASE_URL = "jdbc:mysql://172.17.0.2:3306/mcdb";
   // private static final String DATABASE_USERNAME = "mc";
   private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/database";
     private static final String DATABASE_USERNAME = "root";
    private static final String DATABASE_PASSWORD = "passwd";

    private ConnectionSource connectionSource;

    public void initializeDatabase() throws SQLException {
        connectionSource = new JdbcConnectionSource(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

        TableUtils.createTableIfNotExists(connectionSource, CommitManagementEntity.class);
    }

    public void closeConnection() throws Exception {
        if (connectionSource != null) {
            connectionSource.close();
        }
    }

    public BlockDao getBlockDao() throws SQLException {
        return new BlockDao(connectionSource);
    }

    public UserDao getUserDao() throws  SQLException {
        return new UserDao(connectionSource);
    }

    public MaterialMapDao getMaterialMapDao() throws SQLException {
        return new MaterialMapDao(connectionSource);
    }

    public CommitManagementDao getCommitManagementDao() throws SQLException {
        return new CommitManagementDao(connectionSource);
    }
}
