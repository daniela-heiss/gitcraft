package top.gitcraft.database.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_block")
public class BlockEntity {
    @DatabaseField(generatedId = true, columnName = "rowid")
    public long rowId;

    @DatabaseField(dataType = DataType.INTEGER)
    public int time;

    @DatabaseField(dataType = DataType.INTEGER, columnName = "user")
    public int userId;

    @DatabaseField(dataType = DataType.INTEGER)
    public int wid;

    @DatabaseField(dataType = DataType.INTEGER)
    public int x;

    @DatabaseField(dataType = DataType.INTEGER)
    public int y;

    @DatabaseField(dataType = DataType.INTEGER)
    public int z;

    @DatabaseField(dataType = DataType.INTEGER)
    public int type;

    @DatabaseField(dataType = DataType.INTEGER)
    public int data;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    public byte[] meta;

    @DatabaseField(dataType = DataType.BYTE_ARRAY, columnName = "blockdata")
    public byte[] blockData;

    @DatabaseField(dataType = DataType.INTEGER)
    public int action;

    @DatabaseField(dataType = DataType.BOOLEAN, columnName = "rolled_back")
    public boolean rolledBack;
}
