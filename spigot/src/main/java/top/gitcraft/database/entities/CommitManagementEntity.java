package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "commitManagement")
public class CommitManagementEntity {
    @DatabaseField
    public String playerName;

    @DatabaseField
    public int currentID;
    @DatabaseField
    public int highestID;


}
