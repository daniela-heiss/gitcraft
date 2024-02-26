package top.gitcraft.commands;

import com.onarandombox.MultiverseCore.MVWorld;
import com.onarandombox.MultiverseCore.MultiverseCore;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class BranchJoinCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {

            if (args.length == 0) {
                String jsonMessage = "[\"\","
                        + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                        + "{\"text\":\"[\",\"bold\":true,\"color\":\"blue\"},"
                        + "{\"text\":\"i\",\"bold\":true},"
                        + "{\"text\":\"] \",\"bold\":true,\"color\":\"blue\"},"
                        + "{\"text\":\"Please provide a branch name\",\"bold\":true},"
                        + "{\"text\":\"\\n \"}]";

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);
                return true;
            }
            if (args.length == 2){
                joinBranch(sender, args[0], args[1]);
            }else {
                joinBranch(sender, args[0]);
            }
            return true;
        }
        else{
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
    }
    public void joinBranch(CommandSender sender, String branchName){
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        Player player = ((Player) sender).getPlayer();
        World branch = Bukkit.getWorld(branchName);
        GameMode originalGameMode = player.getGameMode();

        joinMessage(sender, branchName);
        player.teleport(branch.getSpawnLocation());
        Bukkit.getScheduler().runTaskLater(core, () -> {
            player.setGameMode(originalGameMode);
        }, 5);
    }

    public void joinBranch(CommandSender sender, String branchName, String created){

        if(Objects.equals(created, "true")){
            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
            Player player = ((Player) sender).getPlayer();
            World branch = Bukkit.getWorld(branchName);
            Location location = new Location(branch, player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ());

            joinMessage(sender, branchName);
            player.teleport(location);

        } else {
            joinBranch(sender, branchName);
        }
    }
    private void joinMessage(CommandSender sender, String branchName){

        String jsonMessage = "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\"i\",\"bold\":true},"
                + "{\"text\":\"]\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\" Joining \",\"bold\":true},"
                + "{\"text\":\""+ branchName +"\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\"\\n \"}]";


        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);

    }
}
