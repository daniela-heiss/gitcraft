package top.gitcraft.commands.schematics;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.database.entities.BlockEntity;

import java.io.File;
import java.util.List;

import static top.gitcraft.listeners.AreaSelectListener.getSelection;
import static top.gitcraft.utils.BlockUtils.getBlockChangedByPlayers;
import static top.gitcraft.utils.BlockUtils.regionFromList;
import static top.gitcraft.utils.SchematicUtils.createClipboard;
import static top.gitcraft.utils.SchematicUtils.saveClipboardAsSchematic;

public class GenerateSchematicCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        if (args.length != 2) {
            return false;
        }
        String schematicName = args[1];
        World currentWorld = BukkitAdapter.adapt(player.getWorld());
        String worldName = player.getWorld().getName();

        switch (args[0]) {
            case "area":
                generateSchematicFromArea(player, currentWorld, schematicName);
                break;

            case "all":
                generateSchematicFromAllChanges(currentWorld, worldName, schematicName);
                break;

            default:
                return false;
        }

        return true;
    }

    public static void generateSchematicFromArea(Player player, World currentWorld, String schematicName) {
        // Get BlockVector3 Coordinates of the selected Area
        CuboidRegion selectedArea = getSelection(player);
        if (selectedArea == null) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Error: No Area selected");
        }

        player.sendMessage("Min Coordinates : " + selectedArea.getPos1());
        player.sendMessage("Min Coordinates : " + selectedArea.getPos2());

        BlockArrayClipboard clipboard1 = createClipboard(selectedArea, currentWorld);

        saveClipboardAsSchematic(clipboard1, schematicName);
    }

    public static File generateSchematicFromAllChanges(World currentWorld, String worldName, String schematicName) {
        List<BlockEntity> blockChangedByPlayers = getBlockChangedByPlayers(worldName);
        CuboidRegion region = regionFromList(blockChangedByPlayers);
        BlockArrayClipboard clipboard = createClipboard(region, currentWorld);

        return saveClipboardAsSchematic(clipboard, schematicName);
    }
}
