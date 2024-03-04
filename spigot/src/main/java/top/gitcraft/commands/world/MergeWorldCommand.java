package top.gitcraft.commands.world;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.onarandombox.MultiverseCore.*;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldType;

public class MergeWorldCommand {


    public void createMerge(String worldName,int layerheight) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();

        //adding the world creation
        worldManager.addWorld(worldName, World.Environment.NORMAL, null, WorldType.NORMAL, false, "gitcraft."+layerheight);




    }
}
