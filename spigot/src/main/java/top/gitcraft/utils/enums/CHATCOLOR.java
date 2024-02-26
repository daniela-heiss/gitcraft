package top.gitcraft.utils.enums;

public enum CHATCOLOR {
    BLACK("black", "\u00A70", "\u00A70", "#000000"),
    DARK_BLUE("dark_blue", "\u00A71", "\u00A71", "#0000AA"),
    DARK_GREEN("dark_green", "\u00A72", "\u00A72", "#00AA00"),
    DARK_AQUA("dark_aqua", "\u00A73", "\u00A73", "#00AAAA"),
    DARK_RED("dark_red", "\u00A74", "\u00A74", "#AA0000"),
    DARK_PURPLE("dark_purple", "\u00A75", "\u00A75", "#AA00AA"),
    GOLD("gold", "\u00A76", "\u00A76", "#FFAA00"),
    GRAY("gray", "\u00A77", "\u00A77", "#AAAAAA"),
    DARK_GRAY("dark_gray", "\u00A78", "\u00A78", "#555555"),
    BLUE("blue", "\u00A79", "\u00A79", "#5555FF"),
    GREEN("green", "\u00A7a", "\u00A7a", "#55FF55"),
    AQUA("aqua", "\u00A7b", "\u00A7b", "#55FFFF"),
    RED("red", "\u00A7c", "\u00A7c", "#FF5555"),
    LIGHT_PURPLE("light_purple", "\u00A7d", "\u00A7d", "#FF55FF"),
    YELLOW("yellow", "\u00A7e", "\u00A7e", "#FFFF55"),
    WHITE("white", "\u00A7f", "\u00A7f", "#FFFFFF");

    private final String name;
    private final String chatCode;
    private final String motdCode;
    private final String hexCode;

    CHATCOLOR(String name, String chatCode, String motdCode, String hexCode) {
        this.name = name;
        this.chatCode = chatCode;
        this.motdCode = motdCode;
        this.hexCode = hexCode;
    }

    public String getName() {
        return name;
    }

    public String getChatCode() {
        return chatCode;
    }

    public String getMotdCode() {
        return motdCode;
    }

    public String getHexCode() {
        return hexCode;
    }
}

