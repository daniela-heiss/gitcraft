package top.gitcraft.utils.configUtils;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class YamlMap {
    // TODO: Add Database credentials
    /*
        Config-File contents
            --> keepGamemodeOnWorldJoin         : default: false
            --> showParticles                   : default: true
            --> deleteSourceWorld               : default: false
            --> cleanSchematicsInterval         : default: false
            --> allowPlayerToDeleteOtherWorld   : default: false
            --> cleanWorlds                     : default: false
            --> skipVoidWorld                   : default: false
            --> selectAreaByItem                : default: true
            --> particleType                    : default:
            --> defaultWorld                    : default: world
    */
    @NotNull
    public static Map<String, Object> getStringObjectMap() {
        Map<String, Object> dataMap = new LinkedHashMap<>();
        dataMap.put("keepGamemodeOnWorldJoin", false);
        dataMap.put("showParticles", true);
        dataMap.put("deleteSourceWorld", false);
        dataMap.put("cleanSchematicsInterval", false);
        dataMap.put("allowPlayerToDeleteOtherWorld", false);
        dataMap.put("cleanWorlds", false);
        dataMap.put("skipVoidWorld", false);
        dataMap.put("selectAreaByItem", true);
        dataMap.put("particleType", "flames");
        dataMap.put("defaultWorld", "world");
        return dataMap;
    }
}
