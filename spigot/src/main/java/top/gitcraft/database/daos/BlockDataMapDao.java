package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.BlockDataMapEntity;

import java.sql.SQLException;

public class BlockDataMapDao extends BaseDaoImpl<BlockDataMapEntity, Integer> {

    public BlockDataMapDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, BlockDataMapEntity.class);
    }

    public BlockDataMapEntity getBlockDataById(int id) throws SQLException {
        return queryForId(id);
    }
}
