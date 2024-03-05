package top.gitcraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import top.gitcraft.commands.merging.MergeCommand;
import top.gitcraft.commands.world.CreateCommand;
import top.gitcraft.commands.world.DeleteCommand;
import top.gitcraft.commands.world.JoinCommand;

import java.util.Arrays;

import static top.gitcraft.ui.components.Menu.*;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class GitcCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }
        Player player = (Player) sender;

        // Open join list if no arguments are provided
        if (args.length == 0) {
            dispatchTellRawCommand(player, menuMainMenu());
            return true;
        }
        // remove first element of args
        String[] newargs = Arrays.copyOfRange(args, 1, args.length);

        switch (args[0]) {
            case "main":
                dispatchTellRawCommand(player, menuMainMenu());
                return true;
            case "world":
                dispatchTellRawCommand(player, menuWorldMenu());
                return true;
            case "join":
                new JoinCommand().onCommand(sender, command, label, newargs);
                return true;
            case "create":
                new CreateCommand().onCommand(sender, command, label, newargs);
                return true;
            case "merge":
                new MergeCommand().onCommand(sender, command, label, newargs);
                return true;
            case "delete":
                new DeleteCommand().onCommand(sender, command, label, newargs);
                return true;

            default:
                dispatchTellRawCommand(player, menuMainMenu());
                return true;
        }
    }
}
