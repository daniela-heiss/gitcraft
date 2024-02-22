package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;
import top.gitcraft.commands.SaveCommand;
import top.gitcraft.commands.LoadCommand;

import top.gitcraft.commands.*;

import java.sql.SQLException;
import java.util.Objects;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {

        getLogger().info("Hello, SpigotMC!");

        Objects.requireNonNull(this.getCommand("gcmenu")).setExecutor(new MenuOpenCommand());
        Objects.requireNonNull(this.getCommand("gcbranchmenu")).setExecutor(new BranchMenuCommand());
        Objects.requireNonNull(this.getCommand("gcbranchlist")).setExecutor(new BranchListCommand());
        Objects.requireNonNull(this.getCommand("gcbranchjoinlist")).setExecutor(new BranchJoinListCommand());
        Objects.requireNonNull(this.getCommand("gcbranchcreatelist")).setExecutor(new BranchCreateListCommand());
        Objects.requireNonNull(this.getCommand("gcbranchdeletelist")).setExecutor(new BranchDeleteListCommand());

        Objects.requireNonNull(this.getCommand("gcbranchjoin")).setExecutor(new BranchJoinCommand());
        Objects.requireNonNull(this.getCommand("gcbranchcreate")).setExecutor(new BranchCreateCommand());
        Objects.requireNonNull(this.getCommand("gcbranchdelete")).setExecutor(new BranchDeleteCommand());

        try {
            Objects.requireNonNull(this.getCommand("gcload")).setExecutor(new LoadCommand());
            Objects.requireNonNull(this.getCommand("gcsave")).setExecutor(new SaveCommand());
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
