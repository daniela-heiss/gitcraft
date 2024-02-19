package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.daos.MaterialMapDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.BlockDataMapEntity;
import top.gitcraft.database.entities.BlockEntity;
import top.gitcraft.database.entities.MaterialMapEntity;
import top.gitcraft.database.entities.WorldEntity;

import java.sql.SQLException;

public class LoadCommand implements CommandExecutor {
    private final MaterialMapDao mapDao;
    private final WorldDao worldDao;
    private final BlockDao blockDao;

    public LoadCommand() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        mapDao = databaseManager.getMaterialMapDao();
        worldDao = databaseManager.getWorldDao();
        blockDao = databaseManager.getBlockDao();
    }

    public void revertLine(BlockEntity zeile, String direction) {   //time 0 = rollback, time 1 = restore
        MaterialMapEntity block;
        WorldEntity world;

        try {
            block = mapDao.getMaterialById(zeile.type);
            world = worldDao.getWorldById(zeile.worldId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Location loc = new Location(Bukkit.getServer().getWorld(world.worldName), zeile.x, zeile.y, zeile.z);

        if (zeile.action == 1 && direction.equals("past")){
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);
        } else if (zeile.action == 0 && direction.equals("past")){
            loc.getBlock().setBlockData(Bukkit.createBlockData(block.material), true);
        } else if (zeile.action == 1 && direction.equals("future")){
            loc.getBlock().setBlockData(Bukkit.createBlockData(block.material), true);
        } else if (zeile.action == 0 && direction.equals("future")){
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);
        } else {
            Bukkit.broadcastMessage("Interact");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Loading commit...");

        BlockEntity zeile;

        try {
            zeile = blockDao.getBlockById(10329);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        int commitID = Integer.parseInt(args[0]);
        String direction = "past";

        int currentCommit = 5; //dummy for sql
        int difference = commitID - currentCommit;

        if (difference > 0){
            direction = "future";
        } else if (difference < 0){
            direction = "past";
        } else {
            sender.sendMessage("Failed to load commit");
        }

        revertLine(zeile, direction);
        /*for (int i = 0; i < commits[0].length; i++){
            revertLine(commits[i], direction);
        }*/
        return true;
    }
}