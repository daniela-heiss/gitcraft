package top.gitcraft.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import top.gitcraft.listeners.AreaSelectListener;
import top.gitcraft.utils.enums.JSONCOLOR;

import static top.gitcraft.ui.components.Menu.confirmMerge;
import static top.gitcraft.utils.MergeWorldText.createMergeWorldText;

public class MergeUtils {

    public static void pasteMergeAreas(Player player, String fromWorldName, String targetWorldName,
                                       String mergeWorldName, CuboidRegion region) {

        //expanding the section by 5 in each dimension
        //get timestamp to create a unique world name

        final int expandBy = 10;
        final int margin = 10;

        CuboidRegion expandedRegion = CubeUtils.expandCube(region, expandBy);

        final int width = expandedRegion.getWidth();

        mergeWorldName = "mergeWorld" + System.currentTimeMillis();
        WorldUtils.createVoidWorld(mergeWorldName, 0);

        World fromWorld = BukkitAdapter.adapt(Bukkit.getWorld(fromWorldName));
        World targetWorld = BukkitAdapter.adapt(Bukkit.getWorld(targetWorldName));
        World voidWorld = BukkitAdapter.adapt(WorldUtils.createVoidWorld(mergeWorldName, 0));

        BlockArrayClipboard fromClipboard =
                SchematicUtils.createClipboard(expandedRegion, fromWorld);
        BlockVector3 fromOrigin = fromClipboard.getOrigin().subtract(width + margin, 0, 0);
        SchematicUtils.pasteClipboard(voidWorld, player, fromOrigin, fromClipboard);
        createMergeWorldText(mergeWorldName, fromOrigin.add(width/2, 0,expandedRegion.getLength()/2).withY(fromClipboard.getMaximumPoint().getY()), fromWorldName, JSONCOLOR.GREEN);

        //the region in the "to" world
        BlockArrayClipboard targetClipboard =
                SchematicUtils.createClipboard(expandedRegion, targetWorld);
        BlockVector3 targetOrigin = fromClipboard.getOrigin().add(width + margin, 0, 0);
        SchematicUtils.pasteClipboard(voidWorld, player, targetOrigin, targetClipboard);
        createMergeWorldText(mergeWorldName, targetOrigin.add(width/2, 0,expandedRegion.getLength()/2).withY(fromClipboard.getMaximumPoint().getY()), targetWorldName, JSONCOLOR.GREEN);

        BlockArrayClipboard changesClipboard =
                SchematicUtils.createClipboardFromChanges(region, fromWorldName);
        BlockVector3 changesOrigin = changesClipboard.getOrigin();
        BlockVector3 targetPreviewOrigin = fromClipboard.getOrigin();
        SchematicUtils.pasteClipboard(voidWorld, player, targetPreviewOrigin, targetClipboard);
        SchematicUtils.pasteClipboard(voidWorld, player, changesOrigin, changesClipboard);
        createMergeWorldText(mergeWorldName, targetOrigin.add(-(width/2)-margin, 0,expandedRegion.getLength()/2).withY(fromClipboard.getMaximumPoint().getY()), "COMBINED", JSONCOLOR.GOLD);


        Runnable callback = () -> {
            AreaSelectListener.setPos1(player, region.getPos1());
            AreaSelectListener.setPos2(player, region.getPos2());
            CommandUtils.dispatchTellRawCommand(player,
                    confirmMerge(fromWorldName, targetWorldName));
        };
        TeleportUtils.joinWorldAtCurrentLocation(player, mergeWorldName, callback);

        player.setAllowFlight(true);
        player.setFlying(true);

        //get the chunks of the clipboards
        Iterable<BlockVector2> chunks;
        chunks = region.getChunks();
        //TODO: fix lighting for the other 2 areas
        voidWorld.fixLighting(chunks);
    }

    @Deprecated private static org.bukkit.World creatVoidWorld(String worldName) {
        WorldCreator wc = new WorldCreator(worldName);
        wc.type(WorldType.FLAT);
        wc.generatorSettings(
                "{\"layers\": [{\"block\": \"barrier\", \"height\": 1}], \"biome\":\"desert\"}");
        wc.generateStructures(false);
        return wc.createWorld();
    }
}

