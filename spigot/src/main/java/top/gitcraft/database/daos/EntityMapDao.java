package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.EntityMapEntity;

import java.sql.SQLException;

public class EntityMapDao extends BaseDaoImpl<EntityMapEntity, Integer> {


    public EntityMapDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, EntityMapEntity.class);
    }

    public EntityMapEntity getEntityMapById(int id) throws SQLException {
        return queryForId(id);
    }
}


