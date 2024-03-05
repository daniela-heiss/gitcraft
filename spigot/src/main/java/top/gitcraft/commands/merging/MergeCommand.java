package top.gitcraft.commands.merging;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.enums.LISTTYPE;

import static top.gitcraft.ui.components.Menu.menuMergeMenu;
import static top.gitcraft.ui.components.WorldList.worldListAll;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class MergeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;
        String arg;
        if(args.length == 0) {
            arg = "true";
            dispatchTellRawCommand(player, menuMergeMenu(arg));
            return true;
        }
        arg = args[0];
        dispatchTellRawCommand(player, menuMergeMenu(arg));
        return true;
    }
}
