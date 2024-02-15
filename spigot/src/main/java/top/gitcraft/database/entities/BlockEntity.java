package top.gitcraft.database.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_block")
public class BlockEntity {
    @DatabaseField(generatedId = true)
    public long rowid;

    @DatabaseField(dataType = DataType.INTEGER)
    public int time;

    @DatabaseField(dataType = DataType.INTEGER)
    public int user;

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

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    public byte[] blockdata;

    @DatabaseField(dataType = DataType.INTEGER)
    public int action;

    @DatabaseField(dataType = DataType.BOOLEAN)
    public boolean rolled_back;

    @DatabaseField(dataType = DataType.INTEGER)
    public int commitID;
}
