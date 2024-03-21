package top.gitcraft.utils;

import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.TextDisplay;
import org.bukkit.metadata.FixedMetadataValue;
import top.gitcraft.GitCraft;
import top.gitcraft.utils.enums.JSONCOLOR;

public class MergeWorldText {

    public static void createMergeWorldText(String mergeWorldName, BlockVector3 location, String text, JSONCOLOR color) {
        World voidWorldBkt = Bukkit.getWorld(mergeWorldName);
        Location spawnLocation = new Location(voidWorldBkt, location.getX(), location.getY()+20, location.getZ());
        String jb = new JsonBuilder().text(text).color(color).bold().build();
        int scale = 12;
        if (text.length() > 15){
            scale = 8;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "execute in minecraft:" + mergeWorldName.toLowerCase() + " run summon minecraft:text_display " + spawnLocation.getX() + " " + spawnLocation.getY() + " " + spawnLocation.getZ() + " {text:'" + jb + "',billboard:\"center\",background:0,transformation:{left_rotation:[0f,0f,0f,1f],right_rotation:[0f,0f,0f,1f],translation:[0f,0f,0f],scale:["+scale+"f,"+scale+"f,"+scale+"f]},view_range:16,see_through:false,shadow:true,line_width:500}");
    }
}
