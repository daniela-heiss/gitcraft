package top.gitcraft;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;

import java.sql.*;

import static org.bukkit.Bukkit.getLogger;

public class GCColumns {
    private DatabaseManager dbman = new DatabaseManager();
    private BlockDao dao;

    public void tableInit() {
        try
        {
            dao = dbman.getBlockDao();
           if(!dao.checkColumnExists("commitID")){
              dao.addColumn("commitID", "INT");
           }else{
               getLogger().info("Column exists already.");
           }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}