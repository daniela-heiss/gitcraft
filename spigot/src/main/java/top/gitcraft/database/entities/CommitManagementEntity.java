package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "commitManagement")
public class CommitManagementEntity {

    @DatabaseField(generatedId = true)
    public int rowid;

    @DatabaseField
    public int pid;

    @DatabaseField
    public int currentID;

    @DatabaseField
    public int highestID;


}
