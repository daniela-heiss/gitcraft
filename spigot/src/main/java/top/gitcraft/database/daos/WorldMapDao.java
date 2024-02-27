package top.gitcraft.database.daos;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.WorldMapEntity;
import java.util.List;
import java.sql.SQLException;


public class WorldMapDao extends BaseDaoImpl<WorldMapEntity, Integer> {
    public WorldMapDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, WorldMapEntity.class);
    }

    public void createWorldMapping(WorldMapEntity worldMap) throws SQLException {
        create(worldMap);
    }

    public void deleteWorldMapping(WorldMapEntity worldMap) throws SQLException {
        delete(worldMap);
    }

    public List<WorldMapEntity> getAllByPID(int playerId) throws SQLException {
        return queryBuilder().where().eq("user", playerId).query();
    }

    public List<WorldMapEntity> getAllByWorldName(String worldName) throws SQLException {
        return queryBuilder().where().eq("world_name", worldName).query();
    }

    public WorldMapEntity getByPIDAndWorldName(int playerId, String worldName) throws SQLException {
        return queryBuilder().where().eq("user", playerId).and().eq("world_name", worldName).queryForFirst();
    }
}
