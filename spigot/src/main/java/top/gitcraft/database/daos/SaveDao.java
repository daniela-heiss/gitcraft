package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.SaveEntity;

import java.sql.SQLException;
import java.util.List;

public class SaveDao extends BaseDaoImpl<SaveEntity, Integer>{

    public SaveDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, SaveEntity.class);
    }

    public void createSave(SaveEntity save) throws SQLException {
        create(save);
    }

    public SaveEntity getBySaveId(int id) throws SQLException {
        return queryForId(id);
    }

    public List<SaveEntity> getAllCommits() throws SQLException {
        return queryForAll();
    }

    public void updateSave(SaveEntity save) throws SQLException {
        update(save);
    }

    public void deleteSave(SaveEntity save) throws SQLException {
        delete(save);
    }

    public List<SaveEntity> getAllSavesByUser(Integer playerId) throws SQLException {
        return queryBuilder().where().eq("user", playerId).query();
    }

    public SaveEntity getSaveByUserAndName(Integer playerId, String saveName) throws SQLException {
        return queryBuilder().where().eq("save_name", saveName).and().eq("user", playerId).queryForFirst();
    }

    public List<SaveEntity> getAllEarlierSavesByPlayerAndTime(Integer playerId, int time) throws SQLException {
        return queryBuilder().where().lt("time", time).and().eq("user", playerId).query();
    }

    public List<SaveEntity> getAllLaterSavesByPlayerAndTime(Integer playerId, int time) throws SQLException {
        return queryBuilder().where().gt("time", time).and().eq("user", playerId).query();
    }
}
