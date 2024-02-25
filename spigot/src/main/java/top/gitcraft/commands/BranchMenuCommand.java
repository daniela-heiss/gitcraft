package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BranchMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

        String jsonMessage = "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"\\u2550\\u2550\",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\" Git\",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\"Craft\",\"bold\":true,\"color\":\"gold\"},"
                + "{\"text\":\" \\u2550\\u2550\\n\\nBranch Menu\",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"\\n\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\",\"bold\":true},"
                + "{\"text\":\"\\n\\n\\u2554\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoinlist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Join a branch\"}},{\"text\":\"JOIN\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoinlist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Join a branch\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoinlist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Join a branch\"}},{\"text\":\"\\n\\u2560\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchcreatelist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Create a new branch\"}},{\"text\":\"CREATE\",\"bold\":true,\"color\":\"aqua\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchcreatelist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Create a new branch\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchcreatelist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Create a new branch\"}},{\"text\":\"\\n\\u2560\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmergemenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open merge menu\"}},{\"text\":\"MERGE\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmergemenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open merge menu\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmergemenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open merge menu\"}},{\"text\":\"\\n\\u2551\\n\\u255a\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdeletelist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Delete a branch\"}},{\"text\":\"DELETE\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdeletelist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Delete a branch\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdeletelist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Delete a branch\"}},{\"text\":\"\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open main menu\"}},{\"text\":\"Main Menu\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open main menu\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open main menu\"}},{\"text\":\"\\n \"}]";

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);

        return true;
    }
}
