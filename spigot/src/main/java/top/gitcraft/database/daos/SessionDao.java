package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.SessionEntity;

import java.sql.SQLException;

public class SessionDao extends BaseDaoImpl<SessionEntity, Integer> {

    public SessionDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SessionEntity.class);
    }

    public SessionEntity getSessionById(int id) throws SQLException {
        return queryForId(id);
    }
}
