package top.gitcraft.commands.world;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.*;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.onarandombox.MultiverseCore.api.WorldPurger;
import top.gitcraft.GitCraft;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.*;

import java.util.ArrayList;
import java.util.Collections;

public class MergeWorldCommand {


    public void createMergeWorld(String worldName,int layerheight) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();
        WorldPurger purger = worldManager.getTheWorldPurger();

        //adding the world creation
        /*worldManager.addWorld(worldName, World.Environment.NORMAL, null, WorldType.NORMAL, false, "gitcraft."+layerheight);
*/
        Runnable callback = () -> {
            MultiverseWorld mergeWorld = worldManager.getMVWorld(worldName);
            mergeWorld.setGameMode(GameMode.CREATIVE);
            mergeWorld.setAllowAnimalSpawn(false);
            mergeWorld.setAllowMonsterSpawn(false);
            /*ArrayList<String> thingsToKill = new ArrayList<String>();
            thingsToKill.add("ALL");
            purger.purgeWorld(mergeWorld, thingsToKill, false, false);*/
            purger.purgeWorld(mergeWorld);
        };

        createWorldSendCallback(worldName, layerheight, callback);
    }

    public static void createWorldSendCallback(String worldName, int layerheight, Runnable callback) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();

        //adding the world creation

        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            worldManager.addWorld(worldName, World.Environment.NORMAL, null, WorldType.NORMAL, false, "gitcraft." + layerheight);
            if (callback != null) {
                callback.run();
            }

        });
    }
}
