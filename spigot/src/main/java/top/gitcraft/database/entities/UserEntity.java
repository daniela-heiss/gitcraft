package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_user")
public class UserEntity {
    @DatabaseField(generatedId = true)
    public int rowid;

    @DatabaseField
    public int time;

    @DatabaseField
    public String user;

    @DatabaseField
    public String uuid;
}
