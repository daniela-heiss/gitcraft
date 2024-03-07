package top.gitcraft.commands.merging;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.WorldUtils;

import java.util.List;

import static top.gitcraft.utils.TeleportUtils.joinWorldAtCurrentLocation;

public class DiscardMergeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        if (args.length != 1) {
            return false;
        }

        String previousWorld = args[0];
        String mergeWorldName = player.getWorld().getName();

        if (Bukkit.getWorld(mergeWorldName) == null) return false;

        World currentWorld = Bukkit.getWorld(mergeWorldName);

        // Teleport all players in mergeWorld back to their previous world
        assert currentWorld != null;
        List<Player> playersInMergeWorld = currentWorld.getPlayers();

        for(Player players : playersInMergeWorld) {
            joinWorldAtCurrentLocation(players, previousWorld);
        }

        // Delete player's Merge world
        WorldUtils worldUtils = new WorldUtils();

        worldUtils.deleteWorld(player, mergeWorldName);


        return true;
    }
}
