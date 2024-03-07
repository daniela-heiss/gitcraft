package top.gitcraft.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import static top.gitcraft.utils.CubeUtils.expandCube;
import static top.gitcraft.utils.SchematicUtils.*;

public class MergeUtils {

    public static void pasteMergeAreas(Player player, String targetWorldName, String fromWorldName,
                                       String mergeWorldName, CuboidRegion region) {

        //expanding the section by 5 in each dimension
        region = expandCube(region, 5);
        creatVoidWorld(mergeWorldName);

        World fromWorld = BukkitAdapter.adapt(Bukkit.getWorld(fromWorldName));
        World targetWorld = BukkitAdapter.adapt(Bukkit.getWorld(targetWorldName));
        World voidWorld = BukkitAdapter.adapt(creatVoidWorld(mergeWorldName));

        BlockArrayClipboard fromClipboard = createClipboard(region, fromWorld);
        BlockArrayClipboard targetClipboard = createClipboard(region, targetWorld);
        BlockArrayClipboard changesClipboard = createClipboardFromChanges(player);

        MergeMetaData coords = new MergeMetaData(region);

        //the changed blocks only the region in the "from" world and the region in the target world
        pasteClipboard(voidWorld, player, coords.getRegionCombined().getPos1(), changesClipboard);
        pasteClipboard(voidWorld, player, coords.getRegionFrom().getPos1(), fromClipboard);
        pasteClipboard(voidWorld, player, coords.getRegionTo().getPos1(), targetClipboard);

        TeleportUtils.joinWorldAtCurrentLocation(player, mergeWorldName);
    }

    private static org.bukkit.World creatVoidWorld(String worldName) {
        WorldCreator wc = new WorldCreator(worldName);
        wc.type(WorldType.FLAT);
        wc.generatorSettings(
                "{\"layers\": [{\"block\": \"barrier\", \"height\": 1}], \"biome\":\"desert\"}");
        wc.generateStructures(false);
        return wc.createWorld();
    }
}

