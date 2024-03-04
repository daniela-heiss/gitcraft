package top.gitcraft.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static top.gitcraft.utils.SchematicUtils.createClipboard;

public class MergeUtils {
    public void pasteMergeAreas(Player player, String toWorldName, String fromWorldName, BlockVector3 pos1, BlockVector3 pos2) {
        World fromWorld = BukkitAdapter.adapt(Bukkit.getWorld(fromWorldName));
        World toWorld = BukkitAdapter.adapt(Bukkit.getWorld(toWorldName));

        BlockArrayClipboard fromClipboard = createClipboard(pos1, pos2, fromWorld, player);
        BlockArrayClipboard toClipboard = createClipboard(pos1, pos2, toWorld, player);
    }
}
