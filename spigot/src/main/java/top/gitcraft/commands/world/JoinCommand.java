package top.gitcraft.commands.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.enums.JSONCOLOR;
import top.gitcraft.utils.enums.LISTTYPE;

import java.util.Objects;

import static top.gitcraft.ui.components.InfoMessages.infoContent;
import static top.gitcraft.ui.components.WorldList.worldListAll;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;
import static top.gitcraft.utils.TeleportUtils.joinWorldAtCurrentLocation;
import static top.gitcraft.utils.TeleportUtils.joinWorldAtWorldSpawn;

/**
 * JoinCommand
 * This class is responsible for handling the /gcjoin command.
 */
public class JoinCommand implements CommandExecutor {

    /**
     * This method is called when the /gcjoin command is executed.
     * It is responsible for handling the command and joining the player to the specified world.
     *
     * @param args worldName created
     * @return true if the command was executed successfully
     */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            dispatchTellRawCommand(player, worldListAll(LISTTYPE.JOIN, 1));
            return true;
        }
        if(Objects.equals(args[0], ":") && args.length > 1 && !args[1].isEmpty()){
            dispatchTellRawCommand(player, worldListAll(LISTTYPE.JOIN, Integer.parseInt(args[1])));
            return true;
        }
        String worldName = args[0];
        boolean created = args.length > 1 && Boolean.parseBoolean(args[1]);

        if (created) {
            joinWorldAtCurrentLocation(player, worldName);
            return true;
        }
        joinWorldAtWorldSpawn(player, worldName);
        return true;
    }
}
