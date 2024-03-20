package top.gitcraft.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.entities.*;
import top.gitcraft.listeners.AreaSelectListener;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

import static top.gitcraft.ui.components.Menu.confirmMerge;

public class MergeUtils {
    private static final Logger logger = GitCraft.getPlugin(GitCraft.class).getLogger();


    public static void pasteMergeAreas(Player player, String fromWorldName, String targetWorldName,
                                       String mergeWorldName, CuboidRegion region) throws SQLException {

        //expanding the section by 5 in each dimension
        //get timestamp to create a unique world name

        final int expandBy = 10;
        final int margin = 10;

        CuboidRegion expandedRegion = CubeUtils.expandCube(region, expandBy);

        final int width = expandedRegion.getWidth();

        mergeWorldName = "mergeWorld" + System.currentTimeMillis();
        WorldUtils.createVoidWorld(mergeWorldName, 0);
        World fromWorld = BukkitAdapter.adapt(Bukkit.getWorld(fromWorldName));
        World targetWorld = BukkitAdapter.adapt(Bukkit.getWorld(targetWorldName));
        World voidWorld = BukkitAdapter.adapt(WorldUtils.createVoidWorld(mergeWorldName, 0));

        BlockArrayClipboard fromClipboard =
                SchematicUtils.createClipboard(expandedRegion, fromWorld);
        BlockVector3 fromOrigin = fromClipboard.getOrigin().subtract(width + margin, 0, 0);
        SchematicUtils.pasteClipboard(voidWorld, player, fromOrigin, fromClipboard);

//        the region in the "to" world
        BlockArrayClipboard targetClipboard =
                SchematicUtils.createClipboard(expandedRegion, targetWorld);
        BlockVector3 targetOrigin = fromClipboard.getOrigin().add(width + margin, 0, 0);
        SchematicUtils.pasteClipboard(voidWorld, player, targetOrigin, targetClipboard);

        BlockArrayClipboard changesClipboard =
                SchematicUtils.createClipboardFromChanges(region, fromWorldName);
        BlockVector3 changesOrigin = changesClipboard.getOrigin();
        BlockVector3 targetPreviewOrigin = fromClipboard.getOrigin();
        SchematicUtils.pasteClipboard(voidWorld, player, targetPreviewOrigin, targetClipboard);
//        SchematicUtils.pasteClipboard(voidWorld, player, changesOrigin, changesClipboard);

        pasteChangedBlocks(region, Bukkit.getWorld(voidWorld.getId()), fromWorldName);

        Runnable callback = () -> {
            AreaSelectListener.setPos1(player, region.getPos1());
            AreaSelectListener.setPos2(player, region.getPos2());
            CommandUtils.dispatchTellRawCommand(player,
                    confirmMerge(fromWorldName, targetWorldName));
        };
        TeleportUtils.joinWorldAtCurrentLocation(player, mergeWorldName, callback);

        player.setAllowFlight(true);
        player.setFlying(true);

        //get the chunks of the clipboards
        Iterable<BlockVector2> chunks;
        chunks = region.getChunks();
        //TODO: fix lighting for the other 2 areas
        voidWorld.fixLighting(chunks);
    }

    private static void pasteChangedBlocks(CuboidRegion region, org.bukkit.World world, String fromWorldName) throws SQLException {
        DatabaseManager db = DatabaseManager.getInstance();
        WorldEntity worldEntity = db.getWorldDao().getWorldByWorldName(fromWorldName);
        BlockDao blockDao = db.getBlockDao();
        List<BlockEntity> changes = blockDao.getLastBlockChangesInRegionByWorld(region, worldEntity.rowId);
        for (BlockEntity change : changes) {
            Block block = world.getBlockAt(change.x, change.y, change.z);

            if (change.action == ActionType.BREAK.getValue()) {
                block.setType(Material.AIR, false);
                continue;
            }

            if (change.data != 0) {
                logger.info("Picture data not supported yet");
                continue;
            }

            MaterialMapEntity materialEntity = db.getMaterialMapDao().getMaterialById(change.type);
            Material material = Material.matchMaterial(materialEntity.material);
            if (material == null) {
                logger.info("Material not found for " + materialEntity.material);
                continue;
            }

            logger.info("Pasting material " + material + " at " + change.x + " " + change.y + " " + change.z +
                    " wid " + worldEntity.rowId);

            block.setType(material, false);

            if (change.blockData != null) {
                String asciiString = new String(change.blockData, StandardCharsets.US_ASCII);
                String[] values = asciiString.split(",");

                StringBuilder blockDataBuilder = new StringBuilder("[");
                for (String value : values) {
                    BlockDataMapEntity blockDataMapEntity = db.getBlockDataMapDao().getBlockDataById(Integer.parseInt(value));
                    blockDataBuilder.append(blockDataMapEntity.blockData).append(",");
                }

                if (blockDataBuilder.length() > 1) {
                    blockDataBuilder.setLength(blockDataBuilder.length() - 1); // Remove the trailing comma
                }
                blockDataBuilder.append("]");

                String blockData = blockDataBuilder.toString();
                logger.info("Block data: " + blockData);
                block.setBlockData(Bukkit.getServer().createBlockData(material, blockData));
            }
            block.getState().update();
        }

    }
}

