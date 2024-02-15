package top.gitcraft.database.daos;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.PlayerEntity;

import java.sql.SQLException;
import java.util.List;

public class PlayerDao {
    private Dao<PlayerEntity, Integer> playerDao;

    public PlayerDao(ConnectionSource connectionSource) throws SQLException {
        playerDao = DaoManager.createDao(connectionSource, PlayerEntity.class);
    }

    public void createPlayer(PlayerEntity player) throws SQLException {
        playerDao.create(player);
    }

    public void updatePlayer(PlayerEntity player) throws SQLException {
        playerDao.update(player);
    }

    public void deletePlayer(PlayerEntity player) throws SQLException {
        playerDao.delete(player);
    }

    public PlayerEntity getPlayerById(int id) throws SQLException {
        return playerDao.queryForId(id);
    }

    public PlayerEntity getPlayerByName(String name) throws SQLException {
        return playerDao.queryBuilder().where().eq("name", name).queryForFirst();
    }

    //get all players
    public List<PlayerEntity> getAllPlayers() throws SQLException {
        return playerDao.queryForAll();
    }
}
