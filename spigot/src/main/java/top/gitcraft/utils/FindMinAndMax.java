package top.gitcraft.utils;

import com.sk89q.worldedit.math.BlockVector3;
import top.gitcraft.database.entities.BlockEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FindMinAndMax {
    public static Double[] findMin(List<BlockEntity> list) {
        int minX = 0;
        int minY = 0;
        int minZ = 0;

        Double[] coordinates = new Double[3];

        for (int i = 0; i < list.size() - 1; i++) {
            if (i == 0) {
                minX = list.get(i).x;
                minY = list.get(i).y;
                minZ = list.get(i).z;
            } else {
                if (list.get(i).x < minX) {
                    minX = list.get(i).x;
                }
                if (list.get(i).y < minY) {
                    minY = list.get(i).y;
                }
                if (list.get(i).z < minZ) {
                    minZ = list.get(i).z;
                }
            }
        }
        coordinates[0] = (double) minX;
        coordinates[1] = (double) minY;
        coordinates[2] = (double) minZ;

        return coordinates;
    }

    public static Double[] findMax(List<BlockEntity> list) {
        int maxX = 0;
        int maxY = 0;
        int maxZ = 0;

        Double[] coordinates = new Double[3];

        for (int i = 0; i < list.size() - 1; i++) {
            if (i == 0) {
                maxX = list.get(i).x;
                maxY = list.get(i).y;
                maxZ = list.get(i).z;
            } else {
                if (list.get(i).x > maxX) {
                    maxX = list.get(i).x;
                }
                if (list.get(i).y > maxY) {
                    maxY = list.get(i).y;
                }
                if (list.get(i).z > maxZ) {
                    maxZ = list.get(i).z;
                }
            }
        }
        coordinates[0] = (double) maxX;
        coordinates[1] = (double) maxY;
        coordinates[2] = (double) maxZ;

        return coordinates;
    }

    public static List<BlockEntity> findArea(List<BlockEntity> list,BlockVector3 startCoordinates, BlockVector3 endCoordinates) {
        
        double startX = startCoordinates.getX();
        double startY = startCoordinates.getY();
        double startZ = startCoordinates.getZ();

        double endX = endCoordinates.getX();
        double endY = endCoordinates.getY();
        double endZ = endCoordinates.getZ();

        List<BlockEntity> areaBlocks = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++) {
            if (list.get(i).x >= startX && list.get(i).x <= endX && list.get(i).y >= startY && list.get(i).y <= endY && list.get(i).z >= startZ && list.get(i).z <= endZ) {
            //if ((list.get(i).x >= startX && list.get(i).y >= startY && list.get(i).z >= startZ) && (list.get(i).x <= endX && list.get(i).y <= endY && list.get(i).z <= endZ)) {
                areaBlocks.add(list.get(i));
            }
        }
        return areaBlocks;
        
    }
}
