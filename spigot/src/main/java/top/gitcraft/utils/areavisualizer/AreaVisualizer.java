package top.gitcraft.utils.areavisualizer;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockVector;
import top.gitcraft.GitCraft;

/**
 * Used to create an instance of a particle box
 *
 * @apiNote Do not use directly. Create instance with
 * AreaVisualizerHandler. Instances created directly are
 * not bound to a player and will not be destroyed on
 * PlayerQuitEvent.
 */
public class AreaVisualizer {

    private BukkitTask task;
    private int period = 10;
    private Particle particle = Particle.FLAME;
    private boolean force = true;

    /**
     * Empty constructor of AreaVisualizer.
     * Creates a particle box every 10 ticks (0.5 sec)
     * with particle flame and force flag set to true.
     */
    public AreaVisualizer() {
    }

    /**
     * Constructor of AreaVisualizer with parameters
     *
     * @param period   Interval at which particles are spawned in ticks (20 ticks = 1s).
     * @param particle What particle should be displayed.
     * @param force    If the particles should be forced. The users particle settings
     *                 are ignored and the particles are rendered up to 256 block away.
     * @apiNote A low period and force particle may lead to severely decreased performance.
     */
    public AreaVisualizer(int period, Particle particle, boolean force) {
        this.period = period;
        this.particle = particle;
        this.force = force;
    }

    /**
     * Will destroy the AreaVisualizer instance if
     * no further reference exists.
     */
    public void removeVisualizeAreaSelection() {
        if (task != null) {
            task.cancel();
        }
    }

    /**
     * Method to create a new particle box
     *
     * @param world What world the particles are spawned in
     * @param pos1  First corner of the particle box
     * @param pos2  Second corner of the particle box
     * @apiNote This method schedules a task on a background thread.
     * If the class instance is not kept track of, the task can only
     * be cancelled by canceling all plugin tasks potentially leading
     * to side effects<br>
     * <code>plugin.getServer().getScheduler().cancelTasks(plugin);</code>
     */
    public void visualizeCubeBoundaries(World world, BlockVector pos1, BlockVector pos2) {
        Plugin plugin = GitCraft.getPlugin(GitCraft.class);

        Location corner1 = new Location(world, pos1.getX(), pos1.getY(), pos1.getZ());
        Location corner2 = new Location(world, pos2.getX(), pos2.getY(), pos2.getZ());

        // Bukkit task running on a background thread spawning particles at a specified interval
        this.task = new BukkitRunnable() {
            @Override
            public void run() {
                // Vector edges of the particle box
                spawnEdgeParticles(corner1, new Location(corner1.getWorld(), corner2.getX(), corner1.getY(), corner1.getZ()));
                spawnEdgeParticles(corner1, new Location(corner1.getWorld(), corner1.getX(), corner2.getY(), corner1.getZ()));
                spawnEdgeParticles(corner1, new Location(corner1.getWorld(), corner1.getX(), corner1.getY(), corner2.getZ()));
                spawnEdgeParticles(corner2, new Location(corner1.getWorld(), corner1.getX(), corner2.getY(), corner2.getZ()));
                spawnEdgeParticles(corner2, new Location(corner1.getWorld(), corner2.getX(), corner1.getY(), corner2.getZ()));
                spawnEdgeParticles(corner2, new Location(corner1.getWorld(), corner2.getX(), corner2.getY(), corner1.getZ()));
                spawnEdgeParticles(new Location(corner1.getWorld(), corner2.getX(), corner1.getY(), corner1.getZ()), new Location(corner1.getWorld(), corner2.getX(), corner2.getY(), corner1.getZ()));
                spawnEdgeParticles(new Location(corner1.getWorld(), corner1.getX(), corner2.getY(), corner1.getZ()), new Location(corner1.getWorld(), corner2.getX(), corner2.getY(), corner1.getZ()));
                spawnEdgeParticles(new Location(corner1.getWorld(), corner1.getX(), corner1.getY(), corner2.getZ()), new Location(corner1.getWorld(), corner2.getX(), corner1.getY(), corner2.getZ()));
                spawnEdgeParticles(new Location(corner1.getWorld(), corner1.getX(), corner2.getY(), corner2.getZ()), new Location(corner1.getWorld(), corner1.getX(), corner1.getY(), corner2.getZ()));
                spawnEdgeParticles(new Location(corner1.getWorld(), corner2.getX(), corner1.getY(), corner1.getZ()), new Location(corner1.getWorld(), corner2.getX(), corner1.getY(), corner2.getZ()));
                spawnEdgeParticles(new Location(corner1.getWorld(), corner1.getX(), corner2.getY(), corner2.getZ()), new Location(corner1.getWorld(), corner1.getX(), corner2.getY(), corner1.getZ()));
            }
        }.runTaskTimer(plugin, 0, period); // Spawning particle at specified period in ticks (20 ticks = 1s)
    }

    /**
     * Method responsible for spawning the particles
     *
     * @param start Starting coordinate of the vector
     * @param end   End coordinate of the vector
     */
    private void spawnEdgeParticles(Location start, Location end) {
        double particleSpacing = 0.5; // Adjust spacing between particles
        double distance = start.distance(end);
        double vectorX = (end.getX() - start.getX()) / distance;
        double vectorY = (end.getY() - start.getY()) / distance;
        double vectorZ = (end.getZ() - start.getZ()) / distance;

        // Iterate along the edge and spawn particles
        for (double d = 0; d <= distance; d += particleSpacing) {
            Location particleLoc = new Location(start.getWorld(), start.getX() + vectorX * d, start.getY() + vectorY * d, start.getZ() + vectorZ * d);
            start.getWorld().spawnParticle(particle, particleLoc, 0, 0, 0, 0, 0, null, force);
        }
    }
}
