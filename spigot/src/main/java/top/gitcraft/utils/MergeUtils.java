package top.gitcraft.utils;

import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import sun.jvm.hotspot.opto.Block;

public class MergeUtils {

    public static BlockVector3 calculateSize(BlockVector3 startOrigin, BlockVector3 endOrigin) {

        int x = endOrigin.getX() - startOrigin.getX();
        int y = endOrigin.getY() - startOrigin.getY();
        int z = endOrigin.getZ() - startOrigin.getZ();

        return BlockVector3.at(x, y, z);
    }

    // Calculates three areas based on the player's current location in the world
    public static MergeMetaData calculateAreaCoordinates(Player player, BlockVector3 startOrigin, BlockVector3 endOrigin) {

        BlockVector3 size = calculateSize(startOrigin, endOrigin);

        Location playerCoordinates = player.getLocation();
        double playerCoordinateX = playerCoordinates.getX();
        double playerCoordinateY = playerCoordinates.getY();
        double playerCoordinateZ = playerCoordinates.getZ();

        int bufferSizeX = 10;
        int bufferSizeZ = 5;

        // Area on the player's left
        BlockVector3 areaOriginal = BlockVector3.at(playerCoordinateX + bufferSizeX, playerCoordinateY, (playerCoordinateZ - ((double) size.getY() / 2)) - bufferSizeZ - size.getZ());

        // Area right in front of the player
        BlockVector3 areaChanges = BlockVector3.at(playerCoordinateX + bufferSizeX, playerCoordinateY , playerCoordinateZ - ((double) size.getY() / 2));

        // Area on the player's right
        BlockVector3 areaCombined = BlockVector3.at(playerCoordinateX + bufferSizeX, playerCoordinateY, (playerCoordinateZ + ((double) size.getY() / 2)) + bufferSizeZ + size.getZ());

        return new MergeMetaData(startOrigin, areaOriginal, areaChanges, areaCombined);
    }
}
