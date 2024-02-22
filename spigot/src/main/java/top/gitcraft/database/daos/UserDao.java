package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.UserEntity;

import java.sql.SQLException;
import java.util.UUID;

public class UserDao extends BaseDaoImpl<UserEntity, Integer>{

    public UserDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, UserEntity.class);
    }

    public UserEntity getUserByRowId(int rowid) throws SQLException {
        return queryForId(rowid);
    }

    public UserEntity getUserByUuid(UUID uuid) throws SQLException {
        return queryBuilder().where().eq("uuid",uuid).queryForFirst();
    }
}
