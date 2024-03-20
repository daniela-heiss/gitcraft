package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.support.ConnectionSource;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.jetbrains.annotations.NotNull;
import top.gitcraft.database.entities.BlockEntity;

import java.sql.SQLException;
import java.util.List;

public class BlockDao extends BaseDaoImpl<BlockEntity, Integer> {

    public BlockDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, BlockEntity.class);
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

    public List<BlockEntity> getUserBlocksByWorldId(Integer worldId, List<Integer> userIds) throws SQLException {
        return queryBuilder().where().eq("wid", worldId).and().in("user", userIds).query();
    }

    public List<BlockEntity> getBlocksInRegionByWorld(CuboidRegion region) throws SQLException {
        int minX = region.getMinimumPoint().getBlockX();
        int minY = region.getMinimumPoint().getBlockY();
        int minZ = region.getMinimumPoint().getBlockZ();
        int maxX = region.getMaximumPoint().getBlockX();
        int maxY = region.getMaximumPoint().getBlockY();
        int maxZ = region.getMaximumPoint().getBlockZ();

        return queryBuilder().where().between("x", minX, maxX).and().between("y", minY, maxY).and().between("z", minZ, maxZ).query();
    }

    public List<BlockEntity> getLastBlockChangesInRegionByWorld(CuboidRegion region, Integer worldId) throws SQLException {
        String[] arguments = getStrings(region, worldId);
        String query = "SELECT * FROM co_block WHERE " + "wid = ?" + " AND x BETWEEN ? AND ? " + "AND y BETWEEN ? " +
                "AND ? " + "AND z BETWEEN ? AND ? " +
                "GROUP BY x, y, z " +
                "ORDER BY time DESC";
        GenericRawResults<BlockEntity> blockEntities = queryRaw(query, getRawRowMapper(), arguments);
        return blockEntities.getResults();
    }

    @NotNull
    private static String[] getStrings(CuboidRegion region, Integer worldId) {
        int minX = region.getMinimumPoint().getBlockX();
        int minY = region.getMinimumPoint().getBlockY();
        int minZ = region.getMinimumPoint().getBlockZ();
        int maxX = region.getMaximumPoint().getBlockX();
        int maxY = region.getMaximumPoint().getBlockY();
        int maxZ = region.getMaximumPoint().getBlockZ();
        return new String[]{String.valueOf(worldId), String.valueOf(minX), String.valueOf(maxX), String.valueOf(minY),
                String.valueOf(maxY)
                , String.valueOf(minZ), String.valueOf(maxZ)};
    }

}
