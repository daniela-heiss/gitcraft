package top.gitcraft.utils.enums;

public enum LISTTYPE {
    JOIN(9, CHATCOLOR.GREEN),
    CREATE(11, CHATCOLOR.AQUA),
    DELETE(10, CHATCOLOR.RED);

    private final int underlineLength;
    private final CHATCOLOR color;

    private LISTTYPE(int underlineLength, CHATCOLOR color){
        this.underlineLength = underlineLength;
        this.color = color;
    }

    public int getUnderlineLength() {
        return underlineLength;
    }

    public CHATCOLOR getColor(){
        return color;
    }
}
