package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.DatabaseLockEntity;

import java.sql.SQLException;

public class DatabaseLockDao extends BaseDaoImpl<DatabaseLockEntity, Integer> {

    public DatabaseLockDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, DatabaseLockEntity.class);
    }

    public DatabaseLockEntity getDatabaseLockById(int id) throws SQLException {
        return queryForId(id);
    }
}
