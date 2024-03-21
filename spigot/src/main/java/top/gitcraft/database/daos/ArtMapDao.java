package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.ArtMapEntity;

import java.sql.SQLException;

public class ArtMapDao extends BaseDaoImpl<ArtMapEntity, Integer> {

    public ArtMapDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ArtMapEntity.class);
    }

    public ArtMapEntity getArtMapById(int id) throws SQLException {
        return queryForId(id);
    }
}
