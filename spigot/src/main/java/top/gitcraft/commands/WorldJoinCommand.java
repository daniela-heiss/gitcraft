package top.gitcraft.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.ui.components.Info;

import java.util.Objects;

public class WorldJoinCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

        if (args.length == 0) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().noWorldNameProvided());
            return true;
        }
        if (args.length == 2){
            joinWorld(sender, args[0], args[1]);
        }else {
            joinWorld(sender, args[0]);
        }
        return true;
    }

    public void joinWorld(CommandSender sender, String worldName){
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        Player player = ((Player) sender).getPlayer();
        World world = Bukkit.getWorld(worldName);

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().joiningWorld(worldName));
        Bukkit.getScheduler().runTask(core, () -> {
            player.teleport(world.getSpawnLocation());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().worldJoined(worldName));
        });
    }

    public void joinWorld(CommandSender sender, String worldName, String created){

        if(Objects.equals(created, "true")){
            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
            Player player = ((Player) sender).getPlayer();
            World world = Bukkit.getWorld(worldName);
            Location location = new Location(world, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().joiningWorld(worldName));
            Bukkit.getScheduler().runTask(core, () -> {
                player.teleport(location);
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().worldJoined(worldName));
            });
        } else {
            joinWorld(sender, worldName);
        }
    }
}
