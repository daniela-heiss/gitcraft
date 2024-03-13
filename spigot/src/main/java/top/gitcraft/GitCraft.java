package top.gitcraft;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;
import top.gitcraft.commands.GitcCommand;
import top.gitcraft.commands.areaselect.DeselectAreaCommand;
import top.gitcraft.commands.areaselect.GetAreaCommand;
import top.gitcraft.commands.areaselect.SetPos1Command;
import top.gitcraft.commands.areaselect.SetPos2Command;
import top.gitcraft.commands.loadsave.DeleteSaveCommand;
import top.gitcraft.commands.loadsave.LoadCommand;
import top.gitcraft.commands.loadsave.SaveCommand;
import top.gitcraft.commands.merging.*;
import top.gitcraft.commands.schematics.GenerateSchematicCommand;
import top.gitcraft.commands.schematics.GenerateSchematicFromArea;
import top.gitcraft.commands.schematics.PasteSchematicCommand;
import top.gitcraft.commands.world.CreateCommand;
import top.gitcraft.commands.world.DeleteCommand;
import top.gitcraft.commands.world.JoinCommand;
import top.gitcraft.listeners.AreaSelectListener;
import top.gitcraft.listeners.PlayerChangeWorldListener;
import top.gitcraft.listeners.PlayerJoinListener;
import top.gitcraft.listeners.PlayerQuitListener;
import top.gitcraft.ui.logic.MainMenuCommand;
import top.gitcraft.ui.logic.SaveMenuCommand;
import top.gitcraft.ui.logic.WorldMenuCommand;

import java.io.FileNotFoundException;

import static top.gitcraft.utils.ConfigUtils.createNewConfigFile;

public final class GitCraft extends JavaPlugin {
    @Override public void onEnable() {
        registerGcCommand();

        registerMenuCommands();
        registerWorldCommands();

        registerMergeCommands();
        registerSchematicCommands();

        registerAreaSelectCommands();
        registerSaveLoadCommands();

        getServer().getPluginManager().registerEvents(new AreaSelectListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerChangeWorldListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);

        // Create config.yml and GitCraft folder
        try {
            createNewConfigFile();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye, SpigotMC!");
    }

    public void registerGcCommand() {
        registerCommand("gc", new GitcCommand());
    }

    public void registerAreaSelectCommands() {
        registerCommand("gcSetPos1", new SetPos1Command());
        registerCommand("gcSetPos2", new SetPos2Command());
        registerCommand("gcGetSelection", new GetAreaCommand());
        registerCommand("gcDeselectArea", new DeselectAreaCommand());
    }

    public void registerSaveLoadCommands() {
        registerCommand("gcload", new LoadCommand());
        registerCommand("gcsave", new SaveCommand());
        registerCommand("gcdeletesave", new DeleteSaveCommand());
    }

    public void registerMergeCommands() {
        registerCommand("autoMerge", new AutoMergeCommand());
        registerCommand("areamerge", new AreaMergeCommand());

        registerCommand("gcdiscardmerge", new DiscardMergeCommand());

        registerCommand("merge", new MergeCommand());
        registerCommand("gcmergemenu", new MergeMenuCommand());
    }


    public void registerSchematicCommands() {
        registerCommand("generateschematic", new GenerateSchematicCommand());
        registerCommand("pasteschematic", new PasteSchematicCommand());
        registerCommand("generateschematicfromarea", new GenerateSchematicFromArea());
    }

    public void registerMenuCommands() {
        registerCommand("gcmenu", new MainMenuCommand());
        registerCommand("gcworldmenu", new WorldMenuCommand());
        registerCommand("gcsavemenu", new SaveMenuCommand());
    }

    public void registerWorldCommands() {

        registerCommand("gcjoin", new JoinCommand());
        registerCommand("gccreate", new CreateCommand());
        registerCommand("gcdelete", new DeleteCommand());
    }

    private void registerCommand(String commandName, CommandExecutor executor) {
        PluginCommand command = this.getCommand(commandName);
        if (command != null) {
            command.setExecutor(executor);
        } else {
            getLogger().warning("Command '" + commandName + "' not found!");
        }
    }
}
