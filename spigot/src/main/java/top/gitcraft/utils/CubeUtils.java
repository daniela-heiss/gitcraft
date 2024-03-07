package top.gitcraft.utils;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import top.gitcraft.database.entities.BlockEntity;

import java.util.ArrayList;
import java.util.List;

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


    public static CuboidRegion regionFromList(List<BlockEntity> list) {
        if (list.isEmpty()) {
            return null;
        }

        int minX = list.get(0).x;
        int minY = list.get(0).y;
        int minZ = list.get(0).z;
        int maxX = minX;
        int maxY = minY;
        int maxZ = minZ;

        for (BlockEntity entity : list) {
            minX = Math.min(minX, entity.x);
            minY = Math.min(minY, entity.y);
            minZ = Math.min(minZ, entity.z);
            maxX = Math.max(maxX, entity.x);
            maxY = Math.max(maxY, entity.y);
            maxZ = Math.max(maxZ, entity.z);
        }

        BlockVector3 minVector = BlockVector3.at(minX, minY, minZ);
        BlockVector3 maxVector = BlockVector3.at(maxX, maxY, maxZ);

        return new CuboidRegion(minVector, maxVector);
    }


    public static List<BlockEntity> getBlocksInRegion(List<BlockEntity> list, CuboidRegion region) {
        List<BlockEntity> areaBlocks = new ArrayList<>(list.size());
        for (BlockEntity blockEntity : list) {
            if (region.contains(BlockVector3.at(blockEntity.x, blockEntity.y, blockEntity.z))) {
                areaBlocks.add(blockEntity);
            }
        }
        return areaBlocks;
    }
}
