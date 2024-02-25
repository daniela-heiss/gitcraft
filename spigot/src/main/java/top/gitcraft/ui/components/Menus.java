package top.gitcraft.ui.components;

public class Menus {
    public String mainMenu(){
        /*
         * ══ GitCraft ══
         *
         *  Main Menu
         *  ══════
         *
         * ╔[Branch Menu]
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
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Opens the branch menu\"}},"
                + "{\"text\":\"Branch-Menu\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Opens the branch menu\"}},"
                + "{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Opens the branch menu\"}},"
                + "{\"text\":\"\\n\\u2551\\n\\u255a\"},"
                + "{\"text\":\"[\",\"bold\":true},{\"text\":\"Config\",\"bold\":true,\"color\":\"yellow\"},{\"text\":\"]\",\"bold\":true},"
                + "{\"text\":\"\\n\\n\\n\\n\\n\"}]";
    }
    public String configMenu(){
        return "";
    }

    public String branchMenu(){
        /*
         * ══ GitCraft ══
         *
         *  Branch Menu
         *  ════════
         *
         * ╔[JOIN]
         * ╠[CREATE]
         * ╠[MERGE]
         * ║
         * ╚[DELETE]
         *
         *
         * [Branch Menu]
         *
         */
        return "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"\\u2550\\u2550\",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\" Git\",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\"Craft\",\"bold\":true,\"color\":\"gold\"},"
                + "{\"text\":\" \\u2550\\u2550\\n\\nBranch Menu\",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"\\n\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\\u2550\",\"bold\":true},"
                + "{\"text\":\"\\n\\n\\u2554\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoinlist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Join a branch\"}},{\"text\":\"JOIN\",\"bold\":true,\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoinlist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Join a branch\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchjoinlist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Join a branch\"}},{\"text\":\"\\n\\u2560\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchcreatelist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Create a new branch\"}},{\"text\":\"CREATE\",\"bold\":true,\"color\":\"aqua\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchcreatelist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Create a new branch\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchcreatelist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Create a new branch\"}},{\"text\":\"\\n\\u2560\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmergemenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open merge menu\"}},{\"text\":\"MERGE\",\"bold\":true,\"color\":\"gold\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmergemenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open merge menu\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmergemenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open merge menu\"}},{\"text\":\"\\n\\u2551\\n\\u255a\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdeletelist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Delete a branch\"}},{\"text\":\"DELETE\",\"bold\":true,\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdeletelist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Delete a branch\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcbranchdeletelist\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Delete a branch\"}},{\"text\":\"\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open main menu\"}},{\"text\":\"Main Menu\",\"bold\":true,\"color\":\"yellow\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open main menu\"}},{\"text\":\"]\",\"bold\":true,\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/gcmenu\"},\"hoverEvent\":{\"action\":\"show_text\",\"contents\":\"Open main menu\"}},{\"text\":\"\\n \"}]";
    }
}
