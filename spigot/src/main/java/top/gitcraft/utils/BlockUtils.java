package top.gitcraft.utils;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.BlockEntity;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldEntity;

import java.util.ArrayList;
import java.util.List;

public class BlockUtils {

    public static List<BlockEntity> getBlockChangedByPlayers(String worldName) {
        try {

            DatabaseManager instance = DatabaseManager.getInstance();
            UserDao userDao = instance.getUserDao();
            WorldDao worldDao = instance.getWorldDao();
            BlockDao blockDao = instance.getBlockDao();

            WorldEntity world = worldDao.getWorldByWorldName(worldName);
            List<UserEntity> players = userDao.getAllUsersWitUuid();

            List<Integer> playerIds = new ArrayList<>();
            for (UserEntity player : players) {
                playerIds.add(player.rowId);
            }
            return blockDao.getUserBlocksByWorldId(world.id, playerIds);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static CuboidRegion regionFromList(List<BlockEntity> list) {
        if (list.isEmpty()) {
            return null;
        }

        int minX = list.get(0).x;
        int minY = list.get(0).y;
        int minZ = list.get(0).z;
        int maxX = minX;
        int maxY = minY;
        int maxZ = minZ;

        for (BlockEntity entity : list) {
            minX = Math.min(minX, entity.x);
            minY = Math.min(minY, entity.y);
            minZ = Math.min(minZ, entity.z);
            maxX = Math.max(maxX, entity.x);
            maxY = Math.max(maxY, entity.y);
            maxZ = Math.max(maxZ, entity.z);
        }

        BlockVector3 minVector = BlockVector3.at(minX, minY, minZ);
        BlockVector3 maxVector = BlockVector3.at(maxX, maxY, maxZ);

        return new CuboidRegion(minVector, maxVector);
    }


    public static List<BlockEntity> getBlocksInRegion(List<BlockEntity> list, CuboidRegion region) {
        List<BlockEntity> areaBlocks = new ArrayList<>(list.size());
        for (BlockEntity blockEntity : list) {
            if (region.contains(BlockVector3.at(blockEntity.x, blockEntity.y, blockEntity.z))) {
                areaBlocks.add(blockEntity);
            }
        }
        return areaBlocks;
    }
}
