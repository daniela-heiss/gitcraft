
package net.main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TestLoadCommand implements CommandExecutor {

    public CommitData[] dummyData = new CommitData[]{

        new CommitData("world", 0, 100, 0, "minecraft:grass_block", new String[]{"snowy=true"}, 0),
        new CommitData("world", 1, 101, 1, "minecraft:grass_block", new String[]{"snowy=false"}, 0),
        new CommitData("world", 2, 102, 2, "minecraft:grass_block", new String[]{"snowy=true"}, 0),

    };

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        sender.sendMessage("Loading commit...");

        Bukkit.broadcastMessage("Loaded commit data from file - " + dummyData.length + " changes");

        // position refers to a block in the world including the block data, the block material, position, world, action etc.
        for (CommitData position : dummyData) {
            Location loc = new Location(Bukkit.getWorld("world"), position.x, position.y, position.z);
            loc.getBlock().setBlockData(Bukkit.createBlockData(position.block + "[" + position.blockData[0] +"]"), false);
        }
        // create an area 100x100x100 of gold blocks
        for (int x = 100; x < 200; x++) {
            for (int y = 100; y < 200; y++) {
                for (int z = 100; z < 200; z++) {
                    Location loc = new Location(Bukkit.getWorld("world"), x, y, z);
                    loc.getBlock().setBlockData(Bukkit.createBlockData("minecraft:diamond_block"), false);
                }
            }
        }
        return true;
    }
}