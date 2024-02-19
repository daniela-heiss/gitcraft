package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.CommitManagementEntity;

import java.sql.SQLException;

public class CommitManagementDao extends BaseDaoImpl<CommitManagementEntity, Integer> {
 
    public CommitManagementDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, CommitManagementEntity.class);
    }

    public void createCommit(CommitManagementEntity commit) throws SQLException {
        create(commit);
    }

    public void updateCommit(CommitManagementEntity commit) throws SQLException {
        update(commit);
    }

    public void deleteCommit(CommitManagementEntity commit) throws SQLException {
        delete(commit);
    }

    public Iterable<CommitManagementEntity> getCommitsById(Integer commitId, Integer playerId) throws SQLException {
        return queryBuilder().where().eq("currentId", commitId).and().eq("playerId", playerId).query();
    }
}
