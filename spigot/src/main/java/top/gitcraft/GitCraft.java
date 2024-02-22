package top.gitcraft;

import org.bukkit.plugin.java.JavaPlugin;
import top.gitcraft.commands.SaveCommand;
import top.gitcraft.commands.LoadCommand;

import java.sql.SQLException;
import java.util.Objects;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {

        getLogger().info("Hello, SpigotMC!");

        try {
            Objects.requireNonNull(this.getCommand("load")).setExecutor(new LoadCommand());
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
