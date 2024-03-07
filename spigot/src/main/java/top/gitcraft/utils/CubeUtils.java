package top.gitcraft.utils;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;

public class CubeUtils {
    public static CuboidRegion expandCube(CuboidRegion region, int growthAmount) {
        if (region == null) {
            return null;
        }

        Vector3 centerVector = region.getCenter();
        BlockVector3 center =
                (BlockVector3.at(centerVector.getX(), centerVector.getY(), centerVector.getZ()));

        int length = region.getLength() + growthAmount;
        int width = region.getWidth() + growthAmount;
        int height = region.getHeight() + growthAmount;

        BlockVector3 growth = BlockVector3.at(length, height, width).divide(2);

        BlockVector3 newPos1 = center.subtract(growth);
        BlockVector3 newPos2 = center.add(growth);

        return new CuboidRegion(newPos1, newPos2);
    }
}
