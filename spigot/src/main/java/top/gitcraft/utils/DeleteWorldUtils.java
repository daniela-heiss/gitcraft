package top.gitcraft.utils;

import org.bukkit.*;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;

import java.io.File;
import java.io.IOException;

public class DeleteWorldUtils {
    public static void deleteWorld(Player player, World world) {

        if (Bukkit.getWorld(world.getName()) == null ) return;

        File worldFolder = Bukkit.getWorld(world.getName()).getWorldFolder();

       Bukkit.getServer().unloadWorld(world, true);

        Bukkit.getScheduler().runTaskLater(GitCraft.getPlugin(GitCraft.class), () -> {
            try {
                org.apache.commons.io.FileUtils.deleteDirectory(worldFolder);
                player.sendMessage(ChatColor.RED + "Merge World deleted");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, 60L);
    }
}
