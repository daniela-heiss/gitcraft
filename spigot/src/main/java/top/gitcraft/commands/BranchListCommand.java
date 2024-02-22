package top.gitcraft.commands;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class BranchListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length == 0){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gcbranchjoinlist");
            return true;
        }

        if(Objects.equals(args[0], "delete")){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gcbranchdeletelist");
        } else if (Objects.equals(args[0], "create")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gcbranchcreatelist");
        } else{
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "gcbranchjoinlist");
        }

        return true;

    }
//
//    public String generateDynamicMessage(CommandSender sender, List<World> worlds, String action, String color) {
//        String corner = "{\"text\":\"\\n\",\"bold\":true},{\"text\":\"\\u2554\",\"color\":\"white\"}";
//        String middle = "{\"text\":\"\\n\",\"bold\":true},{\"text\":\"\\u2560\",\"color\":\"white\"}";
//        String end = "{\"text\":\"\\n\",\"bold\":true},{\"text\":\"\\u255a\",\"color\":\"white\"}";
//
//        StringBuilder jsonMessage = new StringBuilder("[\"\","
//                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
//                + "{\"text\":\"\\u2550\\u2550\",\"bold\":true,\"color\":\"white\"},"
//                + "{\"text\":\" Git\",\"bold\":true,\"color\":\"red\"},"
//                + "{\"text\":\"Craft\",\"bold\":true,\"color\":\"gold\"},"
//                + "{\"text\":\" \\u2550\\u2550\\n\\nBranch List\",\"bold\":true,\"color\":\"white\"},"
//                + "{\"text\":\"\\n\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\n\",\"bold\":true}");
//
//        if (!worlds.isEmpty()) {
//            jsonMessage.append(corner)
//                    .append("{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(action).append(" ").append(worlds.get(0).getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(action).append(" ").append(worlds.get(0).getName()).append("\"}}");
//            jsonMessage.append(",{\"text\":\"").append(action.toUpperCase()).append("\",\"bold\":true,\"color\":\"").append(color).append("\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(action).append(" ").append(worlds.get(0).getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(action).append(" ").append(worlds.get(0).getName()).append("\"}}");
//            jsonMessage.append(",{\"text\":\"] ").append(worlds.get(0).getName()).append("\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(action).append(" ").append(worlds.get(0).getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(action).append(" ").append(worlds.get(0).getName()).append("\"}}");
//
//            worlds.remove(0);
//
//            for (World world : worlds) {
//                jsonMessage.append(middle)
//                        .append("{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(action).append(" ").append(world.getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(action).append(" ").append(world.getName()).append("\"}}");
//                jsonMessage.append(",{\"text\":\"").append(action.toUpperCase()).append("\",\"bold\":true,\"color\":\"").append(color).append("\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(action).append(" ").append(world.getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(action).append(" ").append(world.getName()).append("\"}}");
//                jsonMessage.append(",{\"text\":\"] ").append(world.getName()).append("\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(action).append(" ").append(world.getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(action).append(" ").append(world.getName()).append("\"}}");
//            }
//
//            if (!worlds.isEmpty()) {
//                jsonMessage.append(end)
//                        .append("{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(action).append(" ").append(worlds.get(worlds.size() - 1).getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(action).append(" ").append(worlds.get(worlds.size() - 1).getName()).append("\"}}");
//                jsonMessage.append(",{\"text\":\"").append(action.toUpperCase()).append("\",\"bold\":true,\"color\":\"").append(color).append("\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(action).append(" ").append(worlds.get(worlds.size() - 1).getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(action).append(" ").append(worlds.get(worlds.size() - 1).getName()).append("\"}}");
//                jsonMessage.append(",{\"text\":\"] ").append(worlds.get(worlds.size() - 1).getName()).append("\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(action).append(" ").append(worlds.get(worlds.size() - 1).getName()).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(action).append(" ").append(worlds.get(worlds.size() - 1).getName()).append("\"}}");
//            }
//        }
//
//        jsonMessage.append(",{\"text\":\"\\n\\n\\n\",\"bold\":true},"
//                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}},"
//                + "{\"text\":\"Branch Menu\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}},"
//                + "{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}}");
//
//        jsonMessage.append(",{\"text\":\"\\n \"}]");
//
//        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);
//
//        return jsonMessage.toString();
//    }

}
