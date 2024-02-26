package top.gitcraft.utils;

import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.BlockEntity;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldEntity;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GetBlockEntityList {

    private static WorldDao worldDao;
    private static BlockDao blockDao;
    private static UserDao userDao;

    public GetBlockEntityList() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        worldDao = databaseManager.getWorldDao();
        blockDao = databaseManager.getBlockDao();
        userDao = databaseManager.getUserDao();
    }

    public static List<BlockEntity> getBlockChangedByPlayers(String worldName) {
        WorldEntity world;

        try {
            //get world
            world = worldDao.getWorldByWorldName(worldName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int worldId = world.id;

        List<UserEntity> players;
        try {
            //get players from users
            players = userDao.getAllUsersWitUuid();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Integer> playerIds = new ArrayList<>();
        for (UserEntity player : players) {
            playerIds.add(player.rowId);
        }

        List<BlockEntity> blockEntityList;
        try {
            //get block changed by players
            blockEntityList = blockDao.getUserBlocksByWorldId(worldId, playerIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return blockEntityList;
    }
}
