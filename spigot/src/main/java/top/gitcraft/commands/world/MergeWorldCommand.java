package top.gitcraft.commands.world;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.*;
import top.gitcraft.GitCraft;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;

public class MergeWorldCommand {


    public void createMergeWorld(String worldName,int layerheight) {
        /*MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();

        //adding the world creation
        worldManager.addWorld(worldName, World.Environment.NORMAL, null, WorldType.NORMAL, false, "gitcraft."+layerheight);
*/
        Runnable callback = () -> {

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
