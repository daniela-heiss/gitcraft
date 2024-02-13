package net.main;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class HelloBlock implements CommandExecutor {
    // Logger log = Logger.getLogger("Minecraft");

    // @Override
    // public void onEnable() {
    //     log.info("Your plugin has been enabled!");
    // }

    // @Override
    // public void onDisable() {
    //     log.info("Your plugin has been disabled.");
    // }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("helloworld")) {
            sender.sendMessage("Hello World!");

            Location loc = new Location(Bukkit.getWorld("world"), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:grass_block[snowy=true]"), true);

            return true;
        }
        return false;
    }
}