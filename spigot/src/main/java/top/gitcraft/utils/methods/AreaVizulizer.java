package top.gitcraft.utils.methods;

import com.sk89q.worldedit.math.Vector3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;
import top.gitcraft.GitCraft;

public class AreaVizulizer{
    private final GitCraft plugin;
    private boolean particlesActive = false; // Flag to track if particles are currently active

    public AreaVizulizer(GitCraft plugin) {
        this.plugin = plugin;
    }

    public boolean visualizeAreaSelection(World world, Vector3 pos1, Vector3 pos2) {

        if (particlesActive) {
            // If particles are active, cancel the task and set particlesActive to false
            Bukkit.getScheduler().cancelTasks(plugin);
            particlesActive = false;
        } else {
            // Define the corner coordinates of the cube
            Location corner1 = new Location(world, pos1.getX(), pos1.getY(), pos1.getZ());
            Location corner2 = new Location(world, pos2.getX(), pos2.getY(), pos2.getZ());

            // Schedule a task to spawn particles for each edge of the cube
            new BukkitRunnable() {
                @Override
                public void run() {
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
            }.runTaskTimer(plugin, 0, 10); // Spawn particles every 10 ticks (0.5 seconds)

            particlesActive = true; // Set particlesActive to true
        }
        return true;
    }

    // Method to spawn particles along an edge between two locations
    private void spawnEdgeParticles(Location start, Location end) {
        double particleSpacing = 0.5; // Adjust spacing between particles
        double distance = start.distance(end);
        double vectorX = (end.getX() - start.getX()) / distance;
        double vectorY = (end.getY() - start.getY()) / distance;
        double vectorZ = (end.getZ() - start.getZ()) / distance;

        // Iterate along the edge and spawn particles
        for (double d = 0; d <= distance; d += particleSpacing) {
            Location particleLoc = new Location(start.getWorld(), start.getX() + vectorX * d, start.getY() + vectorY * d, start.getZ() + vectorZ * d);
            start.getWorld().spawnParticle(Particle.FLAME, particleLoc.getX(), particleLoc.getY(), particleLoc.getZ(), 0, 0, 0, 0, 0, null, true);
        }
    }
}
