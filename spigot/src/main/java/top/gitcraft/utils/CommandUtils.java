package top.gitcraft.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class CommandUtils {
    public static void dispatchTellRawCommand(Player player, String jsonMessage) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + player.getName() + " " + jsonMessage);
    }
}
