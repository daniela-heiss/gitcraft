package top.gitcraft.utils;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.math.BlockVector2;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.World;
import org.bukkit.Art;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import top.gitcraft.GitCraft;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.entities.*;
import top.gitcraft.listeners.AreaSelectListener;

import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
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
        // logger.info("Changes size: " + changes.size());
        for (BlockEntity change : changes) {
            Block block = world.getBlockAt(change.x, change.y, change.z);
            //logger.info("Pasting at " + change.x + " " + change.y + " " + change.z +
            //        " from " + fromWorldName + " (id " + worldEntity.rowId + ")" + " to " + world.getName());

            MaterialMapEntity materialEntity = db.getMaterialMapDao().getMaterialById(change.type);
            Material material = Material.matchMaterial(materialEntity.material);

            //set to air if it is a break action
            if (change.action == ActionType.BREAK.getValue()) {
                block.setType(Material.AIR, false);
                continue;
            }

            //skip if material is not found
            if (material == null) {
                //logger.info("Material not found for " + materialEntity.material);
                continue;
            }

            //assuming it is a painting
            if (change.data != 0) {
                Painting painting = (Painting) block.getWorld().spawnEntity(block.getLocation(), EntityType.PAINTING);
                ArtMapEntity artMapEntity = db.getArtMapDao().getArtMapById(change.data);
                //logger.info("Art map entity: " + artMapEntity.artName);
                Art art = Art.getByName(artMapEntity.artName);
                logger.info("Art: " + art);
                List<String> blockDataList = getBlockData(change.blockData);
                for (String value : blockDataList) {
                    if (value.contains("facing")) {
                        String[] facing = value.split("=");
                        painting.setFacingDirection(BlockFace.valueOf(facing[1].toUpperCase()), true);
                        //logger.info("Facing: " + facing[1]);
                        break;
                    }
                }

                assert art != null;
                painting.setArt(art, true);
                continue;
            }

            //below assumes it is a block
            //set the block type
            block.setType(material, false);

            //set the block data
            if (change.blockData != null) {
                String blockData = getBlockDataString(change.blockData);
                block.setBlockData(Bukkit.getServer().createBlockData(material, blockData));
            }
            block.getState().update();
        }

    }

    private static List<String> getBlockData(byte[] blockDataBytes) throws SQLException {
        DatabaseManager db = DatabaseManager.getInstance();
        String asciiString = new String(blockDataBytes, StandardCharsets.US_ASCII);
        String[] values = asciiString.split(",");
        List<String> blockDataList = new ArrayList<>();
        for (String value : values) {
            BlockDataMapEntity blockDataMapEntity = db.getBlockDataMapDao().getBlockDataById(Integer.parseInt(value));
            blockDataList.add(blockDataMapEntity.blockData);
        }
        return blockDataList;
    }

    private static String getBlockDataString(byte[] blockDataBytes) throws SQLException {
        List<String> blockDataList = getBlockData(blockDataBytes);
        StringBuilder blockDataBuilder = new StringBuilder("[");

        for (String value : blockDataList) {
            blockDataBuilder.append(value).append(",");
        }
        if (blockDataBuilder.length() > 1) {
            blockDataBuilder.setLength(blockDataBuilder.length() - 1);
        }
        blockDataBuilder.append("]");

        String blockData = blockDataBuilder.toString();
        // logger.info("Block data: " + blockData);
        return blockData;

    }
}

