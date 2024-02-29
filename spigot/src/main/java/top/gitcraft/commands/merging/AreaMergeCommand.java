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
        if (args.length != 1) {
            return false;
        }

        String schematicName = args[0];
        CuboidRegion selectedArea = getSelection(player);

        File file = createSchematicFile(selectedArea, schematicName, player);
        if (!file.exists()) {
            return false;
        }

        player.sendMessage("Created Schematic " + schematicName + " from Clipboard");

        joinWorldAtCurrentLocation(player, "world");
        pasteSchematic(file, schematicName, selectedArea, player);

        return true;

    }


    private File createSchematicFile(CuboidRegion selectedArea, String schematicName, Player player) {
        player.sendMessage("Gathering Coordinates...");
        World currentWorld = BukkitAdapter.adapt(player.getWorld());

        String worldName = player.getWorld().getName();
        player.sendMessage("Current World Name: " + worldName);


        BlockArrayClipboard clipboard = copyRegionToClipboard(selectedArea.getPos1(), selectedArea.getPos2(), currentWorld, player);
        player.sendMessage("Copied region to clipboard");

        return saveRegionAsSchematic(clipboard, schematicName, player);
    }

    private void pasteSchematic(File file, String schematicName, CuboidRegion selectedArea, Player player) {
        Bukkit.getScheduler().runTaskLater(GitCraft.getPlugin(GitCraft.class), new Runnable() {
            @Override
            public void run() {
                Clipboard loadedClipboard = loadSchematic(file);
                player.sendMessage("Loaded Schematic " + schematicName + " into Clipboard");

                World originalWorld = BukkitAdapter.adapt(player.getWorld());

                pasteClipboard(originalWorld, selectedArea.getPos1(), loadedClipboard);
                player.sendMessage("Pasted Schematic " + schematicName + " from Clipboard");
            }
        }, 50L);

    }

}