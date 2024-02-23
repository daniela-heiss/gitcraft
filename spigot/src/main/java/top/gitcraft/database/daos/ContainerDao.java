package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.ContainerEntity;

import java.sql.SQLException;

public class ContainerDao extends BaseDaoImpl<ContainerEntity, Integer> {

    public ContainerDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ContainerEntity.class);
    }

    public ContainerEntity getContainerById(int id) throws SQLException {
        return queryForId(id);
    }
}
