package top.gitcraft.commands.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.WorldUtils;

import java.util.Objects;

import static top.gitcraft.ui.components.Info.infoNoWorldNameProvided;
import static top.gitcraft.ui.components.Info.infoWorldIsProtected;
import static top.gitcraft.utils.ExecuteConsoleCommand.dispatchTellRawCommand;

public class DeleteCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        // No world provided
        if (args.length == 0) {
            dispatchTellRawCommand(player, infoNoWorldNameProvided());
            return true;
        }
        // "world" is protected from accidental deletion
        if (Objects.equals(args[0], "world")) {
            dispatchTellRawCommand(player, infoWorldIsProtected("world"));
            return true;
        }
        new WorldUtils().deleteWorld(player, args[0]);
        return true;
    }


}
