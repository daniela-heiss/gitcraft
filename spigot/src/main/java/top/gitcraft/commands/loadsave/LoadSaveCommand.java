package top.gitcraft.commands.loadsave;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.enums.LISTTYPE;

import static top.gitcraft.ui.components.SaveList.saveListAll;
import static top.gitcraft.ui.components.WorldList.worldListAll;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class LoadSaveCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        // Open join list if no arguments are provided
        if (args.length == 0) {
            dispatchTellRawCommand(player, saveListAll(LISTTYPE.LOAD, player.getName()));
            return true;
        }
        switch (args[0]) {
            case "deletesave":
                dispatchTellRawCommand(player, saveListAll(LISTTYPE.DELETESAVE, player.getName()));
                return true;
           /* case "save":
                dispatchTellRawCommand(player, saveListAll(LISTTYPE.SAVE, player.getName()));
                return true;*/
            // default: "join"
            default:
                dispatchTellRawCommand(player, saveListAll(LISTTYPE.LOAD, player.getName()));
                return true;
        }
    }
}
