package top.gitcraft.commands;

import com.sk89q.worldedit.math.Vector3;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockVector;
import top.gitcraft.GitCraft;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.BlockDao;
import top.gitcraft.database.daos.MaterialMapDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.BlockEntity;
import top.gitcraft.utils.methods.AreaVizulizer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

public class TestAreaVisulize implements CommandExecutor {
    private final GitCraft gitCraft;
    public TestAreaVisulize(GitCraft gitCraft) {
        this.gitCraft = gitCraft;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Player player = (Player) sender;

        new AreaVizulizer(gitCraft).visualizeAreaSelection(Bukkit.getWorld("world"), new BlockVector(0, 100, 0), new BlockVector(10, 110, 10));

    return true;
    }

}