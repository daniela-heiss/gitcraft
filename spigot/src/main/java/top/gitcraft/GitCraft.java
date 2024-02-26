package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;

import top.gitcraft.commands.*;
import top.gitcraft.commands.areaselect.GetAreaCommand;
import top.gitcraft.commands.areaselect.SetPos1Command;
import top.gitcraft.commands.areaselect.SetPos2Command;
import top.gitcraft.commands.merging.AreaMergeCommand;
import top.gitcraft.commands.merging.AutoMergeCommand;
import top.gitcraft.commands.schematics.GenerateSchematicCommand;
import top.gitcraft.listeners.AreaSelectListener;

import java.sql.SQLException;
import java.util.Objects;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {

        getLogger().info("Hello, SpigotMC!");

        try {
            Objects.requireNonNull(this.getCommand("automerge")).setExecutor(new AutoMergeCommand());
            Objects.requireNonNull(this.getCommand("areamerge")).setExecutor(new AreaMergeCommand());
            Objects.requireNonNull(this.getCommand("generateschematic")).setExecutor(new GenerateSchematicCommand());
            Objects.requireNonNull(this.getCommand("gcbranchcreate")).setExecutor(new BranchCreateCommand());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Objects.requireNonNull(this.getCommand("gcmenu")).setExecutor(new MenuOpenCommand());
        Objects.requireNonNull(this.getCommand("gcbranchmenu")).setExecutor(new BranchMenuCommand());
        Objects.requireNonNull(this.getCommand("gcbranchjoin")).setExecutor(new BranchJoinCommand());
        Objects.requireNonNull(this.getCommand("gcbranchdelete")).setExecutor(new BranchDeleteCommand());
        Objects.requireNonNull(this.getCommand("gcWETest")).setExecutor(new WETestCommand());


        Objects.requireNonNull(this.getCommand("gcbranchjoinlist")).setExecutor(new BranchJoinListCommand());
        Objects.requireNonNull(this.getCommand("gcbranchcreatelist")).setExecutor(new BranchCreateListCommand());
        Objects.requireNonNull(this.getCommand("gcbranchdeletelist")).setExecutor(new BranchDeleteListCommand());

        // area select
        Objects.requireNonNull(this.getCommand("gcSetPos1")).setExecutor(new SetPos1Command());
        Objects.requireNonNull(this.getCommand("gcSetPos2")).setExecutor(new SetPos2Command());
        Objects.requireNonNull(this.getCommand("gcGetArea")).setExecutor(new GetAreaCommand());


        getServer().getPluginManager().registerEvents(new AreaSelectListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye, SpigotMC!");
    }
}
