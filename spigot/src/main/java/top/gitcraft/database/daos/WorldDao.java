package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.WorldEntity;

import java.sql.SQLException;

public class WorldDao extends BaseDaoImpl<WorldEntity, Integer> {

    public WorldDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, WorldEntity.class);
    }

    public WorldEntity getWorldById(int id) throws SQLException {
        return queryForId(id);
    }
}
