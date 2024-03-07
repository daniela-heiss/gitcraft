package top.gitcraft.commands.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static top.gitcraft.utils.VoidWorldGenerator.createMergeWorld;

public class createVoidWorldCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if(sender instanceof Player) {
            sender.sendMessage("Create void world...");
        }
        if(args.length == 0){
            createMergeWorld(0);
            return true;
        }
        String layerheight = args[0];
        createMergeWorld(Integer.parseInt(layerheight)+64);//is +64 cause the world starts at -64

        return true;
    }
}