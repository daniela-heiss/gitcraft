package top.gitcraft.utils;

import com.sk89q.worldedit.regions.CuboidRegion;

public class MergeMetaData {

    private final CuboidRegion regionFrom;
    private final CuboidRegion regionCombined;
    private final CuboidRegion regionTo;

    public MergeMetaData(CuboidRegion origin) {
        int xLength = origin.getWidth();
        int margin = 10;

        regionFrom = new CuboidRegion(origin.getPos1().subtract(xLength + margin, 0, 0),
                origin.getPos2().subtract(xLength + margin, 0, 0));
        regionCombined = new CuboidRegion(origin.getPos1(), origin.getPos2());

        regionTo = new CuboidRegion(origin.getPos1().add(xLength + margin, 0, 0),
                origin.getPos2().add(xLength + margin, 0, 0));
    }

    public CuboidRegion getRegionFrom() {
        return regionFrom;
    }

    public CuboidRegion getRegionCombined() {
        return regionCombined;
    }

    public CuboidRegion getRegionTo() {
        return regionTo;
    }
}
