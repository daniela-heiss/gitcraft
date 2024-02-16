package top.gitcraft.database.daos;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.CommitEntity;

import java.sql.SQLException;

public class CommitDao {

    private final Dao<CommitEntity, Integer> commitDao;

    public CommitDao(ConnectionSource connectionSource) throws SQLException {
        commitDao = DaoManager.createDao(connectionSource, CommitEntity.class);
    }

    public void createCommit(CommitEntity commit) throws SQLException {
        commitDao.create(commit);
    }

    public CommitEntity getByCommitId(int id) throws SQLException {
        return commitDao.queryForId(id);
    }

    public Iterable<CommitEntity> getAllCommits() throws SQLException {
        return commitDao.queryForAll();
    }
}
