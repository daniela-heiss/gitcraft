package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BranchMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            String jsonMessage = "[\"\","
                    + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                    + "{\"text\":\"\\u2550\\u2550\",\"bold\":true,\"color\":\"white\"},"
                    + "{\"text\":\" Git\",\"bold\":true,\"color\":\"red\"},"
                    + "{\"text\":\"Craft\",\"bold\":true,\"color\":\"gold\"},"
                    + "{\"text\":\" \\u2550\\u2550\\n\\nBranch Menu\",\"bold\":true,\"color\":\"white\"},"
                    + "{\"text\":\"\\n\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\",\"bold\":true},"
                    + "{\"text\":\"\\n\\n\\n\\u2554\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Join a branch\"}}},"
                    + "{\"text\":\"JOIN\",\"bold\":true,\"color\":\"aqua\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Join a branch\"}}},"
                    + "{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoin\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Join a branch\"}}},"
                    + "{\"text\":\"\\n\\u2560\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchcreate\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Create a new branch\"}}},"
                    + "{\"text\":\"CREATE\",\"bold\":true,\"color\":\"dark_green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchcreate\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Create a new branch\"}}},"
                    + "{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchcreate\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Create a new branch\"}}},"
                    + "{\"text\":\"\\n\\u2560\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"color\":\"gold\"},{\"text\":\"MERGE\",\"bold\":true,\"color\":\"gold\"},{\"text\":\"]\",\"bold\":true},"
                    + "{\"text\":\"\\n\\u2551\\n\\u255a\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Delete a branch\"}}},{\"text\":\"DELETE\",\"bold\":true,\"color\":\"dark_red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Delete a branch\"}}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Delete a branch\"}}},"
                    + "{\"text\":\"\\n\\n\\n\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Open main menu\"}}},{\"text\":\"Main Menu\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Open main menu\"}}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":{\"text\":\"Open main menu\"}}},"
                    + "{\"text\":\"\\n \"}]";


            // Send the Tellraw-formatted message to the sender
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);


//            String branchName = args[0];
//            sender.sendMessage("Branch " + branchName + " created!");
//
//            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
//
//            MVWorldManager worldManager = core.getMVWorldManager();
//            worldManager.cloneWorld("world", args[0]);
//
//            MultiverseWorld[] worlds = worldManager.getMVWorlds().toArray(new MultiverseWorld[0]);

            return true;
        }
        else{
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

    }

}
