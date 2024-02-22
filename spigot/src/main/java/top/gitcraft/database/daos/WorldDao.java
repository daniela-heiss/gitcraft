package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.WorldEntity;

import java.sql.SQLException;
import java.util.List;

public class WorldDao extends BaseDaoImpl<WorldEntity, Integer> {
    

    public WorldDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, WorldEntity.class);
        if (!checkColumnExists("playerId")) {
            addColumn("playerId", "INT");
        }
    }

    public WorldEntity getWorldById(int id) throws SQLException {
        return queryForId(id);
    }

    public WorldEntity getWorldByPlayerId(int playerId) throws SQLException {
        return queryForId(playerId);
    }

    public WorldEntity getWorldByWorldName(String worldName) throws SQLException {
        return queryBuilder().where().eq("world", worldName).queryForFirst();
    }

    public boolean checkColumnExists(String columnName) throws SQLException {
        String[] columns = queryRaw("SELECT * FROM co_world LIMIT 1").getColumnNames();
        for (String column : columns) {
            if (column.equals(columnName)) {
                return true;
            }
        }
        return false;
    }

    public void addColumn(String columnName, String columnType) throws SQLException {
        executeRaw("ALTER TABLE co_world ADD COLUMN" + " " + columnName + " "+ columnType);
    }
}
