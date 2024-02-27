package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.BlockEntity;

import java.sql.SQLException;
import java.util.List;

public class BlockDao extends BaseDaoImpl<BlockEntity, Integer> {

    public BlockDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, BlockEntity.class);
        /*if (!checkColumnExists("commitId")) {
            addColumn("commitId", "INT");
        }*/
    }

    public void createBlock(BlockEntity block) throws SQLException {
        create(block);
    }

    public void updateBlock(BlockEntity block) throws SQLException {
        update(block);
    }

    public void deleteBlock(BlockEntity block) throws SQLException {
        delete(block);
    }

    public BlockEntity getBlockById(int id) throws SQLException {
        return queryForId(id);
    }

    public List<BlockEntity> getAllBlocks() throws SQLException {
        return queryForAll();
    }

    public List<BlockEntity> getBlocksByUserId(Integer userId) throws SQLException {
        return queryBuilder().where().eq("user", userId).query();
    }

    public List<BlockEntity> getBlocksByUserIdWithoutInteract(Integer userId) throws SQLException {
        return queryBuilder().where().eq("user", userId).and().ne("action", 2).query();
    }

    /*public List<BlockEntity> getBlocksWithoutCommitByUserId(Integer userId) throws SQLException {
        return queryBuilder().where().eq("user", userId).and().isNull("commitId").query();
    }*/

    public List<BlockEntity> getBlocksByLocation(int x, int y, int z) throws SQLException {
        return queryBuilder().where().eq("x", x).and().eq("y", y).and().eq("z", z).query();
    }

    public boolean checkColumnExists(String columnName) throws SQLException {
        String[] columns = queryRaw("SELECT * FROM co_block LIMIT 1").getColumnNames();
        for (String column : columns) {
            if (column.equals(columnName)) {
                return true;
            }
        }
        return false;
    }

   /* public void addColumn(String columnName, String columnType) throws SQLException {
        executeRaw("ALTER TABLE co_block ADD COLUMN " + columnName + " " + columnType);
    }*/

    public List<BlockEntity> getUserBlocksByWorldId(Integer worldId, List<Integer> userIds) throws SQLException {
        return queryBuilder().where().eq("wid", worldId).and().in("user", userIds).query();
    }

}
