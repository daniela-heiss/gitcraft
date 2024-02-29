package top.gitcraft.commands;

import top.gitcraft.GitCraft;
import top.gitcraft.commands.areaselect.GetAreaCommand;
import top.gitcraft.commands.areaselect.SetPos1Command;
import top.gitcraft.commands.areaselect.SetPos2Command;
import top.gitcraft.commands.loadsave.LoadCommand;
import top.gitcraft.commands.loadsave.SaveCommand;
import top.gitcraft.commands.merging.AreaMergeCommand;
import top.gitcraft.commands.merging.AutoMergeCommand;
import top.gitcraft.commands.schematics.GenerateSchematicCommand;
import top.gitcraft.commands.schematics.PasteSchematicCommand;
import top.gitcraft.commands.world.CreateCommand;
import top.gitcraft.commands.world.DeleteCommand;
import top.gitcraft.commands.world.JoinCommand;
import top.gitcraft.ui.logic.MainMenuCommand;
import top.gitcraft.ui.logic.WorldMenuCommand;

import java.util.Objects;

public class InitCommands {
    GitCraft gitCraft;

    public InitCommands() {
        this.gitCraft = GitCraft.getPlugin(GitCraft.class);
        initCommands();
    }

    private void initCommands() {
        initAreaSelectCommands();
        initWorldCommands();
        initSchematicCommands();
        initMergeCommands();
        initMenuCommands();
        Objects.requireNonNull(gitCraft.getCommand("testareavisulize")).setExecutor(new TestAreaVisualizer());
        //initSaveLoadCommands();

    }

    private void initAreaSelectCommands() {
        Objects.requireNonNull(gitCraft.getCommand("gcSetPos1")).setExecutor(new SetPos1Command());
        Objects.requireNonNull(gitCraft.getCommand("gcSetPos2")).setExecutor(new SetPos2Command());
        Objects.requireNonNull(gitCraft.getCommand("gcGetSelection")).setExecutor(new GetAreaCommand());
    }

    private void initWorldCommands() {
        Objects.requireNonNull(gitCraft.getCommand("gcjoin")).setExecutor(new JoinCommand());
        Objects.requireNonNull(gitCraft.getCommand("gccreate")).setExecutor(new CreateCommand());
        Objects.requireNonNull(gitCraft.getCommand("gcdelete")).setExecutor(new DeleteCommand());
    }

    private void initSchematicCommands() {
        Objects.requireNonNull(gitCraft.getCommand("generateschematic")).setExecutor(new GenerateSchematicCommand());
        Objects.requireNonNull(gitCraft.getCommand("pasteschematic")).setExecutor(new PasteSchematicCommand());
    }

    private void initMergeCommands() {
        Objects.requireNonNull(gitCraft.getCommand("automerge")).setExecutor(new AutoMergeCommand());
        Objects.requireNonNull(gitCraft.getCommand("areamerge")).setExecutor(new AreaMergeCommand());
    }

    private void initSaveLoadCommands() {
        Objects.requireNonNull(gitCraft.getCommand("gcLoad")).setExecutor(new LoadCommand());
        Objects.requireNonNull(gitCraft.getCommand("gcSave")).setExecutor(new SaveCommand());
    }

    private void initMenuCommands() {
        Objects.requireNonNull(gitCraft.getCommand("gcmenu")).setExecutor(new MainMenuCommand());
        Objects.requireNonNull(gitCraft.getCommand("gcworldmenu")).setExecutor(new WorldMenuCommand());
    }


}
