package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_world")
public class WorldEntity {

    @DatabaseField(generatedId = true)
    public int rowid;

    @DatabaseField
    public int id;

    @DatabaseField
    public String world;
}
