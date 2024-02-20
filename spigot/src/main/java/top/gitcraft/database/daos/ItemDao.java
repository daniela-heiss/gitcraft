package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.ItemEntity;

import java.sql.SQLException;

public class ItemDao extends BaseDaoImpl<ItemEntity, Integer> {

    public ItemDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ItemEntity.class);
    }

    public ItemEntity getItemById(int id) throws SQLException {
        return queryForId(id);
    }
}
