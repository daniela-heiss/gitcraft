package top.gitcraft.commands.merging;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import top.gitcraft.GitCraft;

import java.io.*;
import java.util.Objects;

import static top.gitcraft.commands.world.JoinCommand.joinWorldAtCurrentLocation;
import static top.gitcraft.utils.GetBlockEntityList.getBlockChangedByPlayers;
import static top.gitcraft.utils.WorldEditFunctions.*;
import static top.gitcraft.utils.FindMinAndMax.*;

public class AutoMergeCommand implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        World fromWorld = BukkitAdapter.adapt(player.getWorld());
        CuboidRegion region = getMinMaxPos(getBlockChangedByPlayers(fromWorld.getName()));
        BlockVector3 minPos = region.getPos1();

        BlockArrayClipboard clipboard = copyRegionToClipboard(region, fromWorld, player);

        joinWorldAtCurrentLocation(player, "world", () -> {
            World toWorld = BukkitAdapter.adapt(Objects.requireNonNull(Bukkit.getWorld("world")));
            pasteClipboard(toWorld, minPos, clipboard);
            player.sendMessage("Pasting clipboard into " + toWorld.getName() + " at " + minPos);
        });
        return true;
    }

}
