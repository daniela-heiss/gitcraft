package top.gitcraft.utils;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.entity.Player;

import static top.gitcraft.utils.MetaDataWrapper.getMetadata;
import static top.gitcraft.utils.MetaDataWrapper.setMetadata;

public class AreaSelect {

    /**
     * AreaSelect
     * This class is responsible for handling the selection of areas.
     *
     * @usage import static methods
     */

    private AreaSelect() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Set the pos1 for the player
     *
     * @param player the owning player
     * @param pos1   the position
     */
    public static void setPos1(Player player, BlockVector3 pos1) {
        player.sendMessage("Pos1 set to " + pos1);
        //set the metadata for the player
        setMetadata(player, "pos1", pos1);
    }

    /**
     * Set the pos2 for the player
     *
     * @param player the owning player
     * @param pos2   the position
     */
    public static void setPos2(Player player, BlockVector3 pos2) {
        player.sendMessage("Pos2 set to " + pos2);
        //set the metadata for the player
        setMetadata(player, "pos2", pos2);
    }

    /**
     * Get the pos1 for the player
     *
     * @param player the owning player
     * @return the position
     */
    public static BlockVector3 getPos1(Player player) {
        return getMetadata(player, "pos1", BlockVector3.class);
    }

    /**
     * Get the pos2 for the player
     *
     * @param player the owning player
     * @return the position
     */
    public static BlockVector3 getPos2(Player player) {
        return getMetadata(player, "pos2", BlockVector3.class);
    }

    /**
     * Check if the player has both pos1 and pos2 set
     *
     * @param player the owning player
     * @return true if both pos1 and pos2 are set
     */
    public static boolean hasPos1AndPos2(Player player) {
        return getPos1(player) != null && getPos2(player) != null;
    }

    /**
     * Get the selection for the player
     *
     * @param player the owning player
     * @return the selection as a CuboidRegion
     */
    public static CuboidRegion getSelection(Player player) {
        if (hasPos1AndPos2(player)) {
            return new CuboidRegion(getPos1(player), getPos2(player));
        }
        return null;
    }
}
