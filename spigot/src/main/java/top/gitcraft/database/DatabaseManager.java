package top.gitcraft.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import io.github.cdimascio.dotenv.Dotenv;
import top.gitcraft.GitCraft;
import top.gitcraft.database.daos.*;
import top.gitcraft.database.entities.SaveEntity;

import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseManager {
    private static final Logger logger = GitCraft.getPlugin(GitCraft.class).getLogger();
    private static final Dotenv dotenv = Dotenv.load();

    private static final String DATABASE_URL = dotenv.get("DATABASE_URL");
    private static final String DATABASE_USERNAME = dotenv.get("DATABASE_USERNAME");
    private static final String DATABASE_PASSWORD = dotenv.get("DATABASE_PASSWORD");
    private static DatabaseManager instance;
    private ConnectionSource connectionSource;

    private DatabaseManager() throws SQLException {
        initializeDatabase();
    }

    public static synchronized DatabaseManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    boolean isDatabaseAvailable;

    private void initializeDatabase() {
        try {
            connectionSource =
                    new JdbcConnectionSource(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            TableUtils.createTableIfNotExists(connectionSource, SaveEntity.class);
            isDatabaseAvailable = true;
        } catch (SQLException e) {
            isDatabaseAvailable = false;
            e.printStackTrace();
        }
    }

    public boolean isDatabaseAvailable() {
        return isDatabaseAvailable;
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


    public SaveDao getSaveDao() throws SQLException {
        return new SaveDao(connectionSource);
    }

    public WorldDao getWorldDao() throws SQLException {
        return new WorldDao(connectionSource);
    }

    public MaterialMapDao getMaterialMapDao() throws SQLException {
        return new MaterialMapDao(connectionSource);
    }

    public BlockDataMapDao getBlockDataMapDao() throws SQLException {
        return new BlockDataMapDao(connectionSource);
    }

    public ArtMapDao getArtMapDao() throws SQLException {
        return new ArtMapDao(connectionSource);
    }
}
