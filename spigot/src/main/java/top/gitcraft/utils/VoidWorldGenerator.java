package top.gitcraft.utils;

import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.SpawnCategory;

import java.time.Instant;

import static org.bukkit.Bukkit.getWorld;

public class VoidWorldGenerator {
    public static World createMergeWorld(int layerHeight) {
        String newName = "Merge" + Instant.now().getEpochSecond(); //generating new name for the mergeworld
        WorldCreator wc = new WorldCreator(newName);
        wc.type(WorldType.FLAT);
        wc.generatorSettings("{\"layers\": [{\"block\": \"air\", \"height\": "+layerHeight+"},{\"block\": \"barrier\", \"height\": 1}], \"biome\":\"desert\"}");
        wc.generateStructures(false);
        World mergeWorldBukkit = wc.createWorld();
        mergeWorldBukkit.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        mergeWorldBukkit.setGameRule(GameRule.DO_MOB_SPAWNING, false);
        //add new gamerules here if they are deemed necessary. yes i also hate that there is no combined one for everything

        return mergeWorldBukkit;
    }
}
