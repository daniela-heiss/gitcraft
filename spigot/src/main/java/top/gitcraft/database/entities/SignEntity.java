package top.gitcraft.database.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "co_sign")
public class SignEntity {

    @DatabaseField(generatedId = true, columnName = "rowid")
    public int rowId;

    @DatabaseField
    public int time;

    @DatabaseField(columnName = "user")
    public int userId;

    @DatabaseField(columnName = "wid")
    public int worldId;

    @DatabaseField
    public int x;

    @DatabaseField
    public int y;

    @DatabaseField
    public int z;

    @DatabaseField
    public int action;

    @DatabaseField
    public int color;

    @DatabaseField (columnName = "color_secondary")
    public int colorSecondary;

    @DatabaseField
    public int data;

    @DatabaseField
    public int waxed;

    @DatabaseField
    public int face;

    @DatabaseField(columnName = "line_1")
    public String lineOne;

    @DatabaseField(columnName = "line_2")
    public String lineTwo;

    @DatabaseField(columnName = "line_3")
    public String lineThree;

    @DatabaseField(columnName = "line_4")
    public String lineFour;

    @DatabaseField(columnName = "line_5")
    public String lineFive;

    @DatabaseField(columnName = "line_6")
    public String lineSix;

    @DatabaseField(columnName = "line_7")
    public String lineSeven;

    @DatabaseField(columnName = "line_8")
    public String lineEight;
}
