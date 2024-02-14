package net.main;

public class CommitData
{
    public String world;
    public int x;
    public int y;
    public int z;
    public String block;
    public String[] blockData;
    public int action;

    public CommitData(String world, int x, int y, int z, String block, String[] blockData, int action) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.block = block;
        this.blockData = blockData;
        this.action = action;
    }
}
