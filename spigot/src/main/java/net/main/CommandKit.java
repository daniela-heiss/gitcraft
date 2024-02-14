package net.main;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.plugin.Plugin;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;


public class CommandKit implements CommandExecutor {

    @Override
    public boolean  onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;
        String playerName = player.getName();
        int time = 5000;

        CoreProtectAPI api = getCoreProtect();
        if (api != null){ // Ensure we have access to the API
            api.testAPI(); // Will print out "[CoreProtect] API test successful." in the console.
            List<String[]> log = api.sessionLookup(playerName, time);

            // Verarbeite die Protokolleinträge
            for (String[] entry : log) {
                // Verarbeite das Protokolleintrag
                System.out.println(Arrays.toString(entry));

                // Verwende ParseResult
                CoreProtectAPI.ParseResult result = api.parseResult(entry);

                // Überprüfe, ob das Parsen erfolgreich war
                if (result != null) {
                    System.out.println("Aktion: " + result.getActionId());
                    System.out.println("Spieler: " + result.getPlayer());
                    int x = Integer.parseInt(entry[3]);
                    int y = Integer.parseInt(entry[4]);
                    int z = Integer.parseInt(entry[5]);

                    // Use Bukkit API to get the Material at the specified coordinates
                    Material blockType = player.getWorld().getBlockAt(x, y, z).getType();

                    System.out.println("Block: " + blockType.toString());
                } else {
                    System.out.println("Parsen fehlgeschlagen.");
                }
            }

            System.out.println("Anzahl der Protokolleinträge: " + log.size());
        }

        // If the player (or console) uses our command correctly, we can return true
        return true;
    }

    private CoreProtectAPI getCoreProtect() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");

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
}

