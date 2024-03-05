package top.gitcraft.utils;

import com.sk89q.worldedit.math.BlockVector3;

public class MergeMetaData {

    BlockVector3 origin = null;
    BlockVector3 areaOriginal = null;
    BlockVector3 areaChanges = null;
    BlockVector3 areaCombined = null;

    public MergeMetaData(BlockVector3 origin, BlockVector3 areaOriginal, BlockVector3 areaChanges, BlockVector3 areaCombined) {
        this.origin = origin;
        this.areaOriginal = areaOriginal;
        this.areaChanges = areaChanges;
        this.areaCombined = areaCombined;
    }

    public void setOrigin(BlockVector3 inOrigin) {
        origin = inOrigin;
    }
    public void setAreaOriginal(BlockVector3 inArea1) {
        areaOriginal = inArea1;
    }
    public void setAreaChanges(BlockVector3 inArea2) {
        areaChanges = inArea2;
    }
    public void setAreaCombined(BlockVector3 inArea3){
        areaCombined = inArea3;
    }

    public BlockVector3 getOrigin() {
        return origin;
    }
    public BlockVector3 getAreaOriginal() {
        return areaOriginal;
    }
    public BlockVector3 getAreaChanges() {
        return areaChanges;
    }
    public BlockVector3 getAreaCombined() {
        return areaCombined;
    }
}
