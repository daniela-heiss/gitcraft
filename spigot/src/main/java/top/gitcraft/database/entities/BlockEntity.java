package top.gitcraft.database.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_block")
public class BlockEntity {
    @DatabaseField(generatedId = true)
    private long rowid;

    @DatabaseField(dataType = DataType.INTEGER)
    private int time;

    @DatabaseField(dataType = DataType.INTEGER)
    private int user;

    @DatabaseField(dataType = DataType.INTEGER)
    private int wid;

    @DatabaseField(dataType = DataType.INTEGER)
    private int x;

    @DatabaseField(dataType = DataType.INTEGER)
    private int y;

    @DatabaseField(dataType = DataType.INTEGER)
    private int z;

    @DatabaseField(dataType = DataType.INTEGER)
    private int type;

    @DatabaseField(dataType = DataType.INTEGER)
    private int data;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] meta;

    @DatabaseField(dataType = DataType.BYTE_ARRAY)
    private byte[] blockdata;

    @DatabaseField(dataType = DataType.BOOLEAN)
    private boolean action;

    @DatabaseField(dataType = DataType.BOOLEAN)
    private boolean rolled_back;
}
