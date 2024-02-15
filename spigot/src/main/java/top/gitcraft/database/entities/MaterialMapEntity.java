package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_material_map")
public class MaterialMapEntity {

    @DatabaseField(generatedId = true)
    public int rowid;

    @DatabaseField
    public int id;

    @DatabaseField
    public String material;
}
