package top.gitcraft.commands;

import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldEntity;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.ui.components.Info;

import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;

import java.util.Objects;

public class BranchCreateCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

        Player player = (Player) sender;

        String worldName = player.getWorld().getName();
        String branchName = generateWorldName(player, worldName);

        switch (args.length){
            case 2:
                createBranch(sender, branchName, args[1]);
                return true;
            case 1:
                createBranch(sender, args[0]);
                return true;
            default:
                createBranch(sender, branchName);
                return true;
        }
    }

    public void createBranch(CommandSender sender, String branchName) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();
        Player player = ((Player) sender).getPlayer();

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().creatingWorld(branchName));

        Bukkit.getScheduler().runTask(core, () -> {
            // Clone the world after the message is sent
            worldManager.cloneWorld(player.getWorld().getName(), branchName);

            // Send the second message after the cloning operation is completed
            new BranchJoinCommand().joinBranch(sender, branchName, "true");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().worldCreated(branchName));
        });
    }

    public void createBranch(CommandSender sender, String branchName, String doTeleport) {
        if (Objects.equals(doTeleport, "false")) {
            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
            MVWorldManager worldManager = core.getMVWorldManager();
            Player player = ((Player) sender).getPlayer();

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().creatingWorld(branchName));

            Bukkit.getScheduler().runTask(core, () -> {
                // Clone the world after the message is sent
                worldManager.cloneWorld(player.getWorld().getName(), branchName);

                // Send the second message after the cloning operation is completed
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().worldCreated(branchName));
            });
        } else {
            createBranch(sender, branchName);
        }
    }

    private String generateWorldName(Player player, String worldName) {
        long time = Instant.now().getEpochSecond();
        return worldName + "copy" + Long.toString(time);
    }

}
