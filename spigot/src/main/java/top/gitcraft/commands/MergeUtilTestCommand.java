package top.gitcraft.commands;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.MergeMetaData;

import static top.gitcraft.listeners.AreaSelectListener.getSelection;
import static top.gitcraft.utils.MergeUtils.calculateAreaCoordinates;

public class MergeUtilTestCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        CuboidRegion selectedArea = getSelection(player);

        MergeMetaData areas = calculateAreaCoordinates(player, selectedArea.getPos1(), selectedArea.getPos2());

        BlockVector3 original = areas.getAreaOriginal();
        player.sendMessage("Original Coordinates: " + original.getX() + " " + original.getY() + " " + original.getZ());
        BlockVector3 changes = areas.getAreaChanges();
        player.sendMessage("Changes Coordinates: " + changes.getX() + " " + changes.getY() + " " + changes.getZ());
        BlockVector3 combined = areas.getAreaCombined();
        player.sendMessage("Combined Coordinates: " + combined.getX() + " " + combined.getY() + " " + combined.getZ());



        return true;
    }
}
