package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.SaveEntity;
import top.gitcraft.database.entities.UserEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public class UserDao extends BaseDaoImpl<UserEntity, Integer> {

    public UserDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, UserEntity.class);
    }

    public UserEntity getUserByRowId(int rowid) throws SQLException {
        return queryForId(rowid);
    }

    public UserEntity getUserByName(String userName) throws SQLException {
        return queryBuilder().where().eq("user", userName).queryForFirst();
    }

    public List<UserEntity> getAllUsersWitUuid() throws SQLException {
        return queryBuilder().where().isNotNull("uuid").query();
    }
    public UserEntity getUserByUuid(UUID uuid) throws SQLException {
        return queryBuilder().where().eq("uuid", uuid.toString()).queryForFirst();
    }
}
