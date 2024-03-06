package top.gitcraft.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static top.gitcraft.utils.SchematicUtils.*;

public class MergeUtils {

    public static void pasteMergeAreas(Player player, String targetWorldName, String fromWorldName, String mergeWorldName, CuboidRegion region) {
        World fromWorld = BukkitAdapter.adapt(Bukkit.getWorld(fromWorldName));
        World targetWorld = BukkitAdapter.adapt(Bukkit.getWorld(targetWorldName));
        World voidWorld = BukkitAdapter.adapt(Bukkit.getWorld(mergeWorldName));

        BlockArrayClipboard fromClipboard = createClipboard(region, fromWorld);
        BlockArrayClipboard targetClipboard = createClipboard(region, targetWorld);
        BlockArrayClipboard changesClipboard = createClipboardFromChanges(player);

        MergeMetaData coords = new MergeMetaData(region);

        pasteClipboard(voidWorld, player, coords.getRegionCombined().getPos1(), changesClipboard);
        pasteClipboard(voidWorld, player, coords.getRegionFrom().getPos1(), targetClipboard);
        pasteClipboard(voidWorld, player, coords.getRegionTo().getPos1(), fromClipboard);

        TeleportUtils.joinWorldAtCurrentLocation(player, mergeWorldName);
    }
}

