package top.gitcraft.utils;

import com.sk89q.worldedit.regions.CuboidRegion;

public class MergeMetaData {

    // the changed blocks only (on the left of preview)
    private final CuboidRegion changesRegion;

    // the preview aka the incoming (center)
    private final CuboidRegion previewRegion;

    // the target (on the right of preview)
    private final CuboidRegion targetRegion;

    public MergeMetaData(CuboidRegion origin) {
        int xLength = origin.getWidth();
        int margin = 10;

        previewRegion = origin;
        changesRegion = transformToChangesRegion(origin, xLength, margin);
        targetRegion = transformToTargetRegion(origin, xLength, margin);
    }

    public static CuboidRegion transformToTargetRegion(CuboidRegion region, int xLength,
                                                       int margin) {
        return new CuboidRegion(region.getPos1().add(xLength + margin, 0, 0),
                region.getPos2().add(xLength + margin, 0, 0));
    }

    public static CuboidRegion transformToChangesRegion(CuboidRegion changesRegion, int xLength,
                                                        int margin) {
        return new CuboidRegion(changesRegion.getPos1().subtract(xLength + margin, 0, 0),
                changesRegion.getPos2().subtract(xLength + margin, 0, 0));
    }

    public CuboidRegion getChangesRegion() {
        return changesRegion;
    }

    public CuboidRegion getPreviewRegion() {
        return previewRegion;
    }

    public CuboidRegion getTargetRegion() {
        return targetRegion;
    }
}
