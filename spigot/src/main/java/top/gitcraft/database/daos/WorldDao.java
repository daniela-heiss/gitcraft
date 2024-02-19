package top.gitcraft.database.daos;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.MaterialMapEntity;
import top.gitcraft.database.entities.WorldEntity;

import java.sql.SQLException;

public class WorldDao {

    private final Dao<WorldEntity, Integer> worldDao;

    public WorldDao(ConnectionSource connectionSource) throws SQLException {
        worldDao = DaoManager.createDao(connectionSource, WorldEntity.class);
    }

    public String getWorldById(int id) throws SQLException {
        return worldDao.queryForId(id).world;
    }
}
