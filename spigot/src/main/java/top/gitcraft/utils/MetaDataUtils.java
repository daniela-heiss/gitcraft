package top.gitcraft.utils;


import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import top.gitcraft.GitCraft;

public class MetaDataUtils {

    public static void setMetadata(Player player, String key, Object value) {
        player.setMetadata(key, new FixedMetadataValue(GitCraft.getPlugin(GitCraft.class), value));
    }

    public static <T> T getMetadata(Player player, String key, Class<T> clazz) {
        for (MetadataValue metadataValue : player.getMetadata(key)) {
            if (metadataValue.getOwningPlugin() instanceof GitCraft) {
                return clazz.cast(metadataValue.value());
            }
        }
        return null;
    }

}
