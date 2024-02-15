package top.gitcraft.commands;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Remove implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("I will destroy!");

        Location loc = new Location(Bukkit.getWorld("world"), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
        loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:air"), true);

        return true;
    }
}