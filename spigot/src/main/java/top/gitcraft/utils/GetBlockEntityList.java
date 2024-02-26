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

    public static List<BlockEntity> GetBlockEntityList(String worldName) {
        WorldEntity world;

        try {
            world = worldDao.getWorldByWorldName(worldName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        int worldId = world.id;

        List<UserEntity> userEntityList;
        try {
            userEntityList = userDao.getAllUsersWitUuid();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Integer> userIds = new ArrayList<>();
        for (UserEntity userEntity : userEntityList) {
            userIds.add(userEntity.rowId);
        }

        List<BlockEntity> blockEntityList;
        try {
            blockEntityList = blockDao.getUserBlocksByWorldId(worldId, userIds);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return blockEntityList;
    }
}
