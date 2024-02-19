package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.CommitEntity;

import java.sql.SQLException;
import java.util.List;

public class CommitDao extends BaseDaoImpl<CommitEntity, Integer>{

    public CommitDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, CommitEntity.class);
    }

    public void createCommit(CommitEntity commit) throws SQLException {
        create(commit);
    }

    public CommitEntity getByCommitId(int id) throws SQLException {
        return queryForId(id);
    }

    public List<CommitEntity> getAllCommits() throws SQLException {
        return queryForAll();
    }
}
