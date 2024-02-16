package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_user")
public class UserEntity {
    @DatabaseField(generatedId = true, columnName = "rowid")
    public int rowId;

    @DatabaseField
    public int time;

    @DatabaseField(columnName = "user")
    public String userName;

    @DatabaseField
    public String uuid;
}
