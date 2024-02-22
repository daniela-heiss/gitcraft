package top.gitcraft;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extension.platform.Platform;
import org.bukkit.plugin.java.JavaPlugin;
import top.gitcraft.commands.LoadCommand;
import top.gitcraft.database.DatabaseManager;

import java.sql.SQLException;
import java.util.Objects;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {

        getLogger().info("Hello, SpigotMC!");

        try {
            Platform platform = WorldEdit.getInstance().getPlatformManager().getPlatforms().getLast();
            if (platform != null) {
                getLogger().info("WorldEdit platform: " + platform.getPlatformName());
            } else {
                getLogger().warning("WorldEdit platform is null!");
            }
            Objects.requireNonNull(this.getCommand("load")).setExecutor(new LoadCommand());
            Objects.requireNonNull(this.getCommand("gcsave")).setExecutor(new GCSave());
            Objects.requireNonNull(this.getCommand("setBlockExample")).setExecutor(new testWorldEdit());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye, SpigotMC!");
    }
}
