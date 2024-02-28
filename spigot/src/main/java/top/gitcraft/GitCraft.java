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
import top.gitcraft.commands.world.WorldCommand;
import top.gitcraft.commands.world.CreateCommand;
import top.gitcraft.commands.world.DeleteCommand;
import top.gitcraft.commands.world.JoinCommand;
import top.gitcraft.commands.LoadCommand;
import top.gitcraft.commands.SaveCommand;
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

        Objects.requireNonNull(this.getCommand("automerge")).setExecutor(new AutoMergeCommand());
        Objects.requireNonNull(this.getCommand("areamerge")).setExecutor(new AreaMergeCommand());
        Objects.requireNonNull(this.getCommand("generateschematic")).setExecutor(new GenerateSchematicCommand());

        Objects.requireNonNull(this.getCommand("gcWETest")).setExecutor(new WETestCommand());

        // area select
        Objects.requireNonNull(this.getCommand("gcSetPos1")).setExecutor(new SetPos1Command());
        Objects.requireNonNull(this.getCommand("gcSetPos2")).setExecutor(new SetPos2Command());
        Objects.requireNonNull(this.getCommand("gcGetSelection")).setExecutor(new GetAreaCommand());

        //save-load
        Objects.requireNonNull(this.getCommand("gcLoad")).setExecutor(new LoadCommand());
        Objects.requireNonNull(this.getCommand("gcSave")).setExecutor(new SaveCommand());


        getServer().getPluginManager().registerEvents(new AreaSelectListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye, SpigotMC!");
    }
}
