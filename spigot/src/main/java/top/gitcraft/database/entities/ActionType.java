package top.gitcraft.database.entities;

//action enum
public enum ActionType {
    BREAK(0),
    PLACE(1),
    MODIFY(2);

    private final int value;

    ActionType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
