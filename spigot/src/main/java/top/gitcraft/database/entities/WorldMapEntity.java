package top.gitcraft.database.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "world_map")
public class WorldMapEntity {
    @DatabaseField(generatedId = true)
    public int rowId;

    @DatabaseField (dataType = DataType.INTEGER, columnName = "user")
    public int playerId;

    @DatabaseField(dataType = DataType.STRING, columnName = "world_name")
    public String worldName;
}
