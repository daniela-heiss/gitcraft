package top.gitcraft.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GcBranchDelete implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            //String branchName = args[0];
            String branchName = "world2";
            sender.sendMessage("Branch " + branchName + " deleted!");

            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

            MVWorldManager worldManager = core.getMVWorldManager();
            worldManager.deleteWorld(branchName);


            return true;
        }
        else{
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

    }
}
