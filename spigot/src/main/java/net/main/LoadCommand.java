package net.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LoadCommand implements CommandExecutor {

    public void revertLine(Object[] zeile, String direction){   //time 0 = rollback, time 1 = restore
        int x = (Integer) zeile[0];
        int y = (Integer) zeile[1];
        int z = (Integer) zeile[2]; // need world in array
        Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
        String block = (String) zeile[3];
        String blockState = (String) zeile[4];
        int action = (Integer) zeile[5];

        if (action == 1 && direction.equals("past")){
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);
        } else if (action == 0 && direction.equals("past")){
            loc.getBlock().setBlockData(Bukkit.createBlockData(block+"["+blockState+"]"), true);
        } else if (action == 1 && direction.equals("future")){
            loc.getBlock().setBlockData(Bukkit.createBlockData(block+"["+blockState+"]"), true);
        } else if (action == 0 && direction.equals("future")){
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);
        } else {
            Bukkit.broadcastMessage("Interact");
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Loading commit...");
        //sql queries

        Object[][] commits = {{132, 75, -105, "minecraft:grass_block", "snowy=true", 1},
                {133, 75, -105, "minecraft:grass_block", "snowy=true", 1},
                {134, 75, -105, "minecraft:grass_block", "snowy=true", 1},
                {135, 75, -105, "minecraft:grass_block", "snowy=true", 1},
                {136, 75, -105, "minecraft:grass_block", "snowy=true", 1},
                {137, 75, -105, "minecraft:grass_block", "snowy=true", 1}};

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

        for (int i = 0; i < commits[0].length; i++){
            revertLine(commits[i], direction);
        }
        return true;
    }
}