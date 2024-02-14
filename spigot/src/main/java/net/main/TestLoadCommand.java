
package net.main;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestLoadCommand implements CommandExecutor {

    public CommitData[] dummyData = new CommitData[]{
        new CommitData("world", 0, 100, 0, "minecraft:grass_block", new String[]{"snowy=false"}, 0),
        new CommitData("world", 1, 101, 1, "minecraft:grass_block", new String[]{"snowy=false"}, 0),
        new CommitData("world", 2, 102, 2, "minecraft:grass_block", new String[]{"snowy=false"}, 0),
    };

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Loading commit...");

        // position refers to a block in the world including the block data, the block material, position, world, action etc.
        for (CommitData position : dummyData) {
            Location loc = new Location(Bukkit.getWorld("world"), position.x, position.y, position.z);
            loc.getBlock().setBlockData(Bukkit.createBlockData(position.block + "[" + position.blockData +"]"), false);
        }

        return true;
    }
}