package top.gitcraft.commands.world;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.entity.SpawnCategory;
import top.gitcraft.GitCraft;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.*;

import java.time.Instant;

import static org.bukkit.Bukkit.getWorld;

public class createVoidWorldCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        Player player = (Player) sender;
        player.sendMessage("Create void world...");
        if(args.length == 0){
            createMergeWorld(0);
        }else{
            createMergeWorld(Integer.parseInt(args[0])+64);//is +64 cause the world starts at -64
        }
        return true;
    }

    public void createMergeWorld(int layerHeight) {
        String newName = "Merge" + Instant.now().getEpochSecond(); //generating new name for the mergeworld
        //runnable for gamerules to set for the world
        Runnable callback = () -> {
            World mergeWorldBukkit = getWorld(newName);
            mergeWorldBukkit.setTicksPerSpawns(SpawnCategory.valueOf("ANIMAL"),0);
            mergeWorldBukkit.setTicksPerSpawns(SpawnCategory.valueOf("MONSTER"),0);
            mergeWorldBukkit.setTicksPerSpawns(SpawnCategory.valueOf("AXOLOTL"),0);
            mergeWorldBukkit.setTicksPerSpawns(SpawnCategory.valueOf("WATER_AMBIENT"),0);
            mergeWorldBukkit.setTicksPerSpawns(SpawnCategory.valueOf("WATER_ANIMAL"),0);
            mergeWorldBukkit.setTicksPerSpawns(SpawnCategory.valueOf("WATER_UNDERGROUND_CREATURE"),0);
            //add new gamerules here if they are deemed necessary. yes i also hate that there is no combined one for everything
        };

        createWorldSendCallback(newName,layerHeight, callback);
    }

    public static void createWorldSendCallback(String newName,int layerHeight, Runnable callback) {
        //adding the world creation

        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            WorldCreator wc = new WorldCreator(newName);
            wc.type(WorldType.FLAT);
            wc.generatorSettings("{\"layers\": [{\"block\": \"air\", \"height\": "+layerHeight+"},{\"block\": \"barrier\", \"height\": 1}], \"biome\":\"desert\"}");
            wc.generateStructures(false);
            wc.createWorld();
            if (callback != null) {
                callback.run();
            }

        });
    }
}
