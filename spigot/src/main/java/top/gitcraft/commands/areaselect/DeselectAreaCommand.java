package top.gitcraft.commands.areaselect;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import static top.gitcraft.listeners.AreaSelectListener.setPos1;
import static top.gitcraft.listeners.AreaSelectListener.setPos2;
import top.gitcraft.utils.areavisualizer.AreaVisualizerHandler;

public class DeselectAreaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to use this command");
            return true;
        }
        Player player = (Player) commandSender;

        deselectArea(player);

        return false;
    }

    public void deselectArea(Player player){
        setPos1(player, null);
        setPos2(player, null);

        AreaVisualizerHandler instance = AreaVisualizerHandler.getInstance();
        instance.destroyVisualizeArea(player.getUniqueId());
    }

}
