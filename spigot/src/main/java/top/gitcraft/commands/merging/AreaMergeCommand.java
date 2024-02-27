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

import static top.gitcraft.listeners.AreaSelectListener.getSelection;
import static top.gitcraft.utils.WorldEditFunctions.*;

public class AreaMergeCommand implements CommandExecutor {

    private final GitCraft gitCraft;
    public AreaMergeCommand(GitCraft gitCraft) {
        this.gitCraft = gitCraft;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        if (args.length != 1) {
            return false;
        }


        sender.sendMessage("Gathering Coordinates...");
        World currentWorld = BukkitAdapter.adapt(player.getWorld());

        String worldName = player.getWorld().getName();
        sender.sendMessage("Current World Name: " + worldName);

        // Get BlockVector3 Coordinates of the selected Area
        CuboidRegion selectedArea = getSelection(player);


        BlockArrayClipboard clipboard = copyRegionToClipboard(selectedArea.getPos1(), selectedArea.getPos2(), currentWorld, player);
        player.sendMessage("Copied region to clipboard");

        String schematicName = args[0];
        File file = saveRegionAsSchematic(clipboard, schematicName, sender);

        if (file != null) {

            sender.sendMessage("Created Schematic " + schematicName + " from Clipboard");

            new JoinCommand(gitCraft).joinWorldAtCurrentLocation(player, "world", "true");

            Bukkit.getScheduler().runTaskLater(GitCraft.getPlugin(GitCraft.class), new Runnable() {
                @Override
                public void run() {
                    Clipboard loadedClipboard = loadSchematic(file);
                    sender.sendMessage("Loaded Schematic " + schematicName + " into Clipboard");

                    World originalWorld = BukkitAdapter.adapt(player.getWorld());

                    pasteClipboard(originalWorld, selectedArea.getPos1(), loadedClipboard);
                    sender.sendMessage("Pasted Schematic " + schematicName + " from Clipboard");
                }
            }, 50L);

        }
        return true;
    }

}