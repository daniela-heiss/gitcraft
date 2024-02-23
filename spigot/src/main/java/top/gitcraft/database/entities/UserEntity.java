package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.sql.Time;
import java.util.UUID;

@DatabaseTable(tableName = "co_user")
public class UserEntity {
    @DatabaseField(generatedId = true, columnName = "rowid")
    public int rowId;

    @DatabaseField(columnName = "user")
    public String userName;

    @DatabaseField
    public String uuid;

    @Override
    public String toString() {
        return "UserEntity{" +
                "rowId=" + rowId +
                ", userName='" + userName + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
