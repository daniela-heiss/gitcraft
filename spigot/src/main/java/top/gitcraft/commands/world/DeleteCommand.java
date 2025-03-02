package top.gitcraft.commands.world;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;
import top.gitcraft.utils.WorldUtils;
import top.gitcraft.utils.enums.JSONCOLOR;
import top.gitcraft.utils.enums.LISTTYPE;

import java.util.Objects;
import java.util.logging.Logger;

import static top.gitcraft.ui.components.InfoMessages.*;
import static top.gitcraft.ui.components.WorldList.worldListAll;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class DeleteCommand implements CommandExecutor {

    private static final Logger logger = GitCraft.getPlugin(GitCraft.class).getLogger();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        // No world provided
        if (args.length == 0) {
            dispatchTellRawCommand(player, worldListAll(LISTTYPE.DELETE, 1));
            return true;
        }
        if(Objects.equals(args[0], ":") && args.length > 1 && !args[1].isEmpty()){
            dispatchTellRawCommand(player, worldListAll(LISTTYPE.DELETE, Integer.parseInt(args[1])));
            return true;
        }
        String worldName = args[0];
        // "world" is protected from accidental deletion
        if (Objects.equals(args[0], "world")) {
            dispatchTellRawCommand(player, infoWorldAction(JSONCOLOR.RED, "world", "is protected and will not be deleted"));
            return true;
        }
        if (Bukkit.getWorld(worldName) == null) {
            return false;
        }
        logger.info("Deleting " + worldName);
        dispatchTellRawCommand(player, infoActionWorld(JSONCOLOR.RED, "Deleting", worldName));
        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> WorldUtils.deleteWorld(player, Bukkit.getWorld(worldName)));

        return true;
    }
}
