package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.ui.components.WorldList;

public class WorldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        if (args.length == 0) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new WorldList().displayAll("join"));
            return true;
        }
        switch (args[0]) {
            case "delete":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new WorldList().displayAll("delete"));
                return true;
            case "create":
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new WorldList().displayAll("create"));
                return true;
            default:
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new WorldList().displayAll("join"));
                return true;
        }
    }
}
