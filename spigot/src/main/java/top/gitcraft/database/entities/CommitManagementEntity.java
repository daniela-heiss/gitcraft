package top.gitcraft.database.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "commitManagement")
public class CommitManagementEntity {

    @DatabaseField(generatedId = true)
    public int rowId;

    @DatabaseField
    public int playerId;

    @DatabaseField
    public int currentId;

    @DatabaseField
    public int highestId;


}
