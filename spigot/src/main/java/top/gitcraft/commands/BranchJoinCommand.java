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

public class BranchJoinCommand implements CommandExecutor {

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
            joinBranch(sender, args[0], args[1]);
        }else {
            joinBranch(sender, args[0]);
        }
        return true;
    }

    public void joinBranch(CommandSender sender, String branchName){
        Player player = ((Player) sender).getPlayer();
        World branch = Bukkit.getWorld(branchName);

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().joiningWorld(branchName));
        player.teleport(branch.getSpawnLocation());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().worldJoined(branchName));
    }

    public void joinBranch(CommandSender sender, String branchName, String created){

        if(Objects.equals(created, "true")){
            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
            Player player = ((Player) sender).getPlayer();
            World branch = Bukkit.getWorld(branchName);
            Location location = new Location(branch, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().joiningWorld(branchName));
            player.teleport(location);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().worldJoined(branchName));

        } else {
            joinBranch(sender, branchName);
        }
    }
}
