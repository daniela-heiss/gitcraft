package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;
import top.gitcraft.commands.MergeUtilTestCommand;
import top.gitcraft.commands.areaselect.GetAreaCommand;
import top.gitcraft.commands.areaselect.SetPos1Command;
import top.gitcraft.commands.areaselect.SetPos2Command;
import top.gitcraft.commands.loadsave.DeleteSaveCommand;
import top.gitcraft.commands.loadsave.LoadCommand;
import top.gitcraft.commands.loadsave.SaveCommand;
import top.gitcraft.commands.merging.AreaMergeCommand;
import top.gitcraft.commands.merging.AutoMergeCommand;
import top.gitcraft.commands.schematics.GenerateSchematicCommand;
import top.gitcraft.commands.schematics.PasteSchematicCommand;
import top.gitcraft.commands.world.CreateCommand;
import top.gitcraft.commands.world.DeleteCommand;
import top.gitcraft.commands.world.JoinCommand;
import top.gitcraft.commands.world.WorldCommand;
import top.gitcraft.listeners.AreaSelectListener;
import top.gitcraft.ui.logic.MainMenuCommand;
import top.gitcraft.ui.logic.WorldMenuCommand;
import top.gitcraft.utils.areavisualizer.PlayerQuitListener;

import java.util.Objects;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {

        registerMenuCommands();
        registerWorldCommands();

        registerMergeCommands();
        registerSchematicCommands();

        registerAreaSelectCommands();
        registerSaveLoadCommands();

        getServer().getPluginManager().registerEvents(new AreaSelectListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye, SpigotMC!");
    }

    public void registerAreaSelectCommands() {
        Objects.requireNonNull(this.getCommand("gcSetPos1")).setExecutor(new SetPos1Command());
        Objects.requireNonNull(this.getCommand("gcSetPos2")).setExecutor(new SetPos2Command());
        Objects.requireNonNull(this.getCommand("gcGetSelection")).setExecutor(new GetAreaCommand());
    }

    public void registerSaveLoadCommands() {
        Objects.requireNonNull(this.getCommand("gcload")).setExecutor(new LoadCommand());
        Objects.requireNonNull(this.getCommand("gcsave")).setExecutor(new SaveCommand());
        Objects.requireNonNull(this.getCommand("gcdeletesave")).setExecutor(new DeleteSaveCommand());
    }

    public void registerMergeCommands() {
        Objects.requireNonNull(this.getCommand("automerge")).setExecutor(new AutoMergeCommand());
        Objects.requireNonNull(this.getCommand("areamerge")).setExecutor(new AreaMergeCommand());
        // Test Command
        Objects.requireNonNull(this.getCommand("testmergeutils")).setExecutor(new MergeUtilTestCommand());
    }

    public void registerSchematicCommands() {
        Objects.requireNonNull(this.getCommand("generateschematic")).setExecutor(new GenerateSchematicCommand());
        Objects.requireNonNull(this.getCommand("pasteschematic")).setExecutor(new PasteSchematicCommand());
    }

    public void registerMenuCommands() {
        Objects.requireNonNull(this.getCommand("gcmenu")).setExecutor(new MainMenuCommand());
        Objects.requireNonNull(this.getCommand("gcworldmenu")).setExecutor(new WorldMenuCommand());
    }

    public void registerWorldCommands() {
        Objects.requireNonNull(this.getCommand("gclist")).setExecutor(new WorldCommand());
        Objects.requireNonNull(this.getCommand("gcjoin")).setExecutor(new JoinCommand());
        Objects.requireNonNull(this.getCommand("gccreate")).setExecutor(new CreateCommand());
        Objects.requireNonNull(this.getCommand("gcdelete")).setExecutor(new DeleteCommand());
    }
}
