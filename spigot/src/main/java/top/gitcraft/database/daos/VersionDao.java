package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.VersionEntity;
import top.gitcraft.database.entities.WorldEntity;

import java.sql.SQLException;

public class VersionDao extends BaseDaoImpl<VersionEntity, Integer> {

    public VersionDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, VersionEntity.class);
    }

    public VersionEntity getVersionById(int id) throws SQLException {
        return queryForId(id);
    }
}
