package top.gitcraft.utils.areavisualizer;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

/** Class containing the eventHandler destroying the
 * instance mapped to a player on PlayerQuitEvent
 */
public class PlayerQuitListener implements Listener {

    /** EventHandler destroying the
     * instance mapped to a player on PlayerQuitEvent
     *
     * @param event PlayerQuitEvent
     */
    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        AreaVisualizerHandler.getInstance().destroyVisualizeArea(uuid);
    }
}
