package top.gitcraft.commands.schematics;

import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

import static top.gitcraft.utils.SchematicUtils.pasteSchematicAndJoin;

public class PasteSchematicCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return false;
        }
        Player player = (Player) sender;

        if (args.length != 4) {
            player.sendMessage("Usage: /PasteSchematic <schematicName> <x> <y> <z>");
            return false;
        }
        String schematicName = args[0];

        BlockVector3 pos1 = BlockVector3.at(Integer.parseInt(args[1]), Integer.parseInt(args[2]),
                Integer.parseInt(args[3]));

        String fileEnding = ".schem";
        String currentDirectory = System.getProperty("user.dir");
        File file = new File(
                currentDirectory + "/plugins/WorldEdit/schematics/" + schematicName + fileEnding);

        return pasteSchematicAndJoin(file, player, schematicName, pos1, "world");
    }
}
