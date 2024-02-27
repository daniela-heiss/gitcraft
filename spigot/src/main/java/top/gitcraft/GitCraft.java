package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;

import top.gitcraft.commands.*;
import top.gitcraft.commands.world.WorldCommand;
import top.gitcraft.commands.world.CreateCommand;
import top.gitcraft.commands.world.DeleteCommand;
import top.gitcraft.commands.world.JoinCommand;
import top.gitcraft.ui.logic.MainMenuCommand;
import top.gitcraft.ui.logic.WorldMenuCommand;

import java.sql.SQLException;
import java.util.Objects;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {

        getLogger().info("Hello, SpigotMC!");

        Objects.requireNonNull(this.getCommand("gcmenu")).setExecutor(new MainMenuCommand());
        Objects.requireNonNull(this.getCommand("gcworldmenu")).setExecutor(new WorldMenuCommand());
        Objects.requireNonNull(this.getCommand("gclist")).setExecutor(new WorldCommand());
        Objects.requireNonNull(this.getCommand("gcjoin")).setExecutor(new JoinCommand(this));
        Objects.requireNonNull(this.getCommand("gccreate")).setExecutor(new CreateCommand(this));
        Objects.requireNonNull(this.getCommand("gcdelete")).setExecutor(new DeleteCommand(this));
        Objects.requireNonNull(this.getCommand("gcWETest")).setExecutor(new WETestCommand());

        try {
            Objects.requireNonNull(this.getCommand("automerge")).setExecutor(new AutoMergeCommand());
            Objects.requireNonNull(this.getCommand("areamerge")).setExecutor(new AreaMergeCommand());
            Objects.requireNonNull(this.getCommand("generateschematic")).setExecutor(new GenerateSchematicCommand());
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
