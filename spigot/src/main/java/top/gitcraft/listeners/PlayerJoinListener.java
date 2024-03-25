package top.gitcraft.listeners;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import top.gitcraft.GitCraft;
import top.gitcraft.utils.TeleportUtils;

import java.util.logging.Logger;

import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class PlayerJoinListener implements Listener {
    private static final Logger logger = GitCraft.getPlugin(GitCraft.class).getLogger();

    @EventHandler public void onPlayerJoin(PlayerJoinEvent event) {

        World world = event.getPlayer().getWorld();
        //check if the world folder exists
        if (!world.getWorldFolder().exists()) {
            TeleportUtils.joinWorldAtWorldSpawn(event.getPlayer(), "world");
            dispatchTellRawCommand(event.getPlayer(),
                    "The world you were in does not exist anymore. You have been teleported to the default world.");
        }
    }
}
