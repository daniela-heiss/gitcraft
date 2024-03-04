package top.gitcraft.utils;

import com.sk89q.worldedit.math.BlockVector3;

public class MergeMetaData {

    BlockVector3 origin = null;
    BlockVector3 area1 = null;
    BlockVector3 area2 = null;
    BlockVector3 area3 = null;

    public MergeMetaData(BlockVector3 origin, BlockVector3 area1, BlockVector3 area2, BlockVector3 area3) {
        this.origin = origin;
        this.area1 = area1;
        this.area2 = area2;
        this.area3 = area3;
    }

    public void setOrigin(BlockVector3 inOrigin) {
        origin = inOrigin;
    }
    public void setOArea1(BlockVector3 inArea1) {
        area1 = inArea1;
    }
    public void setOArea2(BlockVector3 inArea2) {
        area2 = inArea2;
    }
    public void setArea3(BlockVector3 inArea3){
        area3 = inArea3;
    }

    public BlockVector3 getOrigin() {
        return origin;
    }
    public BlockVector3 getOArea1() {
        return area1;
    }
    public BlockVector3 getOArea2() {
        return area2;
    }
    public BlockVector3 getArea3() {
        return area3;
    }
}
