package top.gitcraft.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import io.github.cdimascio.dotenv.Dotenv;
import top.gitcraft.database.daos.*;
import top.gitcraft.database.entities.SaveEntity;

import java.sql.SQLException;

public class DatabaseManager {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String DATABASE_URL = dotenv.get("DATABASE_URL");
    private static final String DATABASE_USERNAME = dotenv.get("DATABASE_USERNAME");
    private static final String DATABASE_PASSWORD = dotenv.get("DATABASE_PASSWORD");

    private static DatabaseManager instance;
    private ConnectionSource connectionSource;

    // Private constructor to prevent instantiation from outside
    private DatabaseManager() throws SQLException {
        initializeDatabase();
    }

    // Static method to get the singleton instance
    public static synchronized DatabaseManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void initializeDatabase() throws SQLException {
        connectionSource =
                new JdbcConnectionSource(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

        TableUtils.createTableIfNotExists(connectionSource, SaveEntity.class);
        //        TableUtils.createTableIfNotExists(connectionSource, WorldMapEntity.class);
    }

    private void closeConnection() throws Exception {
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

    public SaveDao getSaveDao() throws SQLException {
        return new SaveDao(connectionSource);
    }

    public WorldDao getWorldDao() throws SQLException {
        return new WorldDao(connectionSource);
    }

    public BlockDataMapDao getBlockDataMapDao() throws SQLException {
        return new BlockDataMapDao(connectionSource);
    }

    public WorldMapDao getWorldMapDao() throws SQLException {
        return new WorldMapDao(connectionSource);
    }
}
