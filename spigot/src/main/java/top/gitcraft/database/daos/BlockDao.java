package top.gitcraft.database.daos;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.BlockEntity;

import java.sql.SQLException;

public class BlockDao {
    private final Dao<BlockEntity, Integer> blockDao;

    public BlockDao(ConnectionSource connectionSource) throws SQLException {
        blockDao = DaoManager.createDao(connectionSource, BlockEntity.class);
    }

    public void createBlock(BlockEntity block) throws SQLException {
        blockDao.create(block);
    }

    public void updateBlock(BlockEntity block) throws SQLException {
        blockDao.update(block);
    }

    public void deleteBlock(BlockEntity block) throws SQLException {
        blockDao.delete(block);
    }

    public BlockEntity getBlockById(int id) throws SQLException {
        return blockDao.queryForId(id);
    }

    public Iterable<BlockEntity> getAllBlocks() throws SQLException {
        return blockDao.queryForAll();
    }

    public Iterable<BlockEntity> getBlocksByUserId(Integer userId) throws SQLException {
        return blockDao.queryBuilder().where().eq("user", userId).query();
    }

    public Iterable<BlockEntity> getBlocksWithoutCommitByUserId(Integer userId) throws SQLException {
        return blockDao.queryBuilder().where().eq("user", userId).and().isNull("commitId").query();
    }

    public Iterable<BlockEntity> getBlocksByLocation(int x, int y, int z) throws SQLException {
        return blockDao.queryBuilder().where().eq("x", x).and().eq("y", y).and().eq("z", z).query();
    }
}
