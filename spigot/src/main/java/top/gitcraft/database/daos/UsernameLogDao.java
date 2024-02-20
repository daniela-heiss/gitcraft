package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.UsernameLogEntity;

import java.sql.SQLException;

public class UsernameLogDao extends BaseDaoImpl<UsernameLogEntity, Integer> {

    public UsernameLogDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, UsernameLogEntity.class);
    }

    public UsernameLogEntity getUsernameLogById(int id) throws SQLException {
        return queryForId(id);
    }
}
