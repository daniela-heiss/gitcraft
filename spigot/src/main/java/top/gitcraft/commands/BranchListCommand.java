package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class BranchListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gcbranchjoinlist");
            return true;
        }

        if(Objects.equals(args[0], "delete")){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gcbranchdeletelist");
        } else if (Objects.equals(args[0], "create")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gcbranchcreatelist");
        } else{
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gcbranchjoinlist");
        }

        return true;

    }
}
