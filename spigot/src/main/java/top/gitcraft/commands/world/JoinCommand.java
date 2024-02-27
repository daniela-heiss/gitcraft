package top.gitcraft.commands.world;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import top.gitcraft.GitCraft;

import java.util.Objects;

import static top.gitcraft.ui.components.Info.*;
import static top.gitcraft.utils.methods.ExecuteConsoleCommand.dispatchTellRawCommand;

public class JoinCommand implements CommandExecutor {
    private final GitCraft gitCraft;
    public JoinCommand(GitCraft gitCraft) {
        this.gitCraft = gitCraft;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        switch (args.length){
            // No world provided
            case 0:
                dispatchTellRawCommand(player, infoNoWorldNameProvided());
                return true;
            // Join world at current position if created == "true"
            case 2:
                joinWorldAtCurrentLocation(player, args[0], args[1]);
                return true;
            // Join world at world spawn
            default:
                joinWorldAtWorldSpawn(player, args[0]);
                return true;
        }
    }

    public void joinWorldAtWorldSpawn(Player player, String worldName){
        World world = Bukkit.getWorld(worldName);
        float originalYaw = player.getLocation().getYaw();
        float originalPitch = player.getLocation().getPitch();
        dispatchTellRawCommand(player, infoJoiningWorld(worldName));
        Bukkit.getScheduler().runTask(gitCraft, () -> {
            player.teleport(world.getSpawnLocation());
            setDirection(player, originalYaw, originalPitch);
            dispatchTellRawCommand(player, infoWorldJoined(worldName));
        });
    }
    public void setDirection(Player player, float originalYaw, float originalPitch){
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

        Bukkit.getScheduler().runTaskLater(core, () -> {
            Location newLocation = player.getLocation();
            newLocation.setYaw(originalYaw);
            newLocation.setPitch(originalPitch);

            player.teleport(newLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
        }, 5);

    }

    public void joinWorldAtCurrentLocation(Player player, String worldName, String created){
        if(Objects.equals(created, "true")){
            World world = Bukkit.getWorld(worldName);
            Location location = new Location(world, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

            dispatchTellRawCommand(player, infoJoiningWorld(worldName));
            Bukkit.getScheduler().runTask(gitCraft, () -> {
                player.teleport(location);
                dispatchTellRawCommand(player, infoWorldJoined(worldName));
            });
        } else {
            joinWorldAtWorldSpawn(player, worldName);
        }
    }
}
