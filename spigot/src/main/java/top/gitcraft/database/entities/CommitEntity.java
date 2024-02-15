package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "commit")
public class CommitEntity {
    @DatabaseField(generatedId = true)
    public int commitid;

    @DatabaseField
    public int playercommitid;

    @DatabaseField
    public int pid;

    @DatabaseField
    public String commitname;
}
