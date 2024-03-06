package top.gitcraft.commands.merging;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.WorldUtils;
import top.gitcraft.utils.WorldUtils.*;

import java.sql.Timestamp;

import static top.gitcraft.utils.SchematicUtils.*;

public class ConfirmMergeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 0){
            player.sendMessage("Please give a target world");
            return false;
        }
        String targetWorld = args[0];

        World currentWorld = BukkitAdapter.adapt(player.getWorld());
        String worldName = player.getWorld().getName();

        BlockVector3 pos1 = ;
        BlockVector3 pos2;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String schematicName = "AutoMerge" + timestamp.getTime();

        BlockArrayClipboard clipboard = createClipboard(pos1, pos2, currentWorld, player);
        player.sendMessage("Copied region to clipboard");

        saveClipboardAsSchematic(clipboard, schematicName, player);

        pasteClipboardAndJoin(clipboard, player, targetWorld, pos1);

        WorldUtils worldUtils = new WorldUtils();

        worldUtils.deleteWorld(player, worldName);

        return true;
    }
}
