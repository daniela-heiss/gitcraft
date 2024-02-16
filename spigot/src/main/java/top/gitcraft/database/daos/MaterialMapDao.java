package top.gitcraft.database.daos;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.MaterialMapEntity;

import java.sql.SQLException;

public class MaterialMapDao {

    private final Dao<MaterialMapEntity, Integer> materialMapDao;

    public MaterialMapDao(ConnectionSource connectionSource) throws SQLException {
        materialMapDao = DaoManager.createDao(connectionSource, MaterialMapEntity.class);
    }

    public String getMaterialById(int id) throws SQLException {
        return materialMapDao.queryForId(id).material;
    }
}


