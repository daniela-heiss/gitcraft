package top.gitcraft.database.daos;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.CommitManagementEntity;

import java.sql.SQLException;

public class CommitManagementDao {

    private Dao<CommitManagementEntity, Integer> commitManagementDao;

    public CommitManagementDao(ConnectionSource connectionSource) throws SQLException {
        commitManagementDao = DaoManager.createDao(connectionSource, CommitManagementEntity.class);
    }

    public void createCommit(CommitManagementEntity commit) throws SQLException {
        commitManagementDao.create(commit);
    }

    public void updateCommit(CommitManagementEntity commit) throws SQLException {
        commitManagementDao.update(commit);
    }

    public void deleteCommit(CommitManagementEntity commit) throws SQLException {
        commitManagementDao.delete(commit);
    }

    public Iterable<CommitManagementEntity> getCommitsById(Integer commitId, String playerName) throws SQLException {
        return commitManagementDao.queryBuilder().where().eq("currentID", commitId).and().eq("playerName", playerName).query();
    }
}
