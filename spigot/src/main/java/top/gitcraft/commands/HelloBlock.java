package top.gitcraft.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

public class HelloBlock implements TabExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (sender instanceof Player) {
            sender.sendMessage("Hello World!");

            Player player = (Player) sender;

            // if no arguments, use player's location
            if (args.length == 0) {  
                args = new String[]{"~", "~", "~"};
            }

            // convert ~ ~ ~ to player's location
            if(args[0].equals("~")){
                args[0] = String.valueOf(player.getLocation().getBlockX());
            }
            if(args[1].equals("~")){
                args[1] = String.valueOf(player.getLocation().getBlockY()-1);
            }
            if(args[2].equals("~")){
                args[2] = String.valueOf(player.getLocation().getBlockZ());
            }

            Location loc = new Location(Bukkit.getWorld("world"), Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[2]));
            loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:grass_block[snowy=true]"), true);

            return true;
        }
        sender.sendMessage("You must be a player to use this command!");
        return false;
    }

	@Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return null; // Return null for non-player senders
        }

        List<String> completions = new ArrayList<>();

        Player player = (Player) sender;

        if (args.length == 1) {
            completions.add("~ ~ ~"); // Add relative coordinates suggestion

            // only autocomplete if targeted block is not air
            if (!player.getTargetBlock(null, 5).getType().isAir()) {
                completions.add(String.format("%.0f %.0f %.0f",
                            (double) player.getTargetBlock(null, 5).getX(),
                            (double) player.getTargetBlock(null, 5).getY(),
                            (double) player.getTargetBlock(null, 5).getZ())); // Convert integers to doubles
            }
        }
        if (args.length == 2) {
            completions.add("~ ~"); // Add relative coordinates suggestion

            if (!player.getTargetBlock(null, 5).getType().isAir()) {
                completions.add(String.format("%.0f %.0f",
                            (double) player.getTargetBlock(null, 5).getY(),
                            (double) player.getTargetBlock(null, 5).getZ())); // Convert integers to doubles
            }
        }
        if (args.length == 3) {
            completions.add("~"); // Add relative coordinates suggestion

            if (!player.getTargetBlock(null, 5).getType().isAir()){
                completions.add(String.format("%.0f",
                            (double) player.getTargetBlock(null, 5).getZ())); // Convert integers to doubles

            }
        }
        return completions;
    }
}