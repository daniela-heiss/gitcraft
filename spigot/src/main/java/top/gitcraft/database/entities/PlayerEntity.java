package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "players")
public class PlayerEntity {
    @DatabaseField(generatedId  = true)
    public int id;

    @DatabaseField
    public String name;

}
