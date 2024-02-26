package top.gitcraft.commands;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

import static top.gitcraft.ui.components.Info.*;
import static top.gitcraft.utils.ExecuteConsoleCommand.dispatchTellRawCommand;

public class WorldDeleteCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 0) {
            dispatchTellRawCommand(player, infoNoWorldNameProvided());
            return true;
        }
        if (Objects.equals(args[0], "world")){
            dispatchTellRawCommand(player, infoWorldIsProtected("world"));
            return true;
        }
        deleteWorld(player, args[0]);

        return true;
    }

    public void deleteWorld(Player player, String worldName){
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();

        dispatchTellRawCommand(player, infoWorldDeleted(worldName));
        Bukkit.getScheduler().runTask(core, () -> {
            worldManager.deleteWorld(worldName);
            dispatchTellRawCommand(player, infoWorldDeleted(worldName));
        });
    }
}
