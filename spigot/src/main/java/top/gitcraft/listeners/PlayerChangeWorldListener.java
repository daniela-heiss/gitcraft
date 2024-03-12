package top.gitcraft.listeners;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import top.gitcraft.utils.WorldUtils;

public class PlayerChangeWorldListener implements Listener {

    @EventHandler public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        World world = event.getFrom();
        if (world.getName().startsWith("mergeWorld") && world.getPlayers().isEmpty()) {
            //check if world is empty
            WorldUtils.deleteWorld(null, world);
        }
    }
}
