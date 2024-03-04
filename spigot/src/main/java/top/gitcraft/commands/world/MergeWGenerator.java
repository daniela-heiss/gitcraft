package top.gitcraft.commands.world;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.Random;


public class MergeWGenerator extends ChunkGenerator {

    private int layerheight = 0;

    public MergeWGenerator() {
        this("");
    }

    public MergeWGenerator(String id) {
        layerheight = Integer.parseInt(id);
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {

        ChunkData chunk = createChunkData(world);

        chunk.setRegion(0, layerheight, 0, 16, layerheight + 1, 16, Material.BARRIER);
        return chunk;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        if (!world.isChunkLoaded(0, 0)) {
            world.loadChunk(0, 0);
        }

        int highestBlock = world.getHighestBlockYAt(0, 0);

        if ((highestBlock <= 0) && (world.getBlockAt(0, 0, 0).getType() == Material.AIR)) {
            return new Location(world, 0, 64, 0);
        }

        return new Location(world, 0, highestBlock, 0);
    }
}