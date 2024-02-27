package top.gitcraft.utils.methods;

import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;

import java.util.HashMap;
import java.util.Map;

public class AreaVisualizerManager{
    private static AreaVisualizerManager instance;
    private final Map<Player, AreaVisualizer> playerVisualizers;

    private AreaVisualizerManager() {
        this.playerVisualizers = new HashMap<>();
    }

    public static synchronized AreaVisualizerManager getInstance() {
        if (instance == null) {
            instance = new AreaVisualizerManager();
        }
        return instance;
    }

    public void createVisualizeArea(Player player, BlockVector pos1, BlockVector pos2) {
        if (playerVisualizers.containsKey(player)) {
            removeVisualizeArea(player);
        }

        AreaVisualizer visualizer = new AreaVisualizer();
        visualizer.createVisualizeAreaSelection(player.getWorld(), pos1, pos2);
        playerVisualizers.put(player, visualizer);
    }
    public void removeVisualizeArea(Player player){
        playerVisualizers.remove(player);
    }
}
