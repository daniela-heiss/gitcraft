package top.gitcraft.commands.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

import static top.gitcraft.utils.VoidWorldGenerator.createMergeWorld;

public class createVoidWorldCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;
        player.sendMessage("Create void world...");
        String mergeWorldName = "mergeWorld" + System.currentTimeMillis();
        if(args.length == 0){
            createMergeWorld(mergeWorldName,0);
            return true;
        }
        try {
            String layerHeight = args[0];
            createMergeWorld(mergeWorldName,Integer.parseInt(layerHeight) + 64);//is +64 cause the world starts at -64
            player.sendMessage("World created");
            return true;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}