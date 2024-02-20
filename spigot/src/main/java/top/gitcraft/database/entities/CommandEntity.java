package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_command")
public class CommandEntity {

    @DatabaseField(generatedId = true, columnName = "rowid")
    public int rowId;

    @DatabaseField
    public int time;

    @DatabaseField(columnName = "user")
    public int userId;

    @DatabaseField(columnName = "wid")
    public int worldId;

    @DatabaseField
    public int x;

    @DatabaseField
    public int y;

    @DatabaseField
    public int z;

    @DatabaseField
    public String message;
}
