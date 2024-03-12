package top.gitcraft.commands.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import top.gitcraft.utils.WorldUtils;

public class createVoidWorldCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel,
                             String[] args) {

        String mergeWorldName = "mergeWorld" + System.currentTimeMillis();
        String layerHeight = args[0];

        try {
            if (args.length == 0) {
                WorldUtils.createVoidWorld(mergeWorldName, 0);
                return true;
            }

            //is +64 cause the world starts at -64
            WorldUtils.createVoidWorld(mergeWorldName, Integer.parseInt(layerHeight) + 64);

            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}