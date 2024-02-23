package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.CommandEntity;

import java.sql.SQLException;

public class CommandDao extends BaseDaoImpl<CommandEntity, Integer> {

    public CommandDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, CommandEntity.class);
    }

    public CommandEntity getCommandById(int id) throws SQLException {
        return queryForId(id);
    }
}
