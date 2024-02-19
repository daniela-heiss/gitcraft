package top.gitcraft.database.daos;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.BlockDataMapEntity;

import java.sql.SQLException;

public class BlockDataMapDao {

    private final Dao<BlockDataMapEntity, Integer> blockDataMapDao;

    public BlockDataMapDao(ConnectionSource connectionSource) throws SQLException {
        blockDataMapDao = DaoManager.createDao(connectionSource, BlockDataMapEntity.class);
    }

    public String getBlockDataById(int id) throws SQLException {
        return blockDataMapDao.queryForId(id).blockData;
    }
}


