package top.gitcraft.ui.components;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import top.gitcraft.utils.JsonBuilder;
import top.gitcraft.utils.enums.CLICKACTION;
import top.gitcraft.utils.enums.HOVERACTION;
import top.gitcraft.utils.enums.JSONCOLOR;

public class Menu {
    public static String header() {
        return new JsonBuilder()
                .clear()
                .text("══").bold()
                .text(" Git").bold().color(JSONCOLOR.RED)
                .text("Craft").bold().color(JSONCOLOR.GOLD)
                .text(" ══").bold()
                .spacing(3)
                .build();
    }

    public static String menuMainMenu() {
        /*
         * ══ GitCraft ══
         *
         *  Main Menu
         *  ══════
         *
         * ╔[World Menu]
         * ╠[Save Menu]
         * ╚[Config]
         *
         */
        return new JsonBuilder()
                .clear()
                .addBuilt(header())
                .text("Main Menu").bold()
                .spacing(1)
                .repeat("═", 6).bold()
                .spacing(2)
                .text("\\n\\u2554").text("[").bold().text("World Menu").bold().color(JSONCOLOR.GOLD).click(CLICKACTION.run_command, "/gcworldmenu").hover(HOVERACTION.show_text, "Opens the world menu").text("]").bold()
                .text("\\n\\u2560").text("[").bold().text("Save Menu").bold().color(JSONCOLOR.GOLD).click(CLICKACTION.run_command, "/gcsavemenu").hover(HOVERACTION.show_text, "Open save menu").text("]").bold()
                .text("\\n\\u255a").text("[").bold().text("Config").bold().color(JSONCOLOR.YELLOW).text("]").bold()
                .spacing(9)
                .addBuilt(consoleFooter(1, "Main Menu"))
                .build();
    }

    public static String menuConfigMenu() {
        return "";
    }

    public static String menuWorldMenu() {
        /*
         * ══ GitCraft ══
         *
         *  World Menu
         *  ═══════
         *
         * ╔[JOIN]
         * ╠[CREATE]
         * ╠[MERGE]
         * ║
         * ╚[DELETE]
         *
         *
         * [World Menu]
         *
         */
        return new JsonBuilder()
                .clear()
                .addBuilt(header())
                .text("World Menu").bold()
                .spacing(1)
                .repeat("═", 7).bold()
                .spacing(3)
                .text("\\u2554").text("[").bold().text("JOIN").bold().color(JSONCOLOR.GREEN).click(CLICKACTION.run_command, "/gclist join").hover(HOVERACTION.show_text, "Join a world").text("]").bold()
                .text("\\n\\u2560").text("[").bold().text("CREATE").bold().color(JSONCOLOR.AQUA).click(CLICKACTION.run_command, "/gccreate").hover(HOVERACTION.show_text, "Create a new world").text("]").bold()
                .text("\\n\\u2560").text("[").bold().text("MERGE").bold().color(JSONCOLOR.GOLD).click(CLICKACTION.run_command, "/gcmerge false").hover(HOVERACTION.show_text, "Open merge menu").text("]").bold()
                .text("\\n\\u2551")
                .text("\\n\\u255a").text("[").bold().text("DELETE").bold().color(JSONCOLOR.RED).click(CLICKACTION.run_command, "/gclist delete").hover(HOVERACTION.show_text, "Delete a world").text("]").bold()
                .spacing(7)
                .addBuilt(consoleFooter(2, "World Menu"))
                .build();
    }

    public static String menuSaveMenu() {
        /*
         * ══ GitCraft ══
         *
         *  Save Menu
         *  ═══════
         *
         * ╔[SAVE]
         * ╠[LOAD]
         * ║
         * ╚[DELETE]
         *
         *
         * [World Menu]
         *
         */
        return new JsonBuilder()
                .clear()
                .addBuilt(header())
                .text("Save Menu").bold()
                .spacing(1)
                .repeat("═",  7).bold()
                .text("\\n\\n\\u2554").text("[").bold().text("SAVE").bold().color(JSONCOLOR.GREEN).click(CLICKACTION.suggest_command, "/gcsave ").hover(HOVERACTION.show_text, "Get the command to set a savepoint. You need to choose a name for your save").text("]").bold()
                .text("\\n\\u2560").text("[").bold().text("LOAD").bold().color(JSONCOLOR.AQUA).click(CLICKACTION.run_command, "/gclistsaves load").hover(HOVERACTION.show_text, "Load a savepoint").text("]").bold()
                .text("\\n\\u2551")
                .text("\\n\\u255a").text("[").bold().text("DELETE").bold().color(JSONCOLOR.RED).click(CLICKACTION.run_command, "/gclistsaves deletesave").hover(HOVERACTION.show_text, "Delete a savepoint").text("]").bold()
                .spacing(3)
                .addBuilt(consoleFooter(2, "Save Menu"))
                .build();
    }

