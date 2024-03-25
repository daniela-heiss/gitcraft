package top.gitcraft.commands.merging;

import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.utils.CubeUtils;
import top.gitcraft.utils.enums.LISTTYPE;

import java.util.Objects;

import static top.gitcraft.listeners.AreaSelectListener.getSelection;
import static top.gitcraft.ui.components.Menu.menuMergeMenu;
import static top.gitcraft.ui.components.WorldList.worldLisSelectAll;
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
        CuboidRegion cr;

        if(args[0].equals("::")){
            dispatchTellRawCommand(player, worldLisSelectAll(LISTTYPE.SELECT, Integer.parseInt(args[1]), args[2], args[3], args[4], args[5]));
            return true;
        }

        // /gcmergemenu : area fromOrIntoWorld <world1> <world2> <selectedWorld>
        if(args[0].equals(":")){
            String fromWorld = Objects.equals(args[2], "from") ? args[5] : args[3];
            String intoWorld = Objects.equals(args[2], "into") ? args[5] : args[4];
            cr = args[1].equals("area") ? getSelection(player) : CubeUtils.regionFromList(getBlockChangedByPlayers(fromWorld));
            dispatchTellRawCommand(player, menuMergeMenu(player, args[1], cr, Bukkit.getWorld(fromWorld), Bukkit.getWorld(intoWorld)));
            return true;
        }

        if (args.length == 0 || !args[0].equals("area")) {
            mergeType = "auto";
            cr = CubeUtils.regionFromList(getBlockChangedByPlayers(player.getWorld().getName()));
            dispatchTellRawCommand(player, menuMergeMenu(player, mergeType, cr, player.getWorld(), Bukkit.getWorld("world")));
            return true;
        }
        mergeType = args[0];
        cr = getSelection(player);
        dispatchTellRawCommand(player, menuMergeMenu(player, mergeType, cr, player.getWorld(), Bukkit.getWorld("world")));
        return true;
    }
}
