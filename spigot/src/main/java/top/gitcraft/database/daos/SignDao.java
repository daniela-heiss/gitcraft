package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.SignEntity;

import java.sql.SQLException;

public class SignDao extends BaseDaoImpl<SignEntity, Integer>{

    public SignDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SignEntity.class);
    }

    public SignEntity getSignById(int rowid) throws SQLException {
        return queryForId(rowid);
    }



}
