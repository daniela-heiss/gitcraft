package top.gitcraft.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import top.gitcraft.commands.loadsave.DeleteSaveCommand;
import top.gitcraft.commands.loadsave.LoadCommand;
import top.gitcraft.commands.merging.MergeMenuCommand;
import top.gitcraft.commands.world.CreateCommand;
import top.gitcraft.commands.world.DeleteCommand;
import top.gitcraft.commands.world.JoinCommand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static top.gitcraft.ui.components.Menu.*;
import static top.gitcraft.utils.CommandUtils.dispatchTellRawCommand;

public class GitcCommand implements TabExecutor {

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
        if (args.length > 1 && (Objects.equals(newargs[0], "list") || Objects.equals(newargs[0], "menu"))) {
            newargs = new String[0];
        }


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
                new MergeMenuCommand().onCommand(sender, command, label, newargs);
                return true;
            case "delete":
                new DeleteCommand().onCommand(sender, command, label, newargs);
                return true;
            case "save":
                new top.gitcraft.commands.loadsave.SaveCommand().onCommand(sender, command, label, newargs);
                return true;
            case "loadSave":
                new LoadCommand().onCommand(sender, command, label, newargs);
                return true;
            case "deleteSave":
                new DeleteSaveCommand().onCommand(sender, command, label, newargs);
                return true;

            default:
                dispatchTellRawCommand(player, menuMainMenu());
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (!(sender instanceof Player)) {
            return null; // Return null for non-player senders
        }
        List<String> completions;
        completions = Arrays.asList("main", "world", "join", "create", "merge", "delete", "save", "loadSave", "deleteSave");

        if (args.length < 2) {
            return completions;
        }

        if (args.length == 2 && (Objects.equals(args[0], "join") || Objects.equals(args[0], "create") || Objects.equals(args[0], "delete"))) {
            completions = Arrays.asList("list", "<worldName>");
            return completions;
        }else if (args.length == 2 && Objects.equals(args[0], "merge")) {
            completions = Arrays.asList("area", "auto");
            return completions;
        } else if (args.length == 2 && Objects.equals(args[0], "deleteSave") || Objects.equals(args[0], "loadSave")) {
            completions = Arrays.asList("list", "<saveName>");
            return completions;
        }else if(args.length == 2 && Objects.equals(args[0], "save")){
            completions = Arrays.asList("menu", "<saveName>");
            return completions;
        }
        completions = new ArrayList<>();
        return completions;
    }
}
