package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_blockdata_map")
public class BlockDataMapEntity {

    @DatabaseField(generatedId = true, columnName = "rowid")
    public int rowId;

    @DatabaseField
    public int id;

    @DatabaseField (columnName = "data")
    public String blockData;
}
