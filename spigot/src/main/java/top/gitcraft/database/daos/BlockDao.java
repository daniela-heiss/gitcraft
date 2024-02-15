package top.gitcraft.database.daos;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.BlockEntity;

import java.sql.SQLException;

public class BlockDao {
    private Dao<BlockEntity, Integer> blockDao;

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

    public Iterable<BlockEntity> getBlocksByUser(Integer user) throws SQLException {
        return blockDao.queryBuilder().where().eq("user", user).query();
    }

    public Iterable<BlockEntity> getBlocksByLocation(int x, int y, int z) throws SQLException {
        return blockDao.queryBuilder().where().eq("x", x).and().eq("y", y).and().eq("z", z).query();
    }
    public boolean checkColumnExists(String columnName) throws SQLException {
        String[] columns =  blockDao.queryRaw("SELECT * FROM block LIMIT 1").getColumnNames();
        for (String column : columns) {
            if (column.equals(columnName)) {
                return true;
            }
        }
        return false;
    }
    public int getCommitID() throws SQLException {
    return 0;
    }

    public void addColumn(String columnName, String columnType) throws SQLException {
        blockDao.executeRaw("ALTER TABLE block ADD COLUMN " + columnName + " " + columnType);
    }

}
