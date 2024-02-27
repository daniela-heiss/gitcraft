package top.gitcraft.utils.enums;

import org.bukkit.ChatColor;

public enum LISTTYPE {
    JOIN(9, ChatColor.GREEN),
    CREATE(11, ChatColor.AQUA),
    DELETE(10, ChatColor.RED);

    private final int underlineLength;
    private final ChatColor color;

    private LISTTYPE(int underlineLength, ChatColor color){
        this.underlineLength = underlineLength;
        this.color = color;
    }

    public int getUnderlineLength() {
        return underlineLength;
    }

    public ChatColor getColor(){
        return color;
    }
}
