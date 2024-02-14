package net.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LoadCommand implements CommandExecutor {

    public void revertLine(Object[] zeile, boolean time){   //time 0 = rollback, time 1 = restore
        int x = zeile[0];
        int y = zeile[1];
        int z = zeile[2]; // need world in array
        Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
        string block = zeile[3];
        string blockstate = zeile[4];
        int action = zeile[5];

        if (action == 1 && time == 0){
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);
        } else if (action == 0 && time == 0){
            loc.getBlock().setBlockData(Bukkit.createBlockData(block), true);
        } else if (action == 1 && time == 1){
            loc.getBlock().setBlockData(Bukkit.createBlockData(block), true);
        } else if (action == 0 && time == 1){
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);
        } else {
            //interact
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Loading commit...");
        //sql queries
        Object[][] commits = new Object[][];
        commits[0][0] = 123;
        commits[0][1] = 67;
        commits[0][2] = -1233;
        commits[0][3] = "minecraft:grass_block";
        commits[0][4] = "state";
        commits[0][5] = 0;

        boolean time = 0;

        if (commitID < currentCommit){
            time = 0;
        } else if (commitID > currentCommit){
            time = 1;
        } else {
            //error, your current commit = commit that you want to load
        }

        int len = commits.length;

        for (int i = 0; i < commits.length; i++){
            revertLine(commits[0][i], time);
        }

        return true;
    }
}