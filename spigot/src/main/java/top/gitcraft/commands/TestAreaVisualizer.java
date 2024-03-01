package top.gitcraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import top.gitcraft.utils.areavisualizer.AreaVisualizerHandler;

import java.util.UUID;

public class TestAreaVisualizer implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        AreaVisualizerHandler instance = AreaVisualizerHandler.getInstance();
        UUID uuid = ((Player) sender).getUniqueId();
        switch (args[0]){
            case "1":
                instance.createVisualizeArea(uuid, new BlockVector(0, 100, 0), new BlockVector(10, 200, 200));
                return true;
            case "2":
                instance.createVisualizeArea(uuid, new BlockVector(20, 100, 20), new BlockVector(30, 110, 30));
                return true;
            default:
                instance.destroyVisualizeArea(uuid);
                return true;
        }
    }
}
