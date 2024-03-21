package top.gitcraft.commands.schematics;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.session.SessionManager;
import com.sk89q.worldedit.session.SessionOwner;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

import static top.gitcraft.utils.MessageUtils.errorMessage;
import static top.gitcraft.utils.MessageUtils.successMessage;
import static top.gitcraft.utils.SchematicUtils.loadSchematicAsClipboard;

public class LoadSchematicCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            return false;
        } else {

            String schematicName = args[0];
            String fileEnding = ".schem";
            String currentDirectory = System.getProperty("user.dir");
            File file = new File(
                    currentDirectory + "/plugins/WorldEdit/schematics/" + schematicName + fileEnding);
            if (file.exists()) {
                LoadSchematicIntoPlayerClipboard(player, file);
                successMessage(player, "Successfully loaded Schematic " + ChatColor.WHITE + schematicName + " into clipboard");
                return true;
            } else {
                errorMessage(player, "Unable to lead schematic " + ChatColor.WHITE + schematicName);
                return false;
            }
        }
    }

    public static void LoadSchematicIntoPlayerClipboard(Player player, File file) {
        SessionManager manager = WorldEdit.getInstance().getSessionManager();
        SessionOwner actor = BukkitAdapter.adapt(player);
        LocalSession localSession = manager.get(actor);

        ClipboardHolder clipboardHolder = new ClipboardHolder(loadSchematicAsClipboard(file));
        localSession.setClipboard(clipboardHolder);

    }
}
