package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BranchJoinListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            String jsonMessage = "[\"\","
                    + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                    + "{\"text\":\"\\u2550\\u2550\",\"bold\":true,\"color\":\"white\"},"
                    + "{\"text\":\" Git\",\"bold\":true,\"color\":\"red\"},"
                    + "{\"text\":\"Craft\",\"bold\":true,\"color\":\"gold\"},"
                    + "{\"text\":\" \\u2550\\u2550\\n\\nBranch List\",\"bold\":true,\"color\":\"white\"},"
                    + "{\"text\":\"\\n\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\",\"bold\":true},"
                    + "{\"text\":\"\\n\\n\\u2554\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin world\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to join world\"}},"
                    + "{\"text\":\"JOIN\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin world\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to join world\"}},"
                    + "{\"text\":\"] world\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin world\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to join world\"}},"
                    + "{\"text\":\"\\n\\u2560\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin world2\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to join world2\"}},"
                    + "{\"text\":\"JOIN\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin world2\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to join world2\"}},"
                    + "{\"text\":\"] world2\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin world2\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to join world2\"}},"
                    + "{\"text\":\"\\n\\u2560\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin world3\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to join world3\"}},"
                    + "{\"text\":\"JOIN\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin world3\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to join world3\"}},"
                    + "{\"text\":\"] world3\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin world3\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to join world3\"}},"
                    + "{\"text\":\"\\n\\u255a\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin EXAMPLEBRANCH\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to join EXAMPLEBRANCH\"}},"
                    + "{\"text\":\"JOIN\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin EXAMPLEBRANCH\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to join EXAMPLEBRANCH\"}},"
                    + "{\"text\":\"] EXAMPLEBRANCH\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin EXAMPLEBRANCH\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to join EXAMPLEBRANCH\"}},"
                    + "{\"text\":\"\\n\\n\\n\\n\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}},"
                    + "{\"text\":\"Branch Menu\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}},"
                    + "{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}},"
                    + "{\"text\":\"\\n \"}]";


            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);

            return true;
        }
        else{
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

    }
}
