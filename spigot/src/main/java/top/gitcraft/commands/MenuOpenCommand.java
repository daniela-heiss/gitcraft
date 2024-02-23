package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MenuOpenCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            String jsonMessage = "[\"\","
                    + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                    + "{\"text\":\"\\u2550\\u2550\",\"bold\":true,\"color\":\"white\"},"
                    + "{\"text\":\" Git\",\"bold\":true,\"color\":\"red\"},"
                    + "{\"text\":\"Craft\",\"bold\":true,\"color\":\"gold\"},"
                    + "{\"text\":\" \\u2550\\u2550\\n\\nMain Menu\",\"bold\":true,\"color\":\"white\"},"
                    + "{\"text\":\"\\n\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\",\"bold\":true},"
                    + "{\"text\":\"\\n\\n\\n\\u2554\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Opens the branch menu\"}},"
                    + "{\"text\":\"Branch-Menu\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Opens the branch menu\"}},"
                    + "{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Opens the branch menu\"}},"
                    + "{\"text\":\"\\n\\u2551\\n\\u255a\"},"
                    + "{\"text\":\"[\",\"bold\":true},{\"text\":\"Config\",\"bold\":true,\"color\":\"yellow\"},{\"text\":\"]\",\"bold\":true},"
                    + "{\"text\":\"\\n\\n\\n\\n\\n\"}]";

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);

            return true;
        }
        else{
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

    }
}
