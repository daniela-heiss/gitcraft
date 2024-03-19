package top.gitcraft.database.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "save")
public class SaveEntity {
    @DatabaseField(generatedId = true)
    public int rowId;

    @DatabaseField (dataType = DataType.INTEGER, columnName = "user")
    public int playerId;

    @DatabaseField (dataType = DataType.INTEGER, columnName = "wid")
    public int worldId;

    @DatabaseField (dataType = DataType.INTEGER)
    public int time;

    @DatabaseField (dataType = DataType.STRING, columnName = "save_name")
    public String saveName;

    @DatabaseField (dataType = DataType.INTEGER, columnName = "rolled_back")
    public int rolledBack;
}
