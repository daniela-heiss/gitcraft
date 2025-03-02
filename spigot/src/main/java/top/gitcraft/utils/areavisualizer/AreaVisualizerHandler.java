package top.gitcraft.utils.areavisualizer;

import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handler to create a particle box with AreaVisualizer
 *
 * @apiNote With this handler instances of AreaVisualizer
 * can be created and destroyed. The handler keeps track of
 * instances and links them to a player. Each player can only
 * have one instance active at a time. The instances are
 * destroyed if a player leaves.
 */
public class AreaVisualizerHandler {
    private static AreaVisualizerHandler instance;
    private final Map<UUID, AreaVisualizer> playerVisualizers;

    private AreaVisualizerHandler() {
        this.playerVisualizers = new HashMap<>();
    }

    /* Creating a singleton to hold all active instances
     *
     * Must be instantiated once in onEnable()
     */
    public static synchronized AreaVisualizerHandler getInstance() {
        if (instance == null) {
            instance = new AreaVisualizerHandler();
        }
        return instance;
    }

    public void createVisualizeArea(Player player, Region region) {
        UUID uuid = player.getUniqueId();

        if (playerVisualizers.containsKey(uuid)) {
            destroyVisualizeArea(uuid);
        }
        BlockVector pos1 =
                new BlockVector(region.getMinimumPoint().getX(), region.getMinimumPoint().getY(),
                        region.getMinimumPoint().getZ());
        BlockVector pos2 =
                new BlockVector(region.getMaximumPoint().getX(), region.getMaximumPoint().getY(),
                        region.getMaximumPoint().getZ());

        AreaVisualizer<Object> visualizer = new AreaVisualizer<>();
        visualizer.visualizeCubeBoundaries(Bukkit.getPlayer(uuid).getWorld(), pos1, pos2);
        playerVisualizers.put(uuid, visualizer);
    }

    /**
     * Method creating an instance of VisualizeArea
     * and mapping it to a player (UUID)
     *
     * @param uuid The UUID of the player
     * @param pos1 First corner of the particle box
     * @param pos2 Second corner of the particle box
     * @apiNote Use this to create a particle box at a specific location with the default settings
     */
    public void createVisualizeArea(UUID uuid, BlockVector pos1, BlockVector pos2) {
        if (playerVisualizers.containsKey(uuid)) {
            destroyVisualizeArea(uuid);
        }

        AreaVisualizer<Object> visualizer = new AreaVisualizer<>();
        visualizer.visualizeCubeBoundaries(Bukkit.getPlayer(uuid).getWorld(), pos1, pos2);
        playerVisualizers.put(uuid, visualizer);
    }

    /**
     * Method creating an instance of VisualizeArea
     * and mapping it to a player (UUID)
     *
     * @param uuid     The UUID of the player
     * @param pos1     First corner of the particle box
     * @param pos2     Second corner of the particle box
     * @param period   Interval at which particles are spawned in ticks (20 ticks = 1s)
     * @param particle What particle should be displayed.
     * @param data     Data regarding the particle (e.g. color and size).
     * @param force    If the particles should be forced. The users particle settings
     *                 are ignored and the particles are rendered up to 256 block away.
     * @apiNote Use this to create a particle box at a specific location and custom options
     */
    public <T> void createVisualizeArea(UUID uuid, BlockVector pos1, BlockVector pos2, int period,
                                        Particle particle, double spacing, T data, boolean force) {
        destroyVisualizeArea(uuid);

        AreaVisualizer<T> visualizer =
                new AreaVisualizer<T>(period, particle, spacing, data, force);
        visualizer.visualizeCubeBoundaries(Bukkit.getPlayer(uuid).getWorld(), pos1, pos2);
        playerVisualizers.put(uuid, visualizer);
    }

    /**
     * Method used to destroy the instance mapped to a player
     *
     * @param uuid The UUID of the player
     */
    public void destroyVisualizeArea(UUID uuid) {
        if (playerVisualizers.containsKey(uuid)) {
            playerVisualizers.get(uuid).removeVisualizeAreaSelection();
            playerVisualizers.remove(uuid);
        }
    }
}
