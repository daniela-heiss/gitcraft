package top.gitcraft.utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;

import java.util.Arrays;
import java.util.Random;


public class MergeWGenerator extends ChunkGenerator {

    private int layerHeight = 0;

    public MergeWGenerator(String id) {
        layerHeight = Integer.parseInt(id);//actually needed(afaik) cause the generator is called by string as a command. i know its cursed af
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {

        ChunkData chunk = createChunkData(world);

        chunk.setRegion(0, layerHeight, 0, 16, layerheight + 1, 16, Material.BARRIER);
        return chunk;
    }

    @Override
    public Location getFixedSpawnLocation(World world, Random random) {
        if (!world.isChunkLoaded(0, 0)) {
            world.loadChunk(0, 0);
        }

        int highestBlock = world.getHighestBlockYAt(0, 0);
        return new Location(world, 0, highestBlock+1, 0);
    }
}