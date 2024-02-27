package top.gitcraft.utils.methods;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import top.gitcraft.ui.components.Menu;

public class ExecuteConsoleCommand {
    public static void dispatchTellRawCommand(Player player, String jsonMessage){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + jsonMessage);
    }
}
