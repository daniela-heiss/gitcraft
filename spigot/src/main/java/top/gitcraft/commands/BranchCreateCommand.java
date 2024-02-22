package top.gitcraft.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BranchCreateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            if (args.length == 0){
                String jsonMessage = "[\"\","
                        + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                        + "{\"text\":\"[\",\"bold\":true,\"color\":\"blue\"},"
                        + "{\"text\":\"i\",\"bold\":true},"
                        + "{\"text\":\"] \",\"bold\":true,\"color\":\"blue\"},"
                        + "{\"text\":\"Please provide a branch name\",\"bold\":true},"
                        + "{\"text\":\"\\n \"}]";

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);
                return false;
            }
            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
            MVWorldManager worldManager = core.getMVWorldManager();
            Player player = ((Player) sender).getPlayer();

            worldManager.cloneWorld(player.getWorld().getName(), args[0]);

            new BranchJoinCommand().joinBranch(sender, args[0], "true");

            return true;
        }
        else{
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

    }
}
