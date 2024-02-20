package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.EntityEntity;

import java.sql.SQLException;

public class EntityDao extends BaseDaoImpl<EntityEntity, Integer> {

    public EntityDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, EntityEntity.class);
    }

    public EntityEntity getEntityById(int id) throws SQLException {
        return queryForId(id);
    }
}
