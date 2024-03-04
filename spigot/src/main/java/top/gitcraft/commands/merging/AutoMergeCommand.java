package top.gitcraft.commands.merging;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.world.World;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import top.gitcraft.GitCraft;
import top.gitcraft.commands.world.JoinCommand;

import java.io.*;
import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import static top.gitcraft.commands.world.JoinCommand.joinWorldAtCurrentLocation;
import static top.gitcraft.utils.GetBlockEntityList.getBlockChangedByPlayers;
import static top.gitcraft.utils.WorldEditFunctions.*;
import static top.gitcraft.utils.FindMinAndMax.*;

public class AutoMergeCommand implements CommandExecutor {

    private final GitCraft gitCraft;

    public AutoMergeCommand(GitCraft gitCraft) {
        this.gitCraft = gitCraft;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        //if (args.length != 1) {
        //    return false;
        //}

        sender.sendMessage("Gathering Coordinates...");

        World currentWorld = BukkitAdapter.adapt(player.getWorld());

        String worldName = player.getWorld().getName();
        sender.sendMessage("Current World Name: " + worldName);

        Double[] minCoordinatesArray = findMin(getBlockChangedByPlayers(worldName));
        Double[] maxCoordinatesArray = findMax(getBlockChangedByPlayers(worldName));

        for (Double number : minCoordinatesArray) {
            sender.sendMessage("Min Coordinates : " + number);
        }
        for (Double number : maxCoordinatesArray) {
            sender.sendMessage("Max Coordinates : " + number);
        }

        BlockArrayClipboard clipboard = copyRegionToClipboard(minCoordinatesArray, maxCoordinatesArray, currentWorld, player);
        player.sendMessage("Copied region to clipboard");

        String schematicName = "AutoMerge" + timestamp.getTime();
        File file = saveRegionAsSchematic(clipboard, schematicName, sender);

        if (file != null) {

            joinWorldAtCurrentLocation(player, "world");

            Bukkit.getScheduler().runTaskLater(GitCraft.getPlugin(GitCraft.class), new Runnable() {
                @Override
                public void run() {
                    Clipboard loadedClipboard = loadSchematic(file);
                    sender.sendMessage("Loaded Schematic " + schematicName + " into Clipboard");

                    World originalWorld = BukkitAdapter.adapt(player.getWorld());
                    sender.sendMessage("Current World Name: " + originalWorld);

                    pasteClipboard(player, originalWorld, minCoordinatesArray, loadedClipboard);
                    sender.sendMessage("Pasted Schematic " + schematicName + " from Clipboard");

                }
            }, 50L);

        }
        return true;
    }

}
