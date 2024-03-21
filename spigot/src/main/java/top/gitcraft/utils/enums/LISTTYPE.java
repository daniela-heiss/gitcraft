package top.gitcraft.utils.enums;

import org.bukkit.ChatColor;

public enum LISTTYPE {
    JOIN(9, JSONCOLOR.GREEN),
    CREATE(11, JSONCOLOR.AQUA),
    DELETE(10, JSONCOLOR.RED),
    LOAD(6, JSONCOLOR.AQUA),
    DELETESAVE(10, JSONCOLOR.RED),
    LOADSCHEM(10, JSONCOLOR.LIGHT_PURPLE),
    SELECT(9, JSONCOLOR.GOLD);


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
