package top.gitcraft.utils;

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
}
