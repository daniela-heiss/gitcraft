package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class BranchDeleteListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

//            String jsonMessage = "[\"\","
//                    + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
//                    + "{\"text\":\"\\u2550\\u2550\",\"bold\":true,\"color\":\"white\"},"
//                    + "{\"text\":\" Git\",\"bold\":true,\"color\":\"red\"},"
//                    + "{\"text\":\"Craft\",\"bold\":true,\"color\":\"gold\"},"
//                    + "{\"text\":\" \\u2550\\u2550\\n\\nBranch List\",\"bold\":true,\"color\":\"white\"},"
//                    + "{\"text\":\"\\n\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\",\"bold\":true},"
//                    + "{\"text\":\"\\n\\n\\u2554\"},"
//                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete world\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete world\"}},{\"text\":\"DELETE\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete world\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete world\"}},{\"text\":\"] world\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete world\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete world\"}},{\"text\":\"\\n\\u2560\"},"
//                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete world2\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete world2\"}},{\"text\":\"DELETE\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete world2\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete world2\"}},{\"text\":\"] world2\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete world2\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete world2\"}},{\"text\":\"\\n\\u2560\"},"
//                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete world3\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete world3\"}},{\"text\":\"DELETE\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete world3\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete world3\"}},{\"text\":\"] world3\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete world3\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete world3\"}},{\"text\":\"\\n\\u255a\"},"
//                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete EXAMPLEBRANCH\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete EXAMPLEBRANCH\"}},{\"text\":\"DELETE\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete EXAMPLEBRANCH\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete EXAMPLEBRANCH\"}},{\"text\":\"] EXAMPLEBRANCH\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete EXAMPLEBRANCH\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete EXAMPLEBRANCH\"}},{\"text\":\"\\n\\n\\n\\n\"},"
//                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}},{\"text\":\"Branch Menu\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}},{\"text\":\"\\n\"}]";
//
//            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);

            List<World> worlds = Bukkit.getWorlds();
            String jsonMessage = generateDynamicMessage(worlds);

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);

            return true;
        }
        else{
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

    }
    private String generateDynamicMessage(List<World> worlds) {
        StringBuilder jsonMessage = new StringBuilder("[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"\\u2550\\u2550\",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\" Git\",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\"Craft\",\"bold\":true,\"color\":\"gold\"},"
                + "{\"text\":\" \\u2550\\u2550\\n\\nBranch Delete List\",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"\\n\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\n\",\"bold\":true}");

        jsonMessage.append(",{\"text\":\"\\n\",\"bold\":true},"
                + "{\"text\":\"\\u2554\",\"color\":\"white\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete ").append(worlds.get(0).getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete ").append(worlds.get(0).getName()).append("\"}},");
        jsonMessage.append("{\"text\":\"DELETE\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete ").append(worlds.get(0).getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete ").append(worlds.get(0).getName()).append("\"}},");
        jsonMessage.append("{\"text\":\"] ").append(worlds.get(0).getName()).append("\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete ").append(worlds.get(0).getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete ").append(worlds.get(0).getName()).append("\"}}");

        worlds.remove(0);
        World lastWorld = worlds.get(worlds.size()-1);
        if(!worlds.isEmpty()){
            worlds.remove(worlds.size()-1);
        }

        for (World world : worlds) {
            String worldName = world.getName();
            jsonMessage.append(",{\"text\":\"\\n\",\"bold\":true},"
                    + "{\"text\":\"\\u2560\",\"color\":\"white\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete ").append(worldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete ").append(worldName).append("\"}},");
            jsonMessage.append("{\"text\":\"DELETE\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete ").append(worldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete ").append(worldName).append("\"}},");
            jsonMessage.append("{\"text\":\"] ").append(worldName).append("\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete ").append(worldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete ").append(worldName).append("\"}}");
        }
        if(!worlds.isEmpty()){
            jsonMessage.append(",{\"text\":\"\\n\",\"bold\":true},"
                    + "{\"text\":\"\\u255a\",\"color\":\"white\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete ").append(lastWorld.getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete ").append(lastWorld.getName()).append("\"}},");
            jsonMessage.append("{\"text\":\"DELETE\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete ").append(lastWorld.getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete ").append(lastWorld.getName()).append("\"}},");
            jsonMessage.append("{\"text\":\"] ").append(lastWorld.getName()).append("\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdelete ").append(lastWorld.getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to delete ").append(lastWorld.getName()).append("\"}}");

        }

        jsonMessage.append(",{\"text\":\"\\n\\n\\n\",\"bold\":true},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}},"
                + "{\"text\":\"Branch Menu\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}},"
                + "{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}}");

        jsonMessage.append(",{\"text\":\"\\n \"}]");

        return jsonMessage.toString();
    }
}
