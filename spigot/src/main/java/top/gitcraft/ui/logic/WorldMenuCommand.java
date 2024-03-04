package top.gitcraft.ui.logic;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static top.gitcraft.ui.components.Menu.menuWorldMenu;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class WorldMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;
        dispatchTellRawCommand(player, menuWorldMenu());
        return true;
    }
}
