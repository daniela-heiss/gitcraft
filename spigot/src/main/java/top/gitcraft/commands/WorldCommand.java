package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.ui.components.WorldList;

import static top.gitcraft.ui.components.WorldList.worldListAll;
import static top.gitcraft.utils.ExecuteConsoleCommand.dispatchTellRawCommand;

public class WorldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        // No arguments: "join"
        if (args.length == 0) {
            dispatchTellRawCommand(player, worldListAll("join"));
            return true;
        }
        switch (args[0]) {
            case "delete":
                dispatchTellRawCommand(player, worldListAll("delete"));
                return true;
            case "create":
                dispatchTellRawCommand(player, worldListAll("create"));
                return true;
                // default: "join"
            default:
                dispatchTellRawCommand(player, worldListAll("join"));
                return true;
        }
    }
}
