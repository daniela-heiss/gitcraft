package top.gitcraft.database.daos;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.UserEntity;

import java.sql.SQLException;

public class UserDao {

    private Dao<UserEntity, Integer> userDao;

    public UserDao(ConnectionSource connectionSource) throws SQLException {
        userDao = DaoManager.createDao(connectionSource, UserEntity.class);
    }

    public String getUserByRowId(int rowid) throws SQLException {
        return userDao.queryForId(rowid).user;
    }



}
