package top.gitcraft.utils.enums;

import org.bukkit.ChatColor;

public enum LISTTYPE {
    JOIN(9, JSONCOLOR.GREEN),
    CREATE(11, JSONCOLOR.AQUA),
    DELETE(10, JSONCOLOR.RED),
    SAVE(9, JSONCOLOR.GREEN),
    LOAD(11, JSONCOLOR.AQUA),
    DELETESAVE(10, JSONCOLOR.RED);


    private final int underlineLength;
    private final JSONCOLOR color;

    private LISTTYPE(int underlineLength, JSONCOLOR color){
        this.underlineLength = underlineLength;
        this.color = color;
    }

    public int getUnderlineLength() {
        return underlineLength;
    }

    public JSONCOLOR getColor(){
        return color;
    }
}
