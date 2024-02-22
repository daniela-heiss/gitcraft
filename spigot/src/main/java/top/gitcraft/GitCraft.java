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
            Objects.requireNonNull(this.getCommand("gcmenu")).setExecutor(new GcMenu());
            Objects.requireNonNull(this.getCommand("gcbranchmenu")).setExecutor(new GcBranchMenu());
        Objects.requireNonNull(this.getCommand("gcbranchjoin")).setExecutor(new GcBranchJoin());
        Objects.requireNonNull(this.getCommand("gcbranchcreate")).setExecutor(new GcBranchCreate());
        Objects.requireNonNull(this.getCommand("gcbranchdelete")).setExecutor(new GcBranchDelete());
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
