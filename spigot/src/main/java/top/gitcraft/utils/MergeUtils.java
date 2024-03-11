package top.gitcraft.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;

import static top.gitcraft.listeners.AreaSelectListener.setPos1;
import static top.gitcraft.listeners.AreaSelectListener.setPos2;
import static top.gitcraft.ui.components.Menu.confirmMerge;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;
import static top.gitcraft.utils.SchematicUtils.*;

public class MergeUtils {

    public static void pasteMergeAreas(Player player, String fromWorldName, String targetWorldName,
                                       String mergeWorldName, CuboidRegion region) {

        //expanding the section by 5 in each dimension
        //        region = expandCube(region, 5);
        //get timestamp to create a unique world name
        mergeWorldName = "mergeWorld" + System.currentTimeMillis();
        creatVoidWorld(mergeWorldName);

        World fromWorld = BukkitAdapter.adapt(Bukkit.getWorld(fromWorldName));
        World targetWorld = BukkitAdapter.adapt(Bukkit.getWorld(targetWorldName));
        World voidWorld = BukkitAdapter.adapt(creatVoidWorld(mergeWorldName));

        //the region in the "from" world, (center)
        BlockArrayClipboard fromClipboard = createClipboard(region, fromWorld);
        pasteClipboard(voidWorld, player, fromClipboard.getOrigin(), fromClipboard);

        //the region in the "to" world (right)
        BlockArrayClipboard targetClipboard = createClipboard(region, targetWorld);
        int width = region.getWidth();
        BlockVector3 targetOrigin = fromClipboard.getOrigin().add(width + 10, 0, 0);
        pasteClipboard(voidWorld, player, targetOrigin, targetClipboard);

        //the region containing the changed blocks (left)
        BlockArrayClipboard changesClipboard = createClipboardFromChanges(region, fromWorldName);
        BlockVector3 changesOrigin = changesClipboard.getOrigin().subtract(width + 10, 0, 0);
        pasteClipboard(voidWorld, player, changesOrigin, changesClipboard);

        Runnable callback = () -> {
            setPos1(player, region.getPos1());
            setPos2(player, region.getPos2());
            //            player.sendMessage("Combined Region: " + region);
            dispatchTellRawCommand(player, confirmMerge(fromWorldName, targetWorldName));
        };
        TeleportUtils.joinWorldAtCurrentLocation(player, mergeWorldName, callback);

        //        voidWorld.fixLighting();
        //        changesClipboard.getOrigin()
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

