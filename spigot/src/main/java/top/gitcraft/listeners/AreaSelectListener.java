package top.gitcraft.listeners;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.BlockVector;
import top.gitcraft.utils.areavisualizer.AreaVisualizerHandler;

import java.util.UUID;

import static top.gitcraft.utils.MetaDataWrapper.getMetadata;
import static top.gitcraft.utils.MetaDataWrapper.setMetadata;


public class AreaSelectListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        //check if the player is holding a stick
        if (checkPlayerItemInHand(player, Material.STICK)) {
            //cancel the event so the player doesn't break the block
            event.setCancelled(true);

            ClickType clickType = getClickType(event);
            if (clickType == null) {
                return;
            }

            BlockVector3 blockVector3 = getBlockVector3(event);
            if (blockVector3 == null) {
                return;
            }

            //if the player left clicks, we want to select the first position
            if (clickType == ClickType.LEFT_CLICK) {
                setPos1(player, blockVector3);
            }
            //if the player right clicks, we want to select the second position
            else if (clickType == ClickType.RIGHT_CLICK) {
                setPos2(player, blockVector3);
            }
        }

    }

    //enum to represent the type of click
    private enum ClickType {
        LEFT_CLICK,
        RIGHT_CLICK
    }

    public static void setPos1(Player player, BlockVector3 pos1) {
        player.sendMessage("Pos1 set to " + pos1);
        //set the metadata for the player
        setMetadata(player, "pos1", pos1);

        if(hasPos1AndPos2(player)) {
            AreaVisualizerHandler instance = AreaVisualizerHandler.getInstance();
            UUID uuid = player.getUniqueId();
            BlockVector3 pos2 = getPos2(player);
            int[] visModifierPos1 = getVisualizationModifierPos1(pos2, pos1);
            int[] visModifierPos2 = getVisualizationModifierPos2(pos1, pos2);

            BlockVector parsedPos1 = new BlockVector(pos1.getX()+visModifierPos1[0], pos1.getBlockY()+visModifierPos1[1], pos1.getZ()+visModifierPos1[2]);
            BlockVector parsedPos2 = new BlockVector(pos2.getX()+visModifierPos2[0], pos2.getBlockY()+visModifierPos2[1], pos2.getZ()+visModifierPos2[2]);

            instance.createVisualizeArea(uuid, parsedPos1, parsedPos2);
        }
    }

    public static void setPos2(Player player, BlockVector3 pos2) {
        player.sendMessage("Pos2 set to " + pos2);
        //set the metadata for the player
        setMetadata(player, "pos2", pos2);

        if(hasPos1AndPos2(player)) {
            AreaVisualizerHandler instance = AreaVisualizerHandler.getInstance();
            UUID uuid = player.getUniqueId();
            BlockVector3 pos1 = getPos1(player);
            int[] visModifierPos1 = getVisualizationModifierPos1(pos2, pos1);
            int[] visModifierPos2 = getVisualizationModifierPos2(pos1, pos2);

            BlockVector parsedPos1 = new BlockVector(pos1.getX()+visModifierPos1[0], pos1.getBlockY()+visModifierPos1[1], pos1.getZ()+visModifierPos1[2]);
            BlockVector parsedPos2 = new BlockVector(pos2.getX()+visModifierPos2[0], pos2.getBlockY()+visModifierPos2[1], pos2.getZ()+visModifierPos2[2]);

            instance.createVisualizeArea(uuid, parsedPos1, parsedPos2);
        }
    }

    private boolean checkPlayerItemInHand(Player player, Material material) {
        return player.getInventory().getItemInMainHand().getType() == material;
    }


    private ClickType getClickType(PlayerInteractEvent event) {
        Action action = event.getAction();
        if (action == Action.LEFT_CLICK_AIR || action == Action.LEFT_CLICK_BLOCK) {
            return ClickType.LEFT_CLICK;
        } else if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            return ClickType.RIGHT_CLICK;
        }
        return null;
    }

    private BlockVector3 getBlockVector3(PlayerInteractEvent event) {
        Block block = event.getClickedBlock();
        //make sure block is not null
        if (block == null) {
            return null;
        }
        return BlockVector3.at(block.getX(), block.getY(), block.getZ());
    }


    public static BlockVector3 getPos1(Player player) {
        return getMetadata(player, "pos1", BlockVector3.class);
    }

    public static BlockVector3 getPos2(Player player) {
        return getMetadata(player, "pos2", BlockVector3.class);
    }

    public static boolean hasPos1AndPos2(Player player) {
        return getPos1(player) != null && getPos2(player) != null;
    }

    public static CuboidRegion getSelection(Player player) {
        if (hasPos1AndPos2(player)) {
            return new CuboidRegion(getPos1(player), getPos2(player));
        }
        return null;
    }

    public static int[] getVisualizationModifierPos1(BlockVector3 pos1, BlockVector3 pos2){
        int[] visModifier = new int[3];

        if(pos1.getX() <= pos2.getX()){
            visModifier[0] = 1;
        } else {
            visModifier[0] = 0;
        }

        if(pos1.getY() <= pos2.getY()){
            visModifier[1] = 1;
        } else {
            visModifier[1] = 0;
        }

        if(pos1.getZ() <= pos2.getZ()){
            visModifier[2] = 1;
        } else {
            visModifier[2] = 0;
        }

        return visModifier;
    }

    public static int[] getVisualizationModifierPos2(BlockVector3 pos1, BlockVector3 pos2){
        int[] visModifier = new int[3];

        if(pos1.getX() < pos2.getX()){
            visModifier[0] = 1;
        } else {
            visModifier[0] = 0;
        }

        if(pos1.getY() < pos2.getY()){
            visModifier[1] = 1;
        } else {
            visModifier[1] = 0;
        }

        if(pos1.getZ() < pos2.getZ()){
            visModifier[2] = 1;
        } else {
            visModifier[2] = 0;
        }

        return visModifier;
    }
}
