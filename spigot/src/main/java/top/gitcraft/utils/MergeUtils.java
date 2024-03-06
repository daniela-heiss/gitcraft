package top.gitcraft.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import static top.gitcraft.utils.SchematicUtils.*;

public class MergeUtils {


    // Calculates three areas based on the player's current location in the world
    public static MergeMetaData calculateAreaCoordinates(Player player, CuboidRegion region) {

        int yLength = region.getHeight();
        int zLength = region.getLength();

        Location playerCoordinates = player.getLocation();
        double playerCoordinateX = playerCoordinates.getX();
        double playerCoordinateY = playerCoordinates.getY();
        double playerCoordinateZ = playerCoordinates.getZ();

        int bufferSizeX = 10;
        int bufferSizeZ = 5;

        // Area on the player's left
        BlockVector3 areaOriginal = BlockVector3.at(playerCoordinateX + bufferSizeX, playerCoordinateY, (playerCoordinateZ - ((double) yLength / 2)) - bufferSizeZ - zLength);

        // Area right in front of the player
        BlockVector3 areaChanges = BlockVector3.at(playerCoordinateX + bufferSizeX, playerCoordinateY, playerCoordinateZ - ((double) yLength / 2));

        // Area on the player's right
        BlockVector3 areaCombined = BlockVector3.at(playerCoordinateX + bufferSizeX, playerCoordinateY, (playerCoordinateZ + ((double) zLength / 2)) + bufferSizeZ + zLength);

        return new MergeMetaData(region.getPos1(), areaOriginal, areaChanges, areaCombined);
    }

    public static void pasteMergeAreas(Player player, String targetWorldName, String fromWorldName, String mergeWorldName, CuboidRegion region) {
        World fromWorld = BukkitAdapter.adapt(Bukkit.getWorld(fromWorldName));
        World targetWorld = BukkitAdapter.adapt(Bukkit.getWorld(targetWorldName));
        World voidWorld = BukkitAdapter.adapt(Bukkit.getWorld(mergeWorldName));

        BlockArrayClipboard fromClipboard = createClipboard(region, fromWorld);
        BlockArrayClipboard targetClipboard = createClipboard(region, targetWorld);
        BlockArrayClipboard changesClipboard = createClipboardFromChanges(player);

        MergeMetaData coords = calculateAreaCoordinates(player, region);

        pasteClipboard(voidWorld, player, coords.getAreaChanges(), changesClipboard);
        pasteClipboard(voidWorld, player, coords.getAreaOriginal(), targetClipboard);
        pasteClipboard(voidWorld, player, coords.getAreaCombined(), fromClipboard);

        TeleportUtils.joinWorldAtCurrentLocation(player, mergeWorldName);
    }
}

