package top.gitcraft.commands.merging;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static top.gitcraft.listeners.AreaSelectListener.getSelection;
import static top.gitcraft.utils.MergeUtils.pasteMergeAreas;


public class TestMergingCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }
        Player player = (Player) commandSender;

        if (strings.length != 3) {
            player.sendMessage("Usage: /testmerge <fromWorldName> <targetWorldName> <mergeWorldName>");
            return true;
        }

        String fromWorldName = strings[0];
        String targetWorldName = strings[1];
        String mergeWorldName = strings[2];

        player.sendMessage("Merging " + fromWorldName + " and " + targetWorldName + " into " + mergeWorldName);

        pasteMergeAreas(player, fromWorldName, targetWorldName, mergeWorldName, getSelection(player));

        return true;
    }
}
