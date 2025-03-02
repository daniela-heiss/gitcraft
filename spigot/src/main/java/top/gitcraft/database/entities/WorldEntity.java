package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_world")
public class WorldEntity {

    @DatabaseField(generatedId = true, columnName = "rowid")
    public int rowId;

    @DatabaseField
    public int id;

    @DatabaseField(columnName = "world")
    public String worldName;

    @DatabaseField(columnName = "playerId")
    public int playerId;

    @Override
    public String toString() {
        return "WorldEntity{" +
                "rowId=" + rowId +
                ", id=" + id +
                ", worldName='" + worldName + '\'' +
                ", playerId=" + playerId +
                '}';
    }

}
