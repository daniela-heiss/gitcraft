package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.MaterialMapEntity;

import java.sql.SQLException;

public class MaterialMapDao extends BaseDaoImpl<MaterialMapEntity, Integer> {
    public MaterialMapDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, MaterialMapEntity.class);
    }

    public MaterialMapEntity getMaterialById(int id) throws SQLException {
        return queryForId(id);
    }
}