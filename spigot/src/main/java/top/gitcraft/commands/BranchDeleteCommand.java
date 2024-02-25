package top.gitcraft.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class BranchDeleteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

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
        if (Objects.equals(args[0], "world")){
            String jsonMessage = "[\"\","
                    + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                    + "{\"text\":\"[\",\"bold\":true,\"color\":\"red\"},"
                    + "{\"text\":\"!\",\"bold\":true},"
                    + "{\"text\":\"]\",\"bold\":true,\"color\":\"red\"},"
                    + "{\"text\":\" world\",\"bold\":true,\"color\":\"green\"},"
                    + "{\"text\":\" will not be deleted\\n\",\"bold\":true}]";

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);
            return true;
        }
        deleteBranch(sender, args[0]);

        return true;
    }

    public void deleteBranch(CommandSender sender, String branchName){
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");

        MVWorldManager worldManager = core.getMVWorldManager();
        deleteMessage(sender, branchName);
        worldManager.deleteWorld(branchName);
    }

    private void deleteMessage(CommandSender sender, String branchName){

        String jsonMessage = "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\"i\",\"bold\":true},"
                + "{\"text\":\"]\",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\" Deleting \",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\""+ branchName +"\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\"\\n \"}]";

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + jsonMessage);
    }
}
