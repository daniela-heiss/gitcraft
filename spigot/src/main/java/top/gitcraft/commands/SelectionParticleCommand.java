package top.gitcraft.commands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;

public class SelectionParticleCommand {
    class Box {
        private final BlockVector3 a;
        private final BlockVector3 b;
        private final BlockVector3 c;
        private final BlockVector3 d;
        private final BlockVector3 e;
        private final BlockVector3 f;
        private final BlockVector3 g;
        private final BlockVector3 h;


        Box(BlockVector3 pos1, BlockVector3 pos2){
            a = pos1;
            g = pos2;

            b = BlockVector3.at(a.getX(), a.getY(), g.getZ());
            c = BlockVector3.at(a.getX(), a.getY(), g.getZ());



        }
        /*public void getVectors(BlockVector3 pos1, BlockVector3 pos2){

        }*/
    }

}
