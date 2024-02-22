package top.gitcraft;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.extension.platform.Platform;
import org.bukkit.plugin.java.JavaPlugin;
import top.gitcraft.commands.LoadCommand;
import top.gitcraft.commands.WETestCommand;

import java.sql.SQLException;
import java.util.Objects;

public final class GitCraft extends JavaPlugin {
    @Override
    public void onEnable() {

        getLogger().info("Hello, SpigotMC!");

        Objects.requireNonNull(this.getCommand("gcWETest")).setExecutor(new WETestCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Goodbye, SpigotMC!");
    }
}
