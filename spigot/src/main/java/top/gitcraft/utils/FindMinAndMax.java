package top.gitcraft.utils;

import top.gitcraft.database.entities.BlockEntity;

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
}
