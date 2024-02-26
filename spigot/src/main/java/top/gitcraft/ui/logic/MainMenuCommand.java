package top.gitcraft.ui.logic;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.ui.components.Menu;

import static top.gitcraft.ui.components.Menu.menuMainMenu;
import static top.gitcraft.ui.components.WorldList.worldListAll;
import static top.gitcraft.utils.ExecuteConsoleCommand.dispatchTellRawCommand;

public class MainMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;
        dispatchTellRawCommand(player, menuMainMenu());
        return true;
    }
}
