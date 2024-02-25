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
        Objects.requireNonNull(this.getCommand("gcbranchmenu")).setExecutor(new BranchMenuCommand());
        Objects.requireNonNull(this.getCommand("gcbranch")).setExecutor(new BranchCommand());
        Objects.requireNonNull(this.getCommand("gcbranchjoin")).setExecutor(new BranchJoinCommand());
        Objects.requireNonNull(this.getCommand("gcbranchdelete")).setExecutor(new BranchDeleteCommand());
        Objects.requireNonNull(this.getCommand("gcWETest")).setExecutor(new WETestCommand());

        try {
            Objects.requireNonNull(this.getCommand("merge")).setExecutor(new MergeCommand());
            Objects.requireNonNull(this.getCommand("gcbranchcreate")).setExecutor(new BranchCreateCommand());
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
