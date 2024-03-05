package top.gitcraft.commands.world;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.api.MultiverseWorld;
import com.onarandombox.MultiverseCore.api.WorldPurger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import top.gitcraft.GitCraft;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;
import org.bukkit.*;

import java.time.Instant;

public class createVoidWorldCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Create void world...");
        createMergeWorld(Integer.parseInt(args[0]));
        return true;
    }


    public void createMergeWorld(int layerHeight) {

        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();
        WorldPurger purger = worldManager.getTheWorldPurger();
        String newName = "Merge" + Instant.now().getEpochSecond(); //generating new name for the mergeworld
        //change gamerules on creation completion with a callback
        Runnable callback = () -> {
            MultiverseWorld mergeWorld = worldManager.getMVWorld(newName);
            mergeWorld.setGameMode(GameMode.CREATIVE);
            mergeWorld.setAllowAnimalSpawn(false);
            mergeWorld.setAllowMonsterSpawn(false);
            purger.purgeWorld(mergeWorld);
            //add new gamerules here if they are deemed necessary
        };

        createWorldSendCallback(newName,layerHeight, callback);
    }

    public static void createWorldSendCallback(String newname,int layerheight, Runnable callback) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();
        //adding the world creation

        Bukkit.getScheduler().runTask(GitCraft.getPlugin(GitCraft.class), () -> {
            worldManager.addWorld(newname, World.Environment.NORMAL, null, WorldType.NORMAL, false, "gitcraft:"+layerheight);//change "gitcraft:" if name changes
            if (callback != null) {
                callback.run();
            }

        });
    }
}
