package top.gitcraft.ui.components;

import org.bukkit.Bukkit;
import org.bukkit.World;
import top.gitcraft.utils.enums.LISTTYPE;

import java.util.*;

public class WorldList {

    /**
     * Used to generate a list of worlds to join, create, or delete.
     *
     * @param type       The type of list to generate. Valid values are "join", "create", or "delete".
     * @param worldNames List of world names.
     * @return A JSON message representing the action to be performed on the worlds.
     * @apiNote Refrain from providing worldNames manually.<br>
     *         Use worldListAll(type) instead.
     *         It will provide a list of all worlds. <br>
     *         If you only want to provide a subset, use this method.
     */
    public static String worldListSubset(LISTTYPE type, List<String> worldNames) {

        /* ==================================================
         * =========== WORLD LIST PAGE STRUCTURE ===========
         * ==================================================
         *
         * I.   PAGE TITLE
         *
         * II.  FIRST WORLD
         * III. WORLDS (if exists)
         * IV.  LAST WORLD (if exists)
         *
         * V.   WORLD MENU
         *
         * ================================================== */

        String lowerCaseType = type.toString().toLowerCase();
        String upperCaseType = type.toString().toUpperCase();
        String capitalizedType = type.toString().substring(0, 1).toUpperCase() + type.toString().substring(1);

        int underlineLength = type.getUnderlineLength();
        String typeColor = type.getColor().getName();

        // Different page titles need different lengths of underlining
        StringBuilder underline = new StringBuilder();
        for (int i = 0; i < underlineLength; i++) {
            underline.append("\\u2550");
        }

        StringBuilder jsonMessage = new StringBuilder();

        /* ==================================================
         * ================= I. PAGE TITLE ==================
         * ==================================================
         *
         * ══ GitCraft ══
         *
         * World <capitalizedType> List
         * <underline>
         *
         */

        jsonMessage.append("[\"\",")
                .append("{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},")
                .append("{\"text\":\"\\u2550\\u2550\",\"bold\":true,\"color\":\"white\"},")
                .append("{\"text\":\" Git\",\"bold\":true,\"color\":\"red\"},")
                .append("{\"text\":\"Craft\",\"bold\":true,\"color\":\"gold\"},")
                .append("{\"text\":\" \\u2550\\u2550\\n\\nWorld ").append(capitalizedType).append(" List\",\"bold\":true,\"color\":\"white\"},")
                .append("{\"text\":\"\\n").append(underline).append("\\n\",\"bold\":true}");

        /* ==================================================
         * ================= II. FIRST WORLD ================
         * ==================================================
         *
         * Special treatment for first world. It receives \\u2554 - ╔ as item marker
         *
         * ╔ [<upperCaseType>] <firstWorldName>
         *
         */

        String firstWorldName = worldNames.get(0);
        jsonMessage.append(",{\"text\":\"\\n\",\"bold\":true},")
                .append("{\"text\":\"\\u2554\",\"color\":\"white\"},")
                .append("{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gclist").append(lowerCaseType).append(" ").append(firstWorldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(firstWorldName).append("\"}},")
                .append("{\"text\":\"").append(upperCaseType).append("\",\"bold\":true,\"color\":\"").append(typeColor).append("\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gclist").append(lowerCaseType).append(" ").append(firstWorldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(firstWorldName).append("\"}},")
                .append("{\"text\":\"] ").append(firstWorldName).append("\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gclist").append(lowerCaseType).append(" ").append(firstWorldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(firstWorldName).append("\"}}");

        /* ================================================== */

        // Remove first world from List
        worldNames.remove(0);

        // Save last world for later and remove from list (if exists)
        String lastWorldName = "";
        if (!worldNames.isEmpty()) {
            lastWorldName = worldNames.get(worldNames.size() - 1);
            worldNames.remove(worldNames.size() - 1);
        }

        /* ==================================================
         * =================== III. WORLDS ==================
         * ==================================================
         *
         * Rest of the worlds except last receive \\u2560 - ╠ as item marker
         *
         * ╠ [<upperCaseType>] <worldName>
         *
         */

        for (String worldName : worldNames) {
            jsonMessage.append(",{\"text\":\"\\n\",\"bold\":true},")
                    .append("{\"text\":\"\\u2560\",\"color\":\"white\"},")
                    .append("{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gclist").append(lowerCaseType).append(" ").append(worldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(worldName).append("\"}},")
                    .append("{\"text\":\"").append(upperCaseType).append("\",\"bold\":true,\"color\":\"").append(typeColor).append("\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gclist").append(lowerCaseType).append(" ").append(worldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(worldName).append("\"}},")
                    .append("{\"text\":\"] ").append(worldName).append("\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gclist").append(lowerCaseType).append(" ").append(worldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(worldName).append("\"}}");
        }

        /* ==================================================
         * ================= IV. LAST WORLD =================
         * ==================================================
         *
         * Special treatment for last world. It receives \\u255a - ╚ as item marker
         *
         * ╚ [<upperCaseType>] <lastWorldName>
         *
         */

        jsonMessage.append(",{\"text\":\"\\n\",\"bold\":true},")
                .append("{\"text\":\"\\u255a\",\"color\":\"white\"},")
                .append("{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gclist").append(lowerCaseType).append(" ").append(lastWorldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(lastWorldName).append("\"}},")
                .append("{\"text\":\"").append(upperCaseType).append("\",\"bold\":true,\"color\":\"").append(typeColor).append("\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gclist").append(lowerCaseType).append(" ").append(lastWorldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(lastWorldName).append("\"}},")
                .append("{\"text\":\"] ").append(lastWorldName).append("\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gclist").append(lowerCaseType).append(" ").append(lastWorldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(lastWorldName).append("\"}}");
        /* ==================================================
         * ================= V. WORLD MENU =================
         * ==================================================
         *
         * World menu button
         *
         * [World Menu]
         *
         */

        jsonMessage.append(",{\"text\":\"\\n\\n\\n\",\"bold\":true},")
                .append("{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworldmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open world menu\"}},")
                .append("{\"text\":\"World Menu\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworldmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open world menu\"}},")
                .append("{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworldmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open world menu\"}}")
                .append(",{\"text\":\"\\n \"}]");

        return jsonMessage.toString();
    }

    /**
     * Used to generate a list of worlds to join, create, or delete.
     *
     * @param type       The type of list to generate. Valid values are "join", "create", or "delete".
     * @return A JSON message representing the action to be performed on the worlds.
     * @apiNote If you only want to provide a subset, use <br>
     *          worldListSubset(type, worldNames).
     */
    public static String worldListAll(LISTTYPE type) {
        List<String> worldNames = new ArrayList<>();
        for(World world : Bukkit.getWorlds()){
            worldNames.add(world.getName());
        }
        return worldListSubset(type, worldNames);
    }
}
