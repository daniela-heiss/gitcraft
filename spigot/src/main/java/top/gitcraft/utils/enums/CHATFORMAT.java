package top.gitcraft.utils.enums;

public enum CHATFORMAT {
    OBFUSCATED("Obfuscated", "§k", "\u00A7k"),
    BOLD("Bold", "§l", "\u00A7l"),
    STRIKETHROUGH("Strikethrough", "§m", "\u00A7m"),
    UNDERLINE("Underline", "§n", "\u00A7n"),
    ITALIC("Italic", "§o", "\u00A7o"),
    RESET("Reset", "§r", "\u00A7r");

    private final String name;
    private final String chatCode;
    private final String motdCode;

    CHATFORMAT(String name, String chatCode, String motdCode) {
        this.name = name;
        this.chatCode = chatCode;
        this.motdCode = motdCode;
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
}
