package top.gitcraft.ui.components;

public class Menu {
    public String mainMenu(){
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
        return "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"\\u2550\\u2550\",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\" Git\",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\"Craft\",\"bold\":true,\"color\":\"gold\"},"
                + "{\"text\":\" \\u2550\\u2550\\n\\nMain Menu\",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"\\n\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\",\"bold\":true},"
                + "{\"text\":\"\\n\\n\\n\\u2554\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworldmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Opens the world menu\"}},"
                + "{\"text\":\"World Menu\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworldmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Opens the world menu\"}},"
                + "{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworldmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Opens the world menu\"}},"
                + "{\"text\":\"\\n\\u2551\\n\\u255a\"},"
                + "{\"text\":\"[\",\"bold\":true},{\"text\":\"Config\",\"bold\":true,\"color\":\"yellow\"},{\"text\":\"]\",\"bold\":true},"
                + "{\"text\":\"\\n\\n\\n\\n\\n\"}]";
    }
    public String configMenu(){
        return "";
    }

    public String worldMenu(){
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
        return "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"\\u2550\\u2550\",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\" Git\",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\"Craft\",\"bold\":true,\"color\":\"gold\"},"
                + "{\"text\":\" \\u2550\\u2550\\n\\nWorld Menu\",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"\\n\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\",\"bold\":true},"
                + "{\"text\":\"\\n\\n\\u2554\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworld join\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Join a world\"}},{\"text\":\"JOIN\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworld join\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Join a world\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworld join\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Join a world\"}},{\"text\":\"\\n\\u2560\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworldcreate\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Create a new world\"}},{\"text\":\"CREATE\",\"bold\":true,\"color\":\"aqua\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworldcreate\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Create a new world\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworldcreate\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Create a new world\"}},{\"text\":\"\\n\\u2560\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmergemenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open merge menu\"}},{\"text\":\"MERGE\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmergemenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open merge menu\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmergemenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open merge menu\"}},{\"text\":\"\\n\\u2551\\n\\u255a\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworld delete\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Delete a world\"}},{\"text\":\"DELETE\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworld delete\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Delete a world\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcworld delete\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Delete a world\"}},{\"text\":\"\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open main menu\"}},{\"text\":\"Main Menu\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open main menu\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open main menu\"}},{\"text\":\"\\n \"}]";
    }
}
