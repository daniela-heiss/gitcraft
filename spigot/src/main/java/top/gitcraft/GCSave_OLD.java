package top.gitcraft;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GCSave_OLD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("save")) {
            // Hier fügen Sie den Code zum Überprüfen und Speichern hinzu
            System.out.println("command : " + command + "label : " + label + "args" + args);
            if (checkAndSave(sender)) {
                sender.sendMessage("Save successful!");
            } else {
                sender.sendMessage("Save failed.");
            }
            return true;
        }
        return false;
    }

    private boolean checkAndSave(CommandSender sender) {
        int currentID = getCurrentID();
        int highestID = getHighestID();
        int commitID = getCurrentID();
        String commitName = ""; // Hier fügen Sie den Code zum Abrufen der Commit-ID hinzu

        if (currentID < highestID) {
            // Löschen Sie Daten nach der aktuellen ID
            deleteDataAfterID(currentID, sender);
            //savedata
            try (Connection connection = Database.getConnection(true, 1000);
                // Annahme: Der Sender ist ein Spieler
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO co_block (commitID, commitName) VALUES (?, ?)")) {
                preparedStatement.setInt(1, commitID);
                preparedStatement.setString(2, commitName);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // Erhöhen Sie die IDs um eins
            setCurrentID(currentID + 1);
            setHighestID(currentID + 1);

            return true;
        } else if (currentID == highestID) {
            // Erhöhen Sie die IDs um eins
            setCurrentID(currentID + 1);
            setHighestID(currentID + 1);

            return true;
        } else {
            return false;
        }

    }

    private void deleteDataAfterID(int id, CommandSender sender) {
        try (Connection connection = Database.getConnection(true, 1000);
             PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM co_block WHERE commitID > ? AND user = ?")) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, sender.getName()); // Annahme: Der Sender ist ein Spieler
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getCurrentID() {
        // Hier fügen Sie den Code zum Abrufen der aktuellen ID hinzu
        // Beispiel: return yourCurrentID;
        return 0; // Dummy-Wert, ersetzen Sie dies durch Ihre Logik
    }

    private int getHighestID() {
        // Hier fügen Sie den Code zum Abrufen der höchsten ID hinzu
        // Beispiel: return yourHighestID;
        return 0; // Dummy-Wert, ersetzen Sie dies durch Ihre Logik
    }

    private void setCurrentID(int newCurrentID) {
        // Hier fügen Sie den Code zum Setzen der aktuellen ID hinzu
        // Beispiel: yourCurrentID = newCurrentID;
    }

    private void setHighestID(int newHighestID) {
        // Hier fügen Sie den Code zum Setzen der höchsten ID hinzu
        // Beispiel: yourHighestID = newHighestID;
    }
}