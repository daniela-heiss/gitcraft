package top.gitcraft.commands.merging;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;
import top.gitcraft.commands.world.JoinCommand;

import java.io.File;

import static top.gitcraft.commands.world.JoinCommand.joinWorldAtCurrentLocation;
import static top.gitcraft.utils.AreaSelect.getSelection;
import static top.gitcraft.utils.WorldEditFunctions.*;

/**
 * This command merges a previous selected area into a schematic and then pastes it into the world
 */
public class AreaMergeCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        CuboidRegion selectedArea = getSelection(player);
        if (selectedArea == null) {
            player.sendMessage("You must select an area first");
            return false;
        }

        World currentWorld = BukkitAdapter.adapt(player.getWorld());

        BlockArrayClipboard clipboard = copyRegionToClipboard(selectedArea, currentWorld, player);
        player.sendMessage("Copied region to clipboard");

        joinWorldAtCurrentLocation(player, "world");

        World originalWorld = BukkitAdapter.adapt(player.getWorld());
        player.sendMessage("Pasting clipboard into " + currentWorld.getName());
        pasteClipboard(originalWorld, selectedArea.getPos1(), clipboard);

        return true;
    }


}