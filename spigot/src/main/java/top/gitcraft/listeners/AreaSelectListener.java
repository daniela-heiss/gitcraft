package top.gitcraft.listeners;

import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AreaSelectListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        //check if the player is holding a stick
        if (checkPlayerItemInHand(player, Material.STICK)) {
            ClickType clickType = getClickType(event);
            if (clickType == null) {
                return;
            }

            BlockVector3 blockVector3 = getBlockVector3(event);

            //if the player left clicks, we want to select the first position
            if (clickType == ClickType.LEFT_CLICK) {
                player.sendMessage("Pos1 set to " + blockVector3);

            } else if (clickType == ClickType.RIGHT_CLICK) {
                player.sendMessage("Pos2 set to " + blockVector3);
            }
        }
    }


    private boolean checkPlayerItemInHand(Player player, Material material) {
        return player.getInventory().getItemInMainHand().getType() == material;
    }

    private enum ClickType {
        LEFT_CLICK,
        RIGHT_CLICK
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
        return BlockVector3.at(event.getClickedBlock().getX(), event.getClickedBlock().getY(), event.getClickedBlock().getZ());
    }

}
