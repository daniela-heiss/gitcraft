package top.gitcraft.commands.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.WorldUtils;

public class createVoidWorldCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
                             String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;
        player.sendMessage("Create void world...");

        if (args.length == 0) {
            WorldUtils.createVoidWorld(0);
            return true;
        }
        try {
            String layerHeight = args[0];
            WorldUtils.createVoidWorld(
                    Integer.parseInt(layerHeight) + 64);//is +64 cause the world starts at -64
            player.sendMessage("World created");
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}