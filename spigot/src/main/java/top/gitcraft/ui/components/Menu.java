package top.gitcraft.ui.components;

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
         * ║
         * ╚[Config]
         *
         */
        return new JsonBuilder()
                .clear()
                .addBuilt(header())
                .text("Main Menu").bold()
                .spacing(1)
                .repeat("═",  6).bold()
                .spacing(2)
                .text("\\n\\u2554").text("[").bold().text("World Menu").bold().color(JSONCOLOR.GOLD).click(CLICKACTION.run_command, "/gcworldmenu").hover(HOVERACTION.show_text, "Opens the world menu").text("]").bold()
                .text("\\n\\u2560").text("[").bold().text("Save Menu").bold().color(JSONCOLOR.GOLD).click(CLICKACTION.run_command, "/gcsavemenu").hover(HOVERACTION.show_text, "Open save menu").text("]").bold()
                .text("\\n\\u255a").text("[").bold().text("Config").bold().color(JSONCOLOR.YELLOW).text("]").bold()
                .spacing(5)
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
                .repeat("═",  7).bold()
                .text("\\n\\n\\u2554").text("[").bold().text("JOIN").bold().color(JSONCOLOR.GREEN).click(CLICKACTION.run_command, "/gclist join").hover(HOVERACTION.show_text, "Join a world").text("]").bold()
                .text("\\n\\u2560").text("[").bold().text("CREATE").bold().color(JSONCOLOR.AQUA).click(CLICKACTION.run_command, "/gccreate").hover(HOVERACTION.show_text, "Create a new world").text("]").bold()
                .text("\\n\\u2560").text("[").bold().text("MERGE").bold().color(JSONCOLOR.GOLD).click(CLICKACTION.run_command, "/automerge").hover(HOVERACTION.show_text, "Open merge menu").text("]").bold()
                .text("\\n\\u2551")
                .text("\\n\\u255a").text("[").bold().text("DELETE").bold().color(JSONCOLOR.RED).click(CLICKACTION.run_command, "/gclist delete").hover(HOVERACTION.show_text, "Delete a world").text("]").bold()
                .spacing(3)
                .text("[").bold().text("Main Menu").bold().color(JSONCOLOR.YELLOW).click(CLICKACTION.run_command, "/gcmenu").hover(HOVERACTION.show_text, "Open main menu").text("]").bold()
                .spacing(1)
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
                .text("\\n\\n\\u2554").text("[").bold().text("SAVE").bold().color(JSONCOLOR.GREEN).click(CLICKACTION.run_command, "/gcsave").hover(HOVERACTION.show_text, "Set a savepoint").text("]").bold()
                .text("\\n\\u2560").text("[").bold().text("LOAD").bold().color(JSONCOLOR.AQUA).click(CLICKACTION.run_command, "/gclistsaves load").hover(HOVERACTION.show_text, "Load a savepoint").text("]").bold()
                .text("\\n\\u2551")
                .text("\\n\\u255a").text("[").bold().text("DELETE").bold().color(JSONCOLOR.RED).click(CLICKACTION.run_command, "/gclistsaves deletesave").hover(HOVERACTION.show_text, "Delete a savepoint").text("]").bold()
                .spacing(3)
                .text("[").bold().text("Main Menu").bold().color(JSONCOLOR.YELLOW).click(CLICKACTION.run_command, "/gcmenu").hover(HOVERACTION.show_text, "Open main menu").text("]").bold()
                .spacing(1)
                .build();
    }
}
