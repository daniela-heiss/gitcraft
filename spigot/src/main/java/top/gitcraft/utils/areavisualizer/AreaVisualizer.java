package top.gitcraft.utils.areavisualizer;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.BlockVector;
import top.gitcraft.GitCraft;

public class AreaVisualizer {

    private BukkitTask task;

    public boolean removeVisualizeAreaSelection() {
        if (task != null) {
            task.cancel();
            return true;
        }
        return false;
    }

    public boolean createVisualizeAreaSelection(World world, BlockVector pos1, BlockVector pos2) {
        Plugin plugin = GitCraft.getPlugin(GitCraft.class);

        // Define the corner coordinates of the cube
        Location corner1 = new Location(world, pos1.getX(), pos1.getY(), pos1.getZ());
        Location corner2 = new Location(world, pos2.getX(), pos2.getY(), pos2.getZ());

        // Schedule a task to spawn particles for each edge of the cube
        this.task = new BukkitRunnable() {
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
