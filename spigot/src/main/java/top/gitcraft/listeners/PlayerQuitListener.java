package top.gitcraft.listeners;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import top.gitcraft.utils.WorldUtils;
import top.gitcraft.utils.areavisualizer.AreaVisualizerHandler;

import java.util.UUID;

/**
 * Class containing the eventHandler destroying the
 * instance mapped to a player on PlayerQuitEvent
 */
public class PlayerQuitListener implements Listener {

    /**
     * EventHandler destroying the
     * instance mapped to a player on PlayerQuitEvent
     *
     * @param event PlayerQuitEvent
     */
    @EventHandler private void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        AreaVisualizerHandler.getInstance().destroyVisualizeArea(uuid);

        //get world
        World world = player.getWorld();

        if (world.getName().startsWith("mergeWorld")) {
            //delete the world
            WorldUtils.deleteWorld(null, world);
        }
    }
}