    public static String menuMergeMenu(Player player, String args, BlockVector3 pos1, BlockVector3 pos2, CuboidRegion cr) {
        /*
         * ══ GitCraft ══
         *
         *  Merge Menu
         *  ═════════
         *
         * From: worldName1 → Into: worldName2
         *
         *
         * All changes [X] [ ] Area select
         *
         * Pos1: X1 / Y1 / Z1
         * Pos2: X2 / Y2 / Z2
         *
         * [Merge]
         *
         * [Main Menu]
         *
         */
        JsonBuilder jsonMessage = new JsonBuilder()
                .clear()
                .addBuilt(header())
                .text("Merge Menu").bold()
                .spacing(1)
                .repeat("═", 7).bold()
                .spacing(2)
                .text("From: ").bold().text(player.getWorld().getName()).bold().color(JSONCOLOR.GREEN)
                .text(" → Into: ").bold().text("world\\n").bold().color(JSONCOLOR.GREEN)
                .spacing(2);
        if (args.equals("area")) {
            if (cr == null) {
                jsonMessage.text("All changes [ ] ").click(CLICKACTION.run_command, "/gcmerge auto").hover(HOVERACTION.show_text, "Activate all changes").text("[X] Area select").bold().underlined().click(CLICKACTION.run_command, "/gcmerge area").hover(HOVERACTION.show_text, "Refresh area select")
                        .spacing(2)
                        .text("Pos1: ").bold().text("No area selected").color(JSONCOLOR.RED).bold()
                        .spacing(1)
                        .text("Pos2: ").bold().text("No area selected").color(JSONCOLOR.RED).bold();

            } else {
                pos1 = cr.getPos1();
                pos2 = cr.getPos2();
                jsonMessage.text("All changes [ ] ").click(CLICKACTION.run_command, "/gcmerge auto").hover(HOVERACTION.show_text, "Activate all changes").text("[X] Area select").bold().underlined().click(CLICKACTION.run_command, "/gcmerge area").hover(HOVERACTION.show_text, "Refresh area select")
                        .spacing(2)
                        .text("Pos1: ").bold().text(String.valueOf(pos1.getX())).color(JSONCOLOR.RED).text(" / ").bold().text(String.valueOf(pos1.getY())).color(JSONCOLOR.GREEN).text(" / ").bold().text(String.valueOf(pos1.getZ())).color(JSONCOLOR.BLUE)
                        .spacing(1)
                        .text("Pos2: ").bold().text(String.valueOf(pos2.getX())).color(JSONCOLOR.RED).text(" / ").bold().text(String.valueOf(pos2.getY())).color(JSONCOLOR.GREEN).text(" / ").bold().text(String.valueOf(pos2.getZ())).color(JSONCOLOR.BLUE);
            }
        } else {
            jsonMessage.text("All changes [X]").bold().underlined().click(CLICKACTION.run_command, "/gcmerge auto").hover(HOVERACTION.show_text, "Refresh all changes").text(" [ ] Area select").click(CLICKACTION.run_command, "/gcmerge area").hover(HOVERACTION.show_text, "Activate area select")
                    .spacing(2)
                    .text("Pos1: ").bold().text(String.valueOf(pos1.getX())).color(JSONCOLOR.RED).text(" / ").bold().text(String.valueOf(pos1.getY())).color(JSONCOLOR.GREEN).text(" / ").bold().text(String.valueOf(pos1.getZ())).color(JSONCOLOR.BLUE)
                    .spacing(1)
                    .text("Pos2: ").bold().text(String.valueOf(pos2.getX())).color(JSONCOLOR.RED).text(" / ").bold().text(String.valueOf(pos2.getY())).color(JSONCOLOR.GREEN).text(" / ").bold().text(String.valueOf(pos2.getZ())).color(JSONCOLOR.BLUE);

        }
        jsonMessage.spacing(3)
                .text("[").bold().text("Merge").bold().color(JSONCOLOR.GOLD).click(CLICKACTION.run_command, "/automerge").hover(HOVERACTION.show_text, "Merge the worlds").text("]").bold()
                .spacing(3)
                //.text("[").bold().text("World Menu").bold().color(JSONCOLOR.YELLOW).click(CLICKACTION.run_command, "/gcworldmenu").hover(HOVERACTION.show_text, "Open world menu").text("]").bold()
                .addBuilt(consoleFooter(3, "Merge Menu"))
                .spacing(0);

        return jsonMessage.build();
    }

    public static String consoleFooter(int level, String menu) {
        JSONCOLOR[] highlight = new JSONCOLOR[]{JSONCOLOR.YELLOW, JSONCOLOR.YELLOW, JSONCOLOR.YELLOW, JSONCOLOR.YELLOW, JSONCOLOR.YELLOW};
        highlight[level] = JSONCOLOR.GOLD;
        JsonBuilder jsonMessage = new JsonBuilder();

        jsonMessage.text("G").bold().color(JSONCOLOR.RED).text("C").bold().color(JSONCOLOR.GOLD).text(":\\\\").bold()
        .text("Main Menu").bold().color(highlight[1]).click(CLICKACTION.run_command, "/gcmenu").hover(HOVERACTION.show_text, "Open main menu").bold();
        if (level > 1) {
            jsonMessage.text("\\\\").bold().text("World Menu").bold().color(highlight[2]).click(CLICKACTION.run_command, "/gcworldmenu").hover(HOVERACTION.show_text, "Open world menu").bold();
        }
        if (level > 2) {
            jsonMessage.text("\\\\").bold().text(menu).color(highlight[3]).hover(HOVERACTION.show_text, "You are here").bold();
        }
        jsonMessage.text(">").bold();

        return jsonMessage.build();
    }
}
