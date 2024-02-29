package top.gitcraft.commands.areaselect;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static top.gitcraft.utils.AreaSelect.getSelection;

public class GetAreaCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("You must be a player to use this command");
            return true;
        }
        Player player = (Player) commandSender;

        player.sendMessage("Area: " + getSelection(player));

        return false;
    }

}
