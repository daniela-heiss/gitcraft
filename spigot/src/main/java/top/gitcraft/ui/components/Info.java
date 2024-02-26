package top.gitcraft.ui.components;

public class Info {
    public static String infoNoWorldNameProvided(){
        /*
         * [i] Please provide a world name
         *
         */
        return "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"color\":\"blue\"},"
                + "{\"text\":\"i\",\"bold\":true},"
                + "{\"text\":\"] \",\"bold\":true,\"color\":\"blue\"},"
                + "{\"text\":\"Please provide a world name\",\"bold\":true},"
                + "{\"text\":\"\\n \"}]";
    }

    public static String infoCreatingWorld(String worldName) {
        /*
         * [i] Creating <worldName> ...
         *
         */
        return "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"color\":\"aqua\"},"
                + "{\"text\":\"i\",\"bold\":true},"
                + "{\"text\":\"] \",\"bold\":true,\"color\":\"aqua\"},"
                + "{\"text\":\"Creating \",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"" + worldName + "\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\" ... \",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"\\n \"}]";
    }
    public static String infoWorldCreated(String worldName) {
        /*
         * [i] <worldName> created
         *
         */
        return "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"color\":\"aqua\"},"
                + "{\"text\":\"i\",\"bold\":true},"
                + "{\"text\":\"] \",\"bold\":true,\"color\":\"aqua\"},"
                + "{\"text\":\"" + worldName + "\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\" created \",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"\\n \"}]";
    }

    public static String infoJoiningWorld(String worldName) {
        /*
         * [i] Joining <worldName> ...
         *
         */
        return "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\"i\",\"bold\":true},"
                + "{\"text\":\"] \",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\"Joining \",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"" + worldName + "\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\" ... \",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"\\n \"}]";
    }
    public static String infoWorldJoined(String worldName) {
        /*
         * [i] <worldName> created
         *
         */
        return "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\"i\",\"bold\":true},"
                + "{\"text\":\"] \",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\"" + worldName + "\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\" joined \",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"\\n \"}]";
    }

    public static String infoWorldIsProtected(String worldName) {
        /*
         * [!] <worldName> is protected and will not be deleted
         *
         */
        return "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\"!\",\"bold\":true},"
                + "{\"text\":\"] \",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\"" + worldName + "\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\" is protected and will not be deleted\\n\",\"bold\":true}]";
    }
    public static String infoDeletingWorld(String worldName) {
        /*
         * [i] Deleting <worldName> ...
         *
         */
        return "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\"i\",\"bold\":true},"
                + "{\"text\":\"] \",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\"Deleting \",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"" + worldName + "\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\" ... \",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"\\n \"}]";
    }
    public static String infoWorldDeleted(String worldName) {
        /*
         * [i] <worldName> deleted
         *
         */
        return "[\"\","
                + "{\"text\":\"\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\\n\"},"
                + "{\"text\":\"[\",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\"i\",\"bold\":true},"
                + "{\"text\":\"] \",\"bold\":true,\"color\":\"red\"},"
                + "{\"text\":\"" + worldName + "\",\"bold\":true,\"color\":\"green\"},"
                + "{\"text\":\" deleted \",\"bold\":true,\"color\":\"white\"},"
                + "{\"text\":\"\\n \"}]";
    }
}
