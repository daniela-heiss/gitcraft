package top.gitcraft.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class MessageUtils {

    public static String infoMessage(Player player, String message) {
        player.sendMessage(ChatColor.YELLOW + message);
        return ChatColor.YELLOW + message;
    }

    public static String successMessage(Player player, String message) {
        player.sendMessage(ChatColor.GREEN + message);
        return ChatColor.GREEN + message;
    }

    public static String errorMessage(Player player, String message) {
        player.sendMessage(ChatColor.RED + message);
        return ChatColor.RED + message;
    }
}
