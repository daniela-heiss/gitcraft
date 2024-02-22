package top.gitcraft;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class testWorldEdit implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("setBlockExample") && sender instanceof Player) {
            Player player = (Player) sender;
            BlockVector3 targetLocation = BukkitAdapter.asBlockVector(player.getLocation().add(176, 70, 208)); // Zielposition

            try {
                // Setze den Block an der angegebenen Position
                WorldEdit.getInstance().getEditSessionFactory()
                        .getEditSession(BukkitAdapter.adapt(player.getWorld()), -1)
                        .setBlock(targetLocation, BukkitAdapter.adapt(Material.COBBLESTONE.createBlockData()));

                sender.sendMessage("Block gesetzt!");
            } catch (Exception e) {
                sender.sendMessage("Fehler beim Setzen des Blocks.");
                e.printStackTrace();
            }
            return true;
        }
        return false;
    }
}
