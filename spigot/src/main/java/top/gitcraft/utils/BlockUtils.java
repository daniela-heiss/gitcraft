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

    public static BlockVector3 findMin(List<BlockEntity> list) {
        int minX = 0;
        int minY = 0;
        int minZ = 0;


        for (int i = 0; i < list.size() - 1; i++) {
            if (i == 0) {
                minX = list.get(i).x;
                minY = list.get(i).y;
                minZ = list.get(i).z;
            } else {
                if (list.get(i).x < minX) {
                    minX = list.get(i).x;
                }
                if (list.get(i).y < minY) {
                    minY = list.get(i).y;
                }
                if (list.get(i).z < minZ) {
                    minZ = list.get(i).z;
                }
            }
        }


        return BlockVector3.at(minX, minY, minZ);
    }

    public static BlockVector3 findMax(List<BlockEntity> list) {

        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;


        for (int i = 0; i < list.size() - 1; i++) {
            if (i == 0) {
                maxX = list.get(i).x;
                maxY = list.get(i).y;
                maxZ = list.get(i).z;
            } else {
                if (list.get(i).x > maxX) {
                    maxX = list.get(i).x;
                }
                if (list.get(i).y > maxY) {
                    maxY = list.get(i).y;
                }
                if (list.get(i).z > maxZ) {
                    maxZ = list.get(i).z;
                }
            }
        }

        return BlockVector3.at(maxX, maxY, maxZ);
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
