package top.gitcraft.ui.components;

import net.coreprotect.CoreProtectAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import top.gitcraft.GitCraft;
import top.gitcraft.database.DatabaseManager;
import top.gitcraft.database.daos.SaveDao;
import top.gitcraft.database.daos.UserDao;
import top.gitcraft.database.entities.SaveEntity;
import top.gitcraft.database.entities.UserEntity;
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
    private static UserDao userDao;
    private static SaveDao saveDao;
    private final Logger logger;

    public SaveList() {
        logger = GitCraft.getPlugin(GitCraft.class).getLogger();
        try {
            DatabaseManager databaseManager = DatabaseManager.getInstance();
            userDao = databaseManager.getUserDao();
            saveDao = databaseManager.getSaveDao();
        } catch (SQLException e) {
            logger.severe("Failed to get database manager");
            throw new RuntimeException(e);
        }
    }

    public static String saveListSubset(LISTTYPE type, List<String> saveNames) {
        // Initialize JsonBuilder
        JsonBuilder jsonBuilder = new JsonBuilder();

        // Building the JSON message
        jsonBuilder.addBuilt(Menu.header())
                .text("Save " + type.name().substring(0, 1).toUpperCase() + type.name().substring(1).toLowerCase() + " List\\n").bold()
                .repeat("═", type.getUnderlineLength()).bold()
                .spacing(2);

        // First world
        jsonBuilder.text("\\u2554")
                .text("[").bold()
                .text(type.name().toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + saveNames.get(0)).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + saveNames.get(0))
                .text("] ").bold()
                .text(saveNames.get(0)).bold()
                .spacing(1);

        saveNames.remove(0);
        String lastSave = null;
        if (!saveNames.isEmpty()) {
            lastSave = saveNames.get(saveNames.size()-1);
            saveNames.remove(saveNames.size()-1);
        }
        // Iterate through world names
        for (String saveName : saveNames) {
            jsonBuilder.text("\\u2560")
                    .text("[").bold()
                    .text(type.name().toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + saveName).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + saveName)
                    .text("] ").bold()
                    .text(saveName).bold()
                    .spacing(1);
        }

        // Last world
        if(!(lastSave == null)) {
            jsonBuilder.text("\\u255a")
                    .text("[").bold()
                    .text(type.name().toUpperCase()).bold().color(type.getColor()).click(CLICKACTION.run_command, "/gc" + type.name().toLowerCase() + " " + lastSave).hover(HOVERACTION.show_text, "Click to " + type.name().toLowerCase() + " " + lastSave)
                    .text("] ").bold()
                    .text(lastSave).bold()
                    .spacing(1);
        }

        // Adding World Menu button
        jsonBuilder.spacing(3)
                .text("[").bold()
                .text("Save Menu").bold().color(JSONCOLOR.YELLOW).click(CLICKACTION.run_command, "/gcsavemenu").hover(HOVERACTION.show_text, "Open save menu")
                .text("]").bold()
                .spacing(1);

        return jsonBuilder.build();
    }

    public static String saveListAll(LISTTYPE type, String playerName) {
        List<String> saveNames = new ArrayList<>();
        List<SaveEntity> saves;
        try{
        UserEntity user = userDao.getUserByName(playerName);
        saves = saveDao.getAllSavesByUser(user.rowId);}
        catch(SQLException e) {
            throw new RuntimeException(e);
        }

        for (SaveEntity save : saves) {
            saveNames.add(save.saveName);
        }
        return saveListSubset(type, saveNames);
    }
}
