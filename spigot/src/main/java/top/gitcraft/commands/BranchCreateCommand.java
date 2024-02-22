package top.gitcraft.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

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
            if (args.length == 2){
                createBranch(sender, args[0], args[1]);
            }else {
                createBranch(sender, args[0]);
            }

            return true;
        }
        else{
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
    }
    public void createBranch(CommandSender sender, String branchName){
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();
        Player player = ((Player) sender).getPlayer();

        createMessage(sender, branchName);
        worldManager.cloneWorld(player.getWorld().getName(), branchName);
        new BranchJoinCommand().joinBranch(sender, branchName, "true");
    }
    public void createBranch(CommandSender sender, String branchName, String doTeleport){
        if (Objects.equals(doTeleport, "false")){
            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
            MVWorldManager worldManager = core.getMVWorldManager();
            Player player = ((Player) sender).getPlayer();

            createMessage(sender, branchName);
            worldManager.cloneWorld(player.getWorld().getName(), branchName);
        }else {
            createBranch(sender, branchName);
        }
    }

    private void createMessage(CommandSender sender, String branchName){

        String jsonMessage = "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"color\":\"aqua\"},"
                + "{\"text\":\"i\",\"bold\":true},"
                + "{\"text\":\"]\",\"bold\":true,\"color\":\"aqua\"},"
                + "{\"text\":\" Created \",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\""+ branchName +"\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\"\\n \"}]";

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);

    }
}
