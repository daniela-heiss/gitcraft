package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.SkullEntity;

import java.sql.SQLException;

public class SkullDao extends BaseDaoImpl<SkullEntity, Integer>{

    public SkullDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SkullEntity.class);
    }

    public SkullEntity getSkullById(int rowid) throws SQLException {
        return queryForId(rowid);
    }



}
