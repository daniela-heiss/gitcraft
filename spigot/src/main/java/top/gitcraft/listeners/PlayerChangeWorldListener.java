package top.gitcraft.listeners;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import top.gitcraft.GitCraft;
import top.gitcraft.utils.WorldUtils;

import java.util.logging.Logger;

public class PlayerChangeWorldListener implements Listener {
    private static final Logger logger = GitCraft.getPlugin(GitCraft.class).getLogger();

    @EventHandler public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        World world = event.getFrom();
        if (world.getName().startsWith("mergeWorld") && world.getPlayers().isEmpty()) {
            //check if world is empty
            WorldUtils.deleteWorld(null, world);
        }
    }
}
