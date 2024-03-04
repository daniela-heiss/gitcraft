package top.gitcraft.commands.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.WorldUtils;

import static top.gitcraft.ui.components.Info.infoWorldCreated;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;
import static top.gitcraft.utils.TeleportUtils.joinWorldAtCurrentLocation;

public class CreateCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;
        WorldUtils worldUtils = new WorldUtils();

        String currentWorldName = player.getWorld().getName();
        String worldName = args.length > 0 ? args[0] : worldUtils.generateWorldName(currentWorldName);
        boolean doTeleport = !(args.length > 1 && Boolean.parseBoolean(args[1]));

        player.sendMessage("Cloning world " + currentWorldName + " to " + worldName);
        player.sendMessage("Teleporting to new world: " + doTeleport);

        Runnable callback = () -> {
            dispatchTellRawCommand(player, infoWorldCreated(worldName));
            if (doTeleport) joinWorldAtCurrentLocation(player, worldName);
        };

        worldUtils.cloneWorld(currentWorldName, worldName, callback);
        worldUtils.logWorldCreate(player, worldName);

        return true;
    }
}
