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

import static top.gitcraft.utils.AreaSelect.setPos1;
import static top.gitcraft.utils.AreaSelect.setPos2;
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
}
