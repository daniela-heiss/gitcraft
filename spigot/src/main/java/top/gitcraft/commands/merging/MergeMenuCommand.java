package top.gitcraft.commands.merging;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static top.gitcraft.listeners.AreaSelectListener.getSelection;
import static top.gitcraft.ui.components.Menu.menuMergeMenu;
import static top.gitcraft.utils.BlockUtils.*;
import static top.gitcraft.utils.BlockUtils.getBlockChangedByPlayers;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class MergeMenuCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;
        String mergeType;
        BlockVector3 pos1 = findMin(getBlockChangedByPlayers(player.getWorld().getName()));
        BlockVector3 pos2 = findMax(getBlockChangedByPlayers(player.getWorld().getName()));
        CuboidRegion cr = getSelection(player);
        if(args.length == 0) {
            mergeType = "auto";
            dispatchTellRawCommand(player, menuMergeMenu(player, mergeType, pos1, pos2, cr));
            return true;
        }
        mergeType = args[0];
        dispatchTellRawCommand(player, menuMergeMenu(player, mergeType, pos1, pos2, cr));
        return true;
    }
}
