package top.gitcraft.commands.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.ui.components.WorldList;
import top.gitcraft.utils.enums.LISTTYPE;

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

        // Open join list if no arguments are provided
        if (args.length == 0) {
            dispatchTellRawCommand(player, worldListAll(LISTTYPE.JOIN));
            return true;
        }
        switch (args[0]) {
            case "delete":
                dispatchTellRawCommand(player, worldListAll(LISTTYPE.DELETE));
                return true;
            case "create":
                dispatchTellRawCommand(player, worldListAll(LISTTYPE.CREATE));
                return true;
            // default: "join"
            default:
                dispatchTellRawCommand(player, worldListAll(LISTTYPE.JOIN));
                return true;
        }
    }
}
