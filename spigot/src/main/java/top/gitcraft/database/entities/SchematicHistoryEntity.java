package top.gitcraft.database.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_schematichistory")
public class SchematicHistoryEntity {
    @DatabaseField(generatedId = true, columnName = "rowid")
    public long rowId;

    @DatabaseField(dataType = DataType.STRING, columnName = "schematicname")
    public String schematicname;

    @DatabaseField(dataType = DataType.STRING, columnName = "uuid")
    public String uuid;

    @DatabaseField(dataType = DataType.INTEGER, columnName = "timestamp")
    public int Timestamp;
}
