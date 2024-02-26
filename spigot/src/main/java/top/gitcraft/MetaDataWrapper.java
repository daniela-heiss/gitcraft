package top.gitcraft;


import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

public class MetaDataWrapper {

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
