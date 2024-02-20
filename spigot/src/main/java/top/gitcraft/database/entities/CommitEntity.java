package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "commit")
public class CommitEntity {
    @DatabaseField(generatedId = true)
    public int rowId;

    @DatabaseField
    public int playerCommitId;

    @DatabaseField
    public int pid;

    @DatabaseField
    public String commitName;
}
