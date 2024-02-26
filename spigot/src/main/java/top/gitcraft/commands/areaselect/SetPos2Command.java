package top.gitcraft.commands.areaselect;

import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static top.gitcraft.listeners.AreaSelectListener.setPos2;

public class SetPos2Command implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }
        Player player = (Player) commandSender;

        Location location = player.getLocation();
        //block below player
        BlockVector3 pos = BlockVector3.at(location.getBlockX(), location.getBlockY() - 1, location.getBlockZ());

        setPos2(player, pos);

        return false;
    }


}
