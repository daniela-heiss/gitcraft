package net.main;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import net.coreprotect.CoreProtect;
import net.coreprotect.CoreProtectAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.coreprotect.config.Config;
import net.coreprotect.config.ConfigHandler;
import net.coreprotect.database.Database;
import net.coreprotect.database.statement.UserStatement;

public class CGSave extends JavaPlugin implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("gcsave")) {
            // Fügen Sie hier Ihren Code für die Kommandozeilenargumente hinzu, wenn erforderlich

            String username = String.valueOf(sender);
            int offset = 3600; // Offset in Sekunden

            List<String[]> lookupResults = GCSave.performLookup(username, offset);

            for (String[] result : lookupResults) {
                // Hier können Sie die Ergebnisse ausgeben oder weiter verarbeiten
                for (String data : result) {
                    sender.sendMessage(data);
                }
            }
            return true;
        }
        return false;
    }



    public static final int ID = 0;

    private GCSave() {
        throw new IllegalStateException("API class");
    }

    public static List<String[]> performLookup(String user, int offset) {
        List<String[]> result = new ArrayList<>();
        if (!Config.getGlobal().API_ENABLED) {
            return result;
        }

        try (Connection connection = Database.getConnection(false, 1000)) {
            if (connection == null || user == null) {
                return result;
            }

            String type = String.valueOf(ID);
            int time = (int) (System.currentTimeMillis() / 1000L);
            int checkTime = 0;
            if (offset > 0) {
                checkTime = time - offset;
            }

            if (ConfigHandler.playerIdCache.get(user.toLowerCase(Locale.ROOT)) == null) {
                UserStatement.loadId(connection, user, null);
            }
            int userId = ConfigHandler.playerIdCache.get(user.toLowerCase(Locale.ROOT));

            try (Statement statement = connection.createStatement()) {
                String query = "SELECT time,user,wid,x,y,z,action FROM " + ConfigHandler.prefix + "session WHERE user = '" + userId + "' AND time > '" + checkTime + "' ORDER BY rowid DESC";
                ResultSet results = statement.executeQuery(query);
                while (results.next()) {
                    String resultTime = results.getString("time");
                    int resultUserId = results.getInt("user");
                    String resultWorldId = results.getString("wid");
                    String resultX = results.getString("x");
                    String resultY = results.getString("y");
                    String resultZ = results.getString("z");
                    String resultAction = results.getString("action");

                    if (ConfigHandler.playerIdCacheReversed.get(resultUserId) == null) {
                        UserStatement.loadName(connection, resultUserId);
                    }
                    String resultUser = ConfigHandler.playerIdCacheReversed.get(resultUserId);

                    String[] lookupData = new String[] { resultTime, resultUser, resultX, resultY, resultZ, resultWorldId, type, resultAction };
                    result.add(lookupData);
                }
                results.close();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
