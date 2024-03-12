package top.gitcraft.utils;

import top.gitcraft.ui.components.InfoMessages;
import top.gitcraft.ui.components.Menu;
import top.gitcraft.utils.enums.CLICKACTION;
import top.gitcraft.utils.enums.HOVERACTION;
import top.gitcraft.utils.enums.JSONCOLOR;

public class JsonBuilder {
    private StringBuilder jsonMessage = new StringBuilder();
    //private StringBuilder firstTextComponent = null;
    private StringBuilder lastTextComponent = new StringBuilder();

    public JsonBuilder text(String text) {
        jsonMessage.append("},{\"text\":\"").append(text).append("\"");
        lastTextComponent = new StringBuilder("},{\"text\":\"").append(text).append("\"");
        return this;
    }
    public JsonBuilder text(Integer text) {
        jsonMessage.append("},{\"text\":\"").append(text).append("\"");
        lastTextComponent = new StringBuilder("},{\"text\":\"").append(text).append("\"");
        return this;
    }
//    public JsonBuilder text(JsonBuilder unBuilt) {
//        jsonMessage.append(unBuilt.buildReusable());
//        lastTextComponent = new StringBuilder(unBuilt.buildReusable());
//        return this;
//    }

    public JsonBuilder color(JSONCOLOR color) {
        jsonMessage.append(",\"color\":\"").append(color.getName()).append("\"");
        lastTextComponent.append(",\"color\":\"").append(color.getName()).append("\"");
        return this;
    }

    public JsonBuilder color(String hex) {
        jsonMessage.append(",\"color\":\"").append(hex).append("\"");
        lastTextComponent.append(",\"color\":\"").append(hex).append("\"");
        return this;
    }

    public JsonBuilder bold() {
        jsonMessage.append(",\"bold\":").append("true");
        lastTextComponent.append(",\"bold\":").append("true");
        return this;
    }

    public JsonBuilder italic() {
        jsonMessage.append(",\"italic\":").append("true");
        lastTextComponent.append(",\"italic\":").append("true");
        return this;
    }

    public JsonBuilder underlined() {
        jsonMessage.append(",\"underlined\":").append("true");
        lastTextComponent.append(",\"underlined\":").append("true");
        return this;
    }

    public JsonBuilder strikethrough() {
        jsonMessage.append(",\"strikethrough\":").append("true");
        lastTextComponent.append(",\"strikethrough\":").append("true");
        return this;
    }

    public JsonBuilder obfuscated() {
        jsonMessage.append(",\"obfuscated\":").append("true");
        lastTextComponent.append(",\"obfuscated\":").append("true");

        return this;
    }

    public JsonBuilder hover(HOVERACTION action, String value) {
        jsonMessage.append(",\"hoverEvent\":{\"action\":\"").append(action.name()).append("\",\"contents\":{\"text\":\"").append(value).append("\"}}");
        lastTextComponent.append(",\"hoverEvent\":{\"action\":\"").append(action.name()).append("\",\"contents\":{\"text\":\"").append(value).append("\"}}");
        return this;
    }

    public JsonBuilder hover(HOVERACTION action, String value, String id, int count, String tag) {
        if (action == HOVERACTION.show_item) {
            jsonMessage.append(",\"hoverEvent\":{\"action\":\"").append(action.name()).append("\",\"contents\":{\"id\":\"").append(id).append("\",\"count\":").append(count).append(",\"tag\":\"").append(tag).append("\"}}");
            lastTextComponent.append(",\"hoverEvent\":{\"action\":\"").append(action.name()).append("\",\"contents\":{\"id\":\"").append(id).append("\",\"count\":").append(count).append(",\"tag\":\"").append(tag).append("\"}}");
        } else {
            throw new IllegalArgumentException("Invalid hover action for item tooltip");
        }
        return this;
    }

    public JsonBuilder hover(HOVERACTION action, String value, String name, String type, String entityId) {
        if (action == HOVERACTION.show_entity) {
            jsonMessage.append(",\"hoverEvent\":{\"action\":\"").append(action.name()).append("\",\"contents\":{\"name\":\"").append(name).append("\",\"type\":\"").append(type).append("\",\"id\":\"").append(entityId).append("\"}}");
            lastTextComponent.append(",\"hoverEvent\":{\"action\":\"").append(action.name()).append("\",\"contents\":{\"name\":\"").append(name).append("\",\"type\":\"").append(type).append("\",\"id\":\"").append(entityId).append("\"}}");
        } else {
            throw new IllegalArgumentException("Invalid hover action for entity tooltip");
        }
        return this;
    }

    public JsonBuilder click(CLICKACTION action, String value) {
        jsonMessage.append(",\"clickEvent\":{\"action\":\"").append(action.name()).append("\",\"value\":\"").append(value).append("\"}");
        lastTextComponent.append(",\"clickEvent\":{\"action\":\"").append(action.name()).append("\",\"value\":\"").append(value).append("\"}");
        return this;
    }

    public JsonBuilder repeat(String text, int times) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < times; i++) {
            temp.append(text);
        }
        this.text(temp.toString());
        return this;
    }

    public JsonBuilder repeat(int times) {
        StringBuilder temp = new StringBuilder();
        if (lastTextComponent != null) {
            for (int i = 0; i < times; i++) {
                temp.append(lastTextComponent);
            }
            jsonMessage.append(temp);
        }
        return this;
    }

    public JsonBuilder clear() {
        repeat("\\n", 20);
        return this;
    }

    public JsonBuilder spacing(int times) {
        repeat("\\n", times);
        return this;
    }

    public JsonBuilder addBuilt(String builtJson) {
        jsonMessage.append("},").append(builtJson, 4, builtJson.length() - 2);
        lastTextComponent = new StringBuilder("},").append(builtJson, 4, builtJson.length() - 2);
        return this;
    }

    public JsonBuilder reusable(String reusable) {
        jsonMessage.append(reusable);
        lastTextComponent.append(reusable);
        return this;
    }

    public String buildReusable() {
        return jsonMessage.toString();
    }

    public String build() {
        return "[\"\"," + jsonMessage.substring(2) + "}]";
    }

    // Build to console
    public static void main(String[] args) {
        String jb = new JsonBuilder()
                .clear()
                .addBuilt(Menu.menuMainMenu())
                .build();

        System.out.println(jb);
    }
}
