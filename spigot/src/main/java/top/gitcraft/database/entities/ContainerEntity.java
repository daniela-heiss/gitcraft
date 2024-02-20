package top.gitcraft.database.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_container")
public class ContainerEntity {

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
    public int type;

    @DatabaseField (columnName = "data")
    public int blockData;

    @DatabaseField
    public int amount;

    @DatabaseField
    public byte[] metaData;

    @DatabaseField
    public int action;

    @DatabaseField(columnName = "rolled_back")
    public boolean rolledBack;
}
