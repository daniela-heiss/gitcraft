package top.gitcraft.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BranchJoinCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

            Player player = ((Player) sender).getPlayer();
            //String branchName = args[0];
            String branchName = "world2";
            player.teleport(Bukkit.getWorld(branchName).getSpawnLocation());


            return true;
        }
        else{
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

    }
}
