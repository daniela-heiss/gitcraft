package top.gitcraft.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.ui.components.Info;

import java.time.Instant;

import java.util.Objects;

public class WorldCreateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

        Player player = (Player) sender;

        String currentWorldName = player.getWorld().getName();
        String clonedWorldName = generateWorldName(player, currentWorldName);

        switch (args.length){
            case 2:
                createWorld(sender, clonedWorldName, args[1]);
                return true;
            case 1:
                createWorld(sender, args[0]);
                return true;
            default:
                createWorld(sender, clonedWorldName);
                return true;
        }
    }

    public void createWorld(CommandSender sender, String clonedWorldName) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();
        Player player = ((Player) sender).getPlayer();

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().creatingWorld(clonedWorldName));

        Bukkit.getScheduler().runTask(core, () -> {
            // Clone the world after the message is sent
            worldManager.cloneWorld(player.getWorld().getName(), clonedWorldName);

            // Send the second message after the cloning operation is completed
            new WorldJoinCommand().joinWorld(sender, clonedWorldName, "true");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().worldCreated(clonedWorldName));
        });
    }

    public void createWorld(CommandSender sender, String clonedWorldName, String doTeleport) {
        if (Objects.equals(doTeleport, "false")) {
            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
            MVWorldManager worldManager = core.getMVWorldManager();
            Player player = ((Player) sender).getPlayer();

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().creatingWorld(clonedWorldName));

            Bukkit.getScheduler().runTask(core, () -> {
                // Clone the world after the message is sent
                worldManager.cloneWorld(player.getWorld().getName(), clonedWorldName);

                // Send the second message after the cloning operation is completed
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().worldCreated(clonedWorldName));
            });
        } else {
            createWorld(sender, clonedWorldName);
        }
    }

    private String generateWorldName(Player player, String worldName) {
        long time = Instant.now().getEpochSecond();
        return worldName + "copy" + Long.toString(time);
    }

}
