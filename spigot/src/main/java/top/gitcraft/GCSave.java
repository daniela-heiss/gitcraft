package top.gitcraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class GCSave implements CommandExecutor {

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



}