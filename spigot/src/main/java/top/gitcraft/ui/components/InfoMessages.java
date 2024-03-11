package top.gitcraft.ui.components;

import top.gitcraft.utils.JsonBuilder;
import top.gitcraft.utils.enums.JSONCOLOR;

public class InfoMessages {
    /**
     * @return [i] &lt;action&gt; &lt;worldName&gt; ...
     */
    public static String infoActionWorld(JSONCOLOR color, String action, String worldName){
        return new JsonBuilder()
                .clear()
                .text("[").bold().color(color)
                .text("i").bold()
                .text("] ").bold().color(color)
                .text(action + " ").bold()
                .text(worldName).bold().color(JSONCOLOR.GREEN)
                .text(" ...").bold()
                .spacing(1)
                .build();
    }
    /**
     * @return [i] &lt;worldName&gt; &lt;action&gt;
     */
    public static String infoWorldAction(JSONCOLOR color, String worldName, String action){
        return new JsonBuilder()
                .clear()
                .text("[").bold().color(color)
                .text("i").bold()
                .text("] ").bold().color(color)
                .text(worldName + " ").bold().color(JSONCOLOR.GREEN)
                .text(action).bold()
                .spacing(1)
                .build();
    }
    /**
     * @return [i] &lt;worldName&gt; &lt;action&gt;
     */
    public static String infoContent(JSONCOLOR color, String content){
        return new JsonBuilder()
                .clear()
                .text("[").bold().color(color)
                .text("i").bold()
                .text("] ").bold().color(color)
                .text(content).bold()
                .spacing(1)
                .build();
    }
}
