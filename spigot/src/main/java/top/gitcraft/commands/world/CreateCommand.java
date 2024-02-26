package top.gitcraft.commands.world;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.Instant;

import java.util.Objects;

import static top.gitcraft.ui.components.Info.infoCreatingWorld;
import static top.gitcraft.ui.components.Info.infoWorldCreated;
import static top.gitcraft.utils.methods.ExecuteConsoleCommand.dispatchTellRawCommand;

public class CreateCommand implements CommandExecutor {

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
            // Clone world, give name and don't teleport if doTeleport == "false"
            case 2:
                createWorldAndTp(player, clonedWorldName, args[1]);
                return true;
            // Clone world and give name
            case 1:
                createWorld(player, args[0]);
                return true;
            // Clone world and give random name
            default:
                createWorld(player, clonedWorldName);
                return true;
        }
    }

    public void createWorld(Player player, String clonedWorldName) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();

        dispatchTellRawCommand(player, infoCreatingWorld(clonedWorldName));

        Bukkit.getScheduler().runTask(core, () -> {
            // Clone the world after the message is sent
            worldManager.cloneWorld(player.getWorld().getName(), clonedWorldName);

            // Send the second message after the cloning operation is completed
            new JoinCommand().joinWorldAtCurrentLocation(player, clonedWorldName, "true");
            dispatchTellRawCommand(player, infoWorldCreated(clonedWorldName));
        });
    }

    public void createWorldAndTp(Player player, String clonedWorldName, String doTeleport) {
        if (Objects.equals(doTeleport, "false")) {
            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
            MVWorldManager worldManager = core.getMVWorldManager();

            dispatchTellRawCommand(player, infoCreatingWorld(clonedWorldName));

            Bukkit.getScheduler().runTask(core, () -> {
                // Clone the world after the message is sent
                worldManager.cloneWorld(player.getWorld().getName(), clonedWorldName);

                // Send the second message after the cloning operation is completed
                dispatchTellRawCommand(player, infoWorldCreated(clonedWorldName));
            });
        } else {
            createWorld(player, clonedWorldName);
        }
    }

    private String generateWorldName(Player player, String worldName) {
        long time = Instant.now().getEpochSecond();
        return worldName + "copy" + Long.toString(time);
    }
}
