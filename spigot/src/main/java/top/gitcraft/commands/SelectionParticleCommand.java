package top.gitcraft.commands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;

public class SelectionParticleCommand {
    class Block {
        private final BlockVector3 a;
        private final BlockVector3 b = null;
        private final BlockVector3 c = null;
        private final BlockVector3 d = null;
        private final BlockVector3 e = null;
        private final BlockVector3 f = null;
        private final BlockVector3 g;
        private final BlockVector3 h = null;


        Block(BlockVector3 pos1, BlockVector3 pos2){
            a = pos1;
            g = pos2;

            b = new BlockVector3(a.getX(), a.getY(), g.getZ());



        }
        /*public void getVectors(BlockVector3 pos1, BlockVector3 pos2){

        }*/
    }

}
