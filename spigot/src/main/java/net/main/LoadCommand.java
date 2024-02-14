package net.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class LoadCommand implements CommandExecutor {
    
    string data[] = 

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Loading commit...");

        Location loc = new Location(Bukkit.getWorld("world"), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:grass_block[snowy=true]"), true);

        return true;
    }
}