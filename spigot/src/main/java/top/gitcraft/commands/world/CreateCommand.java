package top.gitcraft.commands.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.WorldUtils;
import top.gitcraft.utils.enums.JSONCOLOR;

import static top.gitcraft.ui.components.InfoMessages.infoWorldAction;
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

        String currentWorldName = player.getWorld().getName();
        String worldName =
                args.length > 0 ? args[0] : WorldUtils.generateWorldName(currentWorldName);
        boolean doTeleport = !(args.length > 1 && Boolean.parseBoolean(args[1]));

        Runnable callback = () -> {
            dispatchTellRawCommand(player, infoWorldAction(JSONCOLOR.AQUA, worldName, "created"));
            if (doTeleport) {
                joinWorldAtCurrentLocation(player, worldName);
            }
        };

        WorldUtils.cloneWorld(currentWorldName, worldName, callback);

        return true;
    }
}
