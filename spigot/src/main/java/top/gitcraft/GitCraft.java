package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;

import top.gitcraft.commands.*;

import java.util.Objects;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {

        getLogger().info("Hello, SpigotMC!");

        //try {
            //Objects.requireNonNull(this.getCommand("load")).setExecutor(new LoadCommand());
            Objects.requireNonNull(this.getCommand("gcmenu")).setExecutor(new MenuOpenCommand());
            Objects.requireNonNull(this.getCommand("gcbranchmenu")).setExecutor(new BranchMenuCommand());
        Objects.requireNonNull(this.getCommand("gcbranchjoin")).setExecutor(new BranchJoinCommand());
        Objects.requireNonNull(this.getCommand("gcbranchcreate")).setExecutor(new BranchCreateCommand());
        Objects.requireNonNull(this.getCommand("gcbranchdelete")).setExecutor(new BranchDeleteCommand());
            //Objects.requireNonNull(this.getCommand("gcsave")).setExecutor(new GCSave());
        //} catch (SQLException e) {
        //    throw new RuntimeException(e);
        //}
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye, SpigotMC!");
    }
}
