package top.gitcraft.commands.merging;

import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static top.gitcraft.utils.BlockUtils.getBlockChangedByPlayers;
import static top.gitcraft.utils.CubeUtils.regionFromList;
import static top.gitcraft.utils.MergeUtils.pasteMergeAreas;

public class AutoMergeCommand implements CommandExecutor {

    @Override public boolean onCommand(CommandSender sender, Command command, String label,
                                       String[] strings) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return false;
        }
        Player player = (Player) sender;

        if (strings.length != 3) {
            player.sendMessage(
                    "Usage: /autoMerge <fromWorldName> <targetWorldName> " + "<mergeWorldName>");
            return true;
        }

        String fromWorldName = strings[0];
        String targetWorldName = strings[1];
        String mergeWorldName = strings[2];

        //        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        //        String schematicName = "AutoMerge" + timestamp.getTime();

        //        World currentWorld = BukkitAdapter.adapt(player.getWorld());
        String worldName = player.getWorld().getName();
        CuboidRegion region = regionFromList(getBlockChangedByPlayers(worldName));

        //        BlockArrayClipboard clipboard = createClipboard(region, currentWorld);
        //        player.sendMessage("Copied region to clipboard");

        //        saveClipboardAsSchematic(clipboard, schematicName);
        //        return pasteClipboardAndJoin(clipboard, player, "world", region.getPos1());

        player.sendMessage("AutoMerging " + fromWorldName + " into " + targetWorldName + " via " +
                mergeWorldName);

        if (region == null) {
            player.sendMessage(ChatColor.RED + "No changes detected");
        } else {
            pasteMergeAreas(player, fromWorldName, targetWorldName, mergeWorldName, region);
        }
        return true;
    }
}
