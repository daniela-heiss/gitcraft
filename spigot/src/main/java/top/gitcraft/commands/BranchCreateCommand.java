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

    private final WorldDao worldDao;
    private final UserDao userDao;

    public BranchCreateCommand() throws SQLException {
        DatabaseManager databaseManager = DatabaseManager.getInstance();
        worldDao = databaseManager.getWorldDao();
        userDao = databaseManager.getUserDao();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }


        Player player = (Player) sender;

        String worldName = player.getWorld().getName();
        String branchName = generateWorldName(player, worldName);

        if (args.length == 0) {

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().noWorldNameProvided());
            return false;
        }
        if (args.length == 2) {
            createBranch(sender, branchName, args[1]);
        } else {
            createBranch(sender, branchName);
        }

        return true;


    }

    public void createBranch(CommandSender sender, String branchName) {
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        MVWorldManager worldManager = core.getMVWorldManager();
        Player player = ((Player) sender).getPlayer();

        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().creatingWorld(branchName));
        worldManager.cloneWorld(player.getWorld().getName(), branchName);
        new BranchJoinCommand().joinBranch(sender, branchName, "true");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().worldCreated(branchName));
    }

    public void createBranch(CommandSender sender, String branchName, String doTeleport) {
        if (Objects.equals(doTeleport, "false")) {
            MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
            MVWorldManager worldManager = core.getMVWorldManager();
            Player player = ((Player) sender).getPlayer();

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().creatingWorld(branchName));
            worldManager.cloneWorld(player.getWorld().getName(), branchName);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw " + sender.getName() + " " + new Info().worldCreated(branchName));
        } else {
            createBranch(sender, branchName);
        }
    }

    private String generateWorldName(Player player, String worldName) {
        long time = Instant.now().getEpochSecond();
        return worldName + "copy" + Long.toString(time);
    }

    private boolean setPlayerId(Player player, String worldName) throws SQLException {
        try {
            player.sendMessage(worldName);

            WorldEntity world = worldDao.getWorldByWorldName(worldName);
            if (world == null) {
                player.sendMessage("World " + worldName + " not found!");
                return false;
            }
            player.sendMessage(world.toString());


            Thread.sleep(1000);
            UUID uuid = player.getUniqueId();
            UserEntity user = userDao.getUserByUuid(uuid);


            world.playerId = user.rowId;
            player.sendMessage(world.toString());
            worldDao.update(world);
            player.sendMessage("Updating world " + worldName + " with player id " + user.rowId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return true;
    }
}
