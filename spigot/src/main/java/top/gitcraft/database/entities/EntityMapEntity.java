package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_entity_map")
public class EntityMapEntity {

    @DatabaseField(generatedId = true, columnName = "rowid")
    public int rowId;

    @DatabaseField
    public int id;

    @DatabaseField(columnName = "entity")
    public String entityName;
}
