package top.gitcraft.ui.components;

import org.bukkit.Bukkit;
import org.bukkit.World;
import top.gitcraft.utils.JsonBuilder;
import top.gitcraft.utils.enums.LISTTYPE;
import top.gitcraft.utils.enums.CLICKACTION;
import top.gitcraft.utils.enums.HOVERACTION;
import top.gitcraft.utils.enums.JSONCOLOR;

import java.util.ArrayList;
import java.util.List;

public class WorldList {

    public static String worldListSubset(LISTTYPE type, List<String> worldNames) {
        // Initialize JsonBuilder
        JsonBuilder jsonBuilder = new JsonBuilder();

        // Building the JSON message
        jsonBuilder.addBuilt(Menu.header())
                .text("World " + type.name().substring(0, 1).toUpperCase() + type.name().substring(1).toLowerCase() + " List\\n").bold()
                .repeat("═", type.getUnderlineLength()).bold()
                .spacing(2);

        // First world
        jsonBuilder.text("\\u2554")
                .text("[").bold()
                .text(type.name().toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + worldNames.get(0)).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + worldNames.get(0))
                .text("] ").bold()
                .text(worldNames.get(0)).bold()
                .spacing(1);

        worldNames.remove(0);
        String lastWorld = null;
        if (!worldNames.isEmpty()) {
            lastWorld = worldNames.get(worldNames.size()-1);
            worldNames.remove(worldNames.size()-1);
        }
        // Iterate through world names
        for (String worldName : worldNames) {
            jsonBuilder.text("\\u2560")
                    .text("[").bold()
                    .text(type.name().toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + worldName).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + worldName)
                    .text("] ").bold()
                    .text(worldName).bold()
                    .spacing(1);
        }

        // Last world
        if(!(lastWorld == null)) {
            jsonBuilder.text("\\u255a")
                    .text("[").bold()
                    .text(type.name().toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + lastWorld).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + lastWorld)
                    .text("] ").bold()
                    .text(lastWorld).bold()
                    .spacing(1);
        }

        // Adding World Menu button
        jsonBuilder.spacing(3)
                .text("[").bold()
                .text("World Menu").bold().color(JSONCOLOR.YELLOW).click(CLICKACTION.run_command, "/gcworldmenu").hover(HOVERACTION.show_text, "Open world menu")
                .text("]").bold()
                .spacing(1);

        return jsonBuilder.build();
    }

    public static String worldListAll(LISTTYPE type) {
        List<String> worldNames = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            worldNames.add(world.getName());
        }
        return worldListSubset(type, worldNames);
    }
}
