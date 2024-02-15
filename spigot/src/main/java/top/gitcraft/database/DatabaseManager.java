package top.gitcraft.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import io.github.cdimascio.dotenv.Dotenv;

import sun.tools.jconsole.Tab;
import top.gitcraft.database.daos.*;
import top.gitcraft.database.entities.CommitEntity;
import top.gitcraft.database.entities.CommitManagementEntity;

import java.sql.SQLException;

public class DatabaseManager {

    // Add .env File to minecraft Folder
    /* .env Structure
        DATABASE_URL = "jdbc:mysql://IP-ADDRESS:3306/DA_NAME"
        DATABASE_USERNAME = "USERNAME"
        DATABASE_PASSWORD = "PASSWORD"
    */
    static Dotenv dotenv = Dotenv.load();
    private static final String DATABASE_URL = dotenv.get("DATABASE_URL");
    private static final String DATABASE_USERNAME = dotenv.get("DATABASE_USERNAME");
    private static final String DATABASE_PASSWORD = dotenv.get("DATABASE_PASSWORD");

    private ConnectionSource connectionSource;

    public void initializeDatabase() throws SQLException {
        connectionSource = new JdbcConnectionSource(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

        TableUtils.createTableIfNotExists(connectionSource, CommitManagementEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, CommitEntity.class);
    }

    public void closeConnection() throws Exception {
        if (connectionSource != null) {
            connectionSource.close();
        }
    }

    public BlockDao getBlockDao() throws SQLException {
        return new BlockDao(connectionSource);
    }

    public UserDao getUserDao() throws SQLException {
        return new UserDao(connectionSource);
    }

    public MaterialMapDao getMaterialMapDao() throws SQLException {
        return new MaterialMapDao(connectionSource);
    }

    public CommitManagementDao getCommitManagementDao() throws SQLException {
        return new CommitManagementDao(connectionSource);
    }

    public CommitDao getCommitDao() throws SQLException {
        return new CommitDao(connectionSource);
    }
}
