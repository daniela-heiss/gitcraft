package top.gitcraft.commands.world;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;

import java.util.Objects;

import static top.gitcraft.ui.components.Info.*;
import static top.gitcraft.utils.methods.ExecuteConsoleCommand.dispatchTellRawCommand;

public class DeleteCommand implements CommandExecutor {
    private final GitCraft gitCraft;
    public DeleteCommand(GitCraft gitCraft) {
        this.gitCraft = gitCraft;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        // No world provided
        if (args.length == 0) {
            dispatchTellRawCommand(player, infoNoWorldNameProvided());
            return true;
        }
        // "world" is protected from accidental deletion
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

        dispatchTellRawCommand(player, infoDeletingWorld(worldName));
        Bukkit.getScheduler().runTask(gitCraft, () -> {
            worldManager.deleteWorld(worldName);
            dispatchTellRawCommand(player, infoWorldDeleted(worldName));
        });
    }
}
