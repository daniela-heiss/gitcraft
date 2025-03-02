package top.gitcraft.ui.components;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import top.gitcraft.GitCraft;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.SaveDao;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.daos.WorldDao;
import top.gitcraft.database.entities.SaveEntity;
import top.gitcraft.database.entities.UserEntity;
import top.gitcraft.database.entities.WorldEntity;
import top.gitcraft.utils.JsonBuilder;
import top.gitcraft.utils.enums.CLICKACTION;
import top.gitcraft.utils.enums.HOVERACTION;
import top.gitcraft.utils.enums.JSONCOLOR;
import top.gitcraft.utils.enums.LISTTYPE;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class SaveList {
    public static String saveListSubset(LISTTYPE type, List<SaveEntity> savesAll, int page) {
        int ifRest = savesAll.size() % 7 != 0 ? 1 : 0;
        int maxPage = (savesAll.size() / 7) + ifRest;
        if (savesAll.isEmpty()){
            page = 1;
            maxPage = 1;
        } else if (page > maxPage) {
            page = maxPage;
        } else if (page < 1) {
            page = 1;
        }
        List<SaveEntity> saves = savesAll;
        if (!savesAll.isEmpty()){
            saves = savesAll.subList((page-1)*7, Math.min(page * 7, savesAll.size()));
        }

        // Initialize JsonBuilder
        JsonBuilder jsonBuilder = new JsonBuilder();
        JSONCOLOR rolledBackColor = JSONCOLOR.DARK_GRAY;

        // Building the JSON message
        jsonBuilder.addBuilt(Menu.header())
                .text(type.name().substring(0, 1).toUpperCase() + type.name().substring(1).toLowerCase() + " List\\n").bold()
                .repeat("═", type.getUnderlineLength()).bold()
                .spacing(2);

        if (!saves.isEmpty()) {
            String symbol = saves.size() == 1 ? "\\u2550" : "\\u2554";
            // First Save
            if (saves.get(0).rolledBack == 0) {
                jsonBuilder.text(symbol)
                        .text("[").bold()
                        .text(type.name().toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + saves.get(0).saveName).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + saves.get(0).saveName)
                        .text("] ").bold()
                        .text(saves.get(0).saveName).bold()
                        .spacing(1);
            } else {
                jsonBuilder.text(symbol)
                        .text("[").bold()
                        .text(type.name().toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + saves.get(0).saveName).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + saves.get(0).saveName)
                        .text("] ").bold()
                        .text(saves.get(0).saveName).bold().color(rolledBackColor)
                        .spacing(1);
            }

            saves.remove(0);
            SaveEntity lastSave = null;
            if (!saves.isEmpty()) {
                lastSave = saves.get(saves.size() - 1);
                saves.remove(saves.size() - 1);
            }
            // Iterate through saves
            for (SaveEntity save : saves) {
                if (save.rolledBack == 0) {
                    jsonBuilder.text("\\u2560")
                            .text("[").bold()
                            .text(type.name().toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + save.saveName).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + save.saveName)
                            .text("] ").bold()
                            .text(save.saveName).bold()
                            .spacing(1);
                } else {
                    jsonBuilder.text("\\u2560")
                            .text("[").bold()
                            .text(type.name().toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + save.saveName).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + save.saveName)
                            .text("] ").bold()
                            .text(save.saveName).bold().color(rolledBackColor)
                            .spacing(1);
                }
            }

            // Last save
            if (!(lastSave == null)) {
                if (lastSave.rolledBack == 0) {
                    jsonBuilder.text("\\u255a")
                            .text("[").bold()
                            .text(type.name().toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + lastSave.saveName).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + lastSave.saveName)
                            .text("] ").bold()
                            .text(lastSave.saveName).bold();
                } else {
                    jsonBuilder.text("\\u255a")
                            .text("[").bold()
                            .text(type.name().toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + lastSave.saveName).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + lastSave.saveName)
                            .text("] ").bold()
                            .text(lastSave.saveName).bold().color(rolledBackColor);
                }
            }
        } else {
            jsonBuilder.spacing(1)
                    .text("There are no saves").bold();
        }

        jsonBuilder.spacing(7-(saves.size()));

        // Adding Reload button
        jsonBuilder.text("[").bold()
                .text("Reload").bold().color(JSONCOLOR.GREEN).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()).hover(HOVERACTION.show_text, "Reloads list")
                .text("]").bold();

        jsonBuilder.spacing(2);

        String leftArrow = page == 1 ? new JsonBuilder().text("⏪ ◁ ").bold().color(JSONCOLOR.GRAY).build() : new JsonBuilder().text("⏪ ").bold().hover(HOVERACTION.show_text, "First page").click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()+ " : " + 1).text("◀ ").bold().hover(HOVERACTION.show_text, "Previous page").click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()+ " : " + (page - 1)).build();
        String rightArrow = page == maxPage ? new JsonBuilder().text(" ▷ ⏩").bold().color(JSONCOLOR.GRAY).build() : new JsonBuilder().text(" ▶ ").bold().hover(HOVERACTION.show_text, "Next page").click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()+ " : " + (page + 1)).text("⏩").bold().hover(HOVERACTION.show_text, "Last page").click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase()+ " : " + maxPage).build();

        jsonBuilder.text("   ").addBuilt(leftArrow).text(page).bold().color(JSONCOLOR.YELLOW).text("/").bold().text(String.valueOf(maxPage)).bold().color(JSONCOLOR.YELLOW).addBuilt(rightArrow);


        // Adding Save Menu button
        jsonBuilder.spacing(2)
                .text("G").bold().color(JSONCOLOR.RED).text("C").bold().color(JSONCOLOR.GOLD).text(":\\\\").bold()
                .text("Main Menu").bold().color(JSONCOLOR.YELLOW).click(CLICKACTION.run_command, "/gcmenu").hover(HOVERACTION.show_text, "Open main menu").bold()
                .text("\\\\").bold().text("Save Menu").bold().color(JSONCOLOR.YELLOW).click(CLICKACTION.run_command, "/gcsavemenu").hover(HOVERACTION.show_text, "Open save menu").bold()
                .text("\\\\").bold().text(type.name()).bold().color(JSONCOLOR.GOLD).hover(HOVERACTION.show_text, "You are here").bold()
                .text(">").bold();
        return jsonBuilder.build();
    }

    public static String saveListAll(LISTTYPE type, String playerName, String worldName, int page) {
        List<SaveEntity> saves;
        UserDao userDao;
        SaveDao saveDao;
        WorldDao worldDao;

        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            userDao = databaseManager.getUserDao();
            saveDao = databaseManager.getSaveDao();
            worldDao = databaseManager.getWorldDao();

            UserEntity user = userDao.getUserByName(playerName);
            WorldEntity world = worldDao.getWorldByWorldName(worldName);
            saves = saveDao.getAllSavesByWorld(world.rowId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return saveListSubset(type, saves, page);
    }
}
