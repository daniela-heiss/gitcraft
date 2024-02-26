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

    public void createWorldMap(WorldMapEntity worldMap) throws SQLException {
        create(worldMap);
    }

    public void deleteWorldMap(WorldMapEntity worldMap) throws SQLException {
        delete(worldMap);
    }

    public List<WorldMapEntity> getAllByPID(int playerId) throws SQLException {
        return queryBuilder().where().eq("user", playerId).query();
    }

    public List<WorldMapEntity> getAllByWID(int worldId) throws SQLException {
        return queryBuilder().where().eq("wid", worldId).query();
    }
}
