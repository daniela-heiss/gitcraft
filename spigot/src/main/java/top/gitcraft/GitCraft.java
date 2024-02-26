package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;

import top.gitcraft.commands.*;

import java.sql.SQLException;
import java.util.Objects;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {

        getLogger().info("Hello, SpigotMC!");

        Objects.requireNonNull(this.getCommand("gcmenu")).setExecutor(new MenuOpenCommand());
        Objects.requireNonNull(this.getCommand("gcworldmenu")).setExecutor(new WorldMenuCommand());
        Objects.requireNonNull(this.getCommand("gcworld")).setExecutor(new WorldCommand());
        Objects.requireNonNull(this.getCommand("gcworldjoin")).setExecutor(new WorldJoinCommand());
        Objects.requireNonNull(this.getCommand("gcworldcreate")).setExecutor(new WorldCreateCommand());
        Objects.requireNonNull(this.getCommand("gcworlddelete")).setExecutor(new WorldDeleteCommand());
        Objects.requireNonNull(this.getCommand("gcWETest")).setExecutor(new WETestCommand());

        try {
            Objects.requireNonNull(this.getCommand("merge")).setExecutor(new MergeCommand());
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
