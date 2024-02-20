package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_version")
public class VersionEntity {
    @DatabaseField(generatedId = true, columnName = "rowid")
    public int rowId;

    @DatabaseField
    public int time;

    @DatabaseField
    public String version;
}

