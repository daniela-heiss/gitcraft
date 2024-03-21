package top.gitcraft.ui.components;

import top.gitcraft.database.entities.SchematicHistoryEntity;
import top.gitcraft.utils.JsonBuilder;
import top.gitcraft.utils.enums.CLICKACTION;
import top.gitcraft.utils.enums.HOVERACTION;
import top.gitcraft.utils.enums.JSONCOLOR;
import top.gitcraft.utils.enums.LISTTYPE;

import java.util.List;

public class SchematicList {

    public static String schematicListSubset (LISTTYPE type, List<SchematicHistoryEntity> schematicHistory, int page) {
        int ifRest = schematicHistory.size() % 9 != 0 ? 1 : 0;
        int maxPage = (schematicHistory.size() / 9) + ifRest;
        if (schematicHistory.isEmpty()){ // theoretically unreachable, just here for
            page = 1;
            maxPage = 1;
        } else if (page > maxPage) {
            page = maxPage;
        } else if (page < 1) {
            page = 1;
        }
        List<SchematicHistoryEntity> schematicNames = schematicHistory.subList((page-1)*9, Math.min(page * 9, schematicHistory.size()));

        // Initialize JsonBuilder
        JsonBuilder jsonBuilder = new JsonBuilder();

        // Building the JSON message
        jsonBuilder.addBuilt(Menu.header())
                .text("Schematic History\\n").bold()
                .repeat("═", type.getUnderlineLength()).bold()
                .spacing(2);

        // First schematic
        String symbol = schematicNames.size() == 1 ? "\\u2550" : "\\u2554";
        String load = "load";
        jsonBuilder.text(symbol)
                .text("[").bold()
                .text(load.toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + schematicNames.get(0).schematicname).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + schematicNames.get(0).schematicname)
                .text("] ").bold()
                .text(schematicNames.get(0).schematicname).bold()
                .spacing(1);

        schematicNames.remove(0);
        SchematicHistoryEntity lastWorld = null;
        if (!schematicNames.isEmpty()) {
            lastWorld = schematicNames.get(schematicNames.size()-1);
            schematicNames.remove(schematicNames.size()-1);
        }

        // Iterate through schematic Names
        for (SchematicHistoryEntity schematicName : schematicNames) {
            jsonBuilder.text("\\u2560")
                    .text("[").bold()
                    .text(load.toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + schematicName.schematicname).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + schematicName.schematicname)
                    .text("] ").bold()
                    .text(schematicName.schematicname).bold()
                    .spacing(1);
        }

        // Last schematic
        if(!(lastWorld == null)) {
            jsonBuilder.text("\\u255a")
                    .text("[").bold()
                    .text(load.toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + lastWorld.schematicname).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + lastWorld.schematicname)
                    .text("] ").bold()
                    .text(lastWorld.schematicname).bold();
        }

        jsonBuilder.spacing(9-(schematicNames.size()));

        String leftArrow = page == 1 ? new JsonBuilder().text("⏪ ◁ ").bold().color(JSONCOLOR.GRAY).build() : new JsonBuilder().text("⏪ ").bold().hover(HOVERACTION.show_text, "First page").click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()+ " : " + 1).text("◀ ").bold().hover(HOVERACTION.show_text, "Previous page").click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()+ " : " + (page - 1)).build();
        String rightArrow = page == maxPage ? new JsonBuilder().text(" ▷ ⏩").bold().color(JSONCOLOR.GRAY).build() : new JsonBuilder().text(" ▶ ").bold().hover(HOVERACTION.show_text, "Next page").click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()+ " : " + (page + 1)).text("⏩").bold().hover(HOVERACTION.show_text, "Last page").click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()+ " : " + maxPage).build();

        jsonBuilder.text("   ").addBuilt(leftArrow).text(page).bold().color(JSONCOLOR.YELLOW).text("/").bold().text(String.valueOf(maxPage)).bold().color(JSONCOLOR.YELLOW).addBuilt(rightArrow);

        // Adding World Menu button
        jsonBuilder.spacing(2)
                .text("G").bold().color(JSONCOLOR.RED).text("C").bold().color(JSONCOLOR.GOLD).text(":\\\\").bold()
                .text("Main Menu").bold().color(JSONCOLOR.YELLOW).click(CLICKACTION.run_command, "/gcmenu").hover(HOVERACTION.show_text, "Open main menu").bold()
                .text("\\\\").bold().text("Schematic Menu").bold().color(JSONCOLOR.YELLOW).click(CLICKACTION.run_command, "/gcschematicmenu").hover(HOVERACTION.show_text, "Open schematic menu").bold()
                .text("\\\\").bold().text("Schematic History").color(JSONCOLOR.GOLD).hover(HOVERACTION.show_text, "You are here").bold()
                .text(">").bold();

        return jsonBuilder.build();
    }
}
