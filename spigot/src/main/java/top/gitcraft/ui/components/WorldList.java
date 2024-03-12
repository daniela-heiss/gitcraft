package top.gitcraft.ui.components;

import org.bukkit.Bukkit;
import org.bukkit.World;
import top.gitcraft.utils.JsonBuilder;
import top.gitcraft.utils.enums.LISTTYPE;
import top.gitcraft.utils.enums.CLICKACTION;
import top.gitcraft.utils.enums.HOVERACTION;
import top.gitcraft.utils.enums.JSONCOLOR;

import javax.json.Json;
import java.util.ArrayList;
import java.util.List;

public class WorldList {

    public static String worldListSubset(LISTTYPE type, List<String> worldNamesAll, int page) {
        int ifRest = worldNamesAll.size() % 9 != 0 ? 1 : 0;
        int maxPage = (worldNamesAll.size() / 9) + ifRest;
        if (worldNamesAll.isEmpty()){ // theoretically unreachable, just here for
            page = 1;
            maxPage = 1;
        } else if (page > maxPage) {
            page = maxPage;
        } else if (page < 1) {
            page = 1;
        }
        List<String> worldNames = worldNamesAll.subList((page-1)*9, Math.min(page * 9, worldNamesAll.size()));

        // Initialize JsonBuilder
        JsonBuilder jsonBuilder = new JsonBuilder();

        // Building the JSON message
        jsonBuilder.addBuilt(Menu.header())
                .text("World " + type.name().substring(0, 1).toUpperCase() + type.name().substring(1).toLowerCase() + " List\\n").bold()
                .repeat("═", type.getUnderlineLength()).bold()
                .spacing(2);

        // First world
        String symbol = worldNames.size() == 1 ? "\\u2550" : "\\u2554";
        jsonBuilder.text(symbol)
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
                    .text(lastWorld).bold();
        }
        jsonBuilder.spacing(Math.max(0, 9-(worldNames.size())));

        String leftArrow = page == 1 ? new JsonBuilder().text("⏪ ◁ ").bold().color(JSONCOLOR.GRAY).build() : new JsonBuilder().text("⏪ ").bold().hover(HOVERACTION.show_text, "First page").click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()+ " : " + 1).text("◀ ").bold().hover(HOVERACTION.show_text, "Previous page").click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()+ " : " + (page - 1)).build();
        String rightArrow = page == maxPage ? new JsonBuilder().text(" ▷ ⏩").bold().color(JSONCOLOR.GRAY).build() : new JsonBuilder().text(" ▶ ").bold().hover(HOVERACTION.show_text, "Next page").click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()+ " : " + (page + 1)).text("⏩").bold().hover(HOVERACTION.show_text, "Last page").click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()+ " : " + maxPage).build();

        jsonBuilder.text("   ").addBuilt(leftArrow).text(page).bold().color(JSONCOLOR.YELLOW).text("/").bold().text(String.valueOf(maxPage)).bold().color(JSONCOLOR.YELLOW).addBuilt(rightArrow);


        // Adding World Menu button
        jsonBuilder.spacing(2)
                .text("G").bold().color(JSONCOLOR.RED).text("C").bold().color(JSONCOLOR.GOLD).text(":\\\\").bold()
                .text("Main Menu").bold().color(JSONCOLOR.YELLOW).click(CLICKACTION.run_command, "/gcmenu").hover(HOVERACTION.show_text, "Open main menu").bold()
                .text("\\\\").bold().text("World Menu").bold().color(JSONCOLOR.YELLOW).click(CLICKACTION.run_command, "/gcworldmenu").hover(HOVERACTION.show_text, "Open world menu").bold()
                .text("\\\\").bold().text(type.name()).color(JSONCOLOR.GOLD).hover(HOVERACTION.show_text, "You are here").bold()
                .text(">").bold();

        return jsonBuilder.build();
    }

    public static String worldListAll(LISTTYPE type, int page) {
        List<String> worldNames = new ArrayList<>();
        for (World world : Bukkit.getWorlds()) {
            worldNames.add(world.getName());
        }
        return worldListSubset(type, worldNames, page);
    }
}
