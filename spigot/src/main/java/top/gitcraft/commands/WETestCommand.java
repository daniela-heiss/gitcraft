package top.gitcraft.commands;


import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class WETestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("sender is not a player");
            return true;
        }

        Player player = (Player) sender;
        World world = BukkitAdapter.adapt(player.getWorld());

        //player location
        Location playerLocation = player.getLocation();

        BlockArrayClipboard clipboard = copyRegionToClipboard(playerLocation, world, player);
        player.sendMessage("copied region to clipboard");
        pasteClipboard(playerLocation, world, clipboard);
        player.sendMessage("pasted clipboard");

        return true;
    }

    private BlockArrayClipboard copyRegionToClipboard(Location playerLocation, World world, Player player) {

        CuboidRegion region = createCubeInFront(10, 10, 10, playerLocation);
        player.sendMessage(region.getPos1() + "" + region.getPos2());
        BlockArrayClipboard clipboard = new BlockArrayClipboard(region);

        ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
                world, region, clipboard, region.getMinimumPoint()
        );

        try {
            Operations.complete(forwardExtentCopy);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

        return clipboard;
    }


    private void pasteClipboard(Location playerLocation, World world, BlockArrayClipboard clipboard) {
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(world)) {
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(BlockVector3.at(playerLocation.getBlockX() + 5, playerLocation.getBlockY(), playerLocation.getBlockZ()))
                    .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            e.printStackTrace();
        }
    }

    public CuboidRegion createCubeInFront(double width, double height, double depth, Location location) {
        // Calculate the position in front of the player based on yaw and pitch
        double xOffset = -Math.sin(Math.toRadians(location.getYaw())) * Math.cos(Math.toRadians(location.getPitch()));
        double yOffset = -Math.sin(Math.toRadians(location.getPitch()));
        double zOffset = Math.cos(Math.toRadians(location.getYaw())) * Math.cos(Math.toRadians(location.getPitch()));

        // Calculate the corner points of the cube
        double startX = location.getX() + xOffset * (depth / 2);
        double startY = location.getY() + yOffset * (depth / 2);
        double startZ = location.getZ() + zOffset * (depth / 2);

        BlockVector3 start = BlockVector3.at(startX, startY, startZ);
        BlockVector3 end = BlockVector3.at(startX + width, startY + height, startZ + depth);

        return new CuboidRegion(start, end);
    }
}
