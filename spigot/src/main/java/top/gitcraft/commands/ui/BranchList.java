package top.gitcraft.commands.ui;

import java.util.Arrays;
import java.util.List;

public class BranchList {

    String dynamicBranchList(String type, List<String> worldNames) {

        /* ==================================================
         * =========== BRANCH LIST PAGE STRUCTURE ===========
         * ==================================================
         *
         * I.   PAGE TITLE
         *
         * II.  FIRST WORLD
         * III. WORLDS (if exists)
         * IV.  LAST WORLD (if exists)
         *
         * V.   BRANCH MENU
         *
         * ================================================== */

        List<String> types = Arrays.asList("join", "create", "delete");

        if (types.contains(type.toLowerCase())) {
            // {join, create, delete}
            int[] underlineLengths = {10, 12, 11};
            String[] typeColors = {"green", "aqua", "red"};

            String lowerCaseType = type.toLowerCase();
            String upperCaseType = type.toUpperCase();
            String capitalizedType = type.substring(0, 1).toUpperCase() + type.substring(1);

            int underlineLength = underlineLengths[types.indexOf(lowerCaseType)];
            String typeColor = typeColors[types.indexOf(lowerCaseType)];

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
             * Branch <capitalizedType> List
             * <underline>
             *
             */

            jsonMessage.append("[\"\",")
                    .append("{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},")
                    .append("{\"text\":\"\\u2550\\u2550\",\"bold\":true,\"color\":\"white\"},")
                    .append("{\"text\":\" Git\",\"bold\":true,\"color\":\"red\"},")
                    .append("{\"text\":\"Craft\",\"bold\":true,\"color\":\"gold\"},")
                    .append("{\"text\":\" \\u2550\\u2550\\n\\nBranch ").append(capitalizedType).append(" List\",\"bold\":true,\"color\":\"white\"},")
                    .append("{\"text\":\"\\n").append(underline).append("n\",\"bold\":true}");

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
                    .append("{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(lowerCaseType).append(" ").append(firstWorldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(firstWorldName).append("\"}},")
                    .append("{\"text\":\"").append(upperCaseType).append("\",\"bold\":true,\"color\":\"").append(typeColor).append("\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(lowerCaseType).append(" ").append(firstWorldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(firstWorldName).append("\"}},")
                    .append("{\"text\":\"] ").append(firstWorldName).append("\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(lowerCaseType).append(" ").append(firstWorldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(firstWorldName).append("\"}}");

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
                        .append("{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(lowerCaseType).append(" ").append(worldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(worldName).append("\"}},")
                        .append("{\"text\":\"\"").append(upperCaseType).append("\"\",\"bold\":true,\"color\":\"\"").append(typeColor).append("\"\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(lowerCaseType).append(" ").append(worldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(worldName).append("\"}},")
                        .append("{\"text\":\"] ").append(worldName).append("\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(lowerCaseType).append(" ").append(worldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(worldName).append("\"}}");
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
                    .append("{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(lowerCaseType).append(" ").append(lastWorldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(lastWorldName).append("\"}},")
                    .append("{\"text\":\"\"").append(upperCaseType).append("\"\",\"bold\":true,\"color\":\"\"").append(typeColor).append("\"\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(lowerCaseType).append(" ").append(lastWorldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(lastWorldName).append("\"}},")
                    .append("{\"text\":\"] ").append(lastWorldName).append("\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranch").append(lowerCaseType).append(" ").append(lastWorldName).append("\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Click to ").append(lowerCaseType).append(" ").append(lastWorldName).append("\"}}");

            /* ==================================================
             * ================= V. BRANCH MENU =================
             * ==================================================
             *
             * Branch menu button
             *
             * [Branch Menu]
             *
             */

            jsonMessage.append(",{\"text\":\"\\n\\n\\n\",\"bold\":true},")
                    .append("{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}},")
                    .append("{\"text\":\"Branch Menu\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}},")
                    .append("{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open branch menu\"}}")
                    .append(",{\"text\":\"\\n \"}]");

            return jsonMessage.toString();
        }

        // TODO: Error message no such branch list type
        return "ERROR: NO SUCH BRANCH LIST TYPE. VALID TYPES: join, create, delete";
    }
}
