package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.CommitManagementEntity;
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

    public List<SaveEntity> getSaveByUser(Integer playerId) throws SQLException {
        return queryBuilder().where().eq("playerId", playerId).query();
    }

        public List<SaveEntity> getSaveByUserAndName(String saveName, Integer playerId) throws SQLException {
        return queryBuilder().where().eq("saveName", saveName).and().eq("playerId", playerId).query();
    }
}
