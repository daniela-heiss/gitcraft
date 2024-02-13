package net.main;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;

private CoreProtectAPI getCoreProtect() {
    Plugin plugin = getServer().getPluginManager().getPlugin("CoreProtect");

    // Check that CoreProtect is loaded
    if (plugin == null || !(plugin instanceof CoreProtect)) {
        return null;
    }

    // Check that the API is enabled
    CoreProtectAPI CoreProtect = ((CoreProtect) plugin).getAPI();
    if (CoreProtect.isEnabled() == false) {
        return null;
    }

    // Check that a compatible version of the API is loaded
    if (CoreProtect.APIVersion() < 9) {
        return null;
    }

    return CoreProtect;
}

public class CommandKit implements CommandExecutor {

    @Override
    public boolean  onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player player) {

            // Create a new ItemStack (type: diamond)
            ItemStack diamond = new ItemStack(Material.DIAMOND);

            // Create a new ItemStack (type: brick)
            ItemStack bricks = new ItemStack(Material.BRICK);

            // Set the amount of the ItemStack
            bricks.setAmount(20);

            // Give the player our items (comma-seperated list of all ItemStack)
            player.getInventory().addItem(bricks, diamond);
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
