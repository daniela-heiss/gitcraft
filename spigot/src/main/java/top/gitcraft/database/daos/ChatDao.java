package top.gitcraft.database.daos;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import top.gitcraft.database.entities.ChatEntity;

import java.sql.SQLException;

public class ChatDao extends BaseDaoImpl<ChatEntity, Integer> {

    public ChatDao(ConnectionSource connectionSource) throws SQLException {
        super(connectionSource, ChatEntity.class);
    }

    public ChatEntity getChatById(int id) throws SQLException {
        return queryForId(id);
    }
}
