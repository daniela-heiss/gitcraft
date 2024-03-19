package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.SchematicHistoryEntity;

import java.sql.SQLException;

import java.util.List;

import static com.j256.ormlite.table.TableUtils.createTableIfNotExists;

public class SchematicHistoryDao extends BaseDaoImpl<SchematicHistoryEntity, Integer> {
    public SchematicHistoryDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SchematicHistoryEntity.class);
        createTableIfNotExists(connectionSource, SchematicHistoryEntity.class);
    }

    public void createHistoryEntry(SchematicHistoryEntity history) throws SQLException {
        create(history);
    }

    public void deleteHistoryEntry(SchematicHistoryEntity history) throws SQLException {
        delete(history);
    }
    public SchematicHistoryEntity getHistoryByRowId(int rowid) throws SQLException {
        return queryForId(rowid);
    }

    public SchematicHistoryEntity getHistoryBySchematicName (String schematicName) throws SQLException {
        return queryBuilder().where().eq("schematicname", schematicName).queryForFirst();
    }

    public List<SchematicHistoryEntity> getEntireSchematicHistory() throws SQLException {
        return queryForAll();
    }

    public List<SchematicHistoryEntity> getEntireSchematicHistoryOfUser(String uuid) throws SQLException {
        return queryBuilder().where().eq("uuid", uuid).query();
    }
}
