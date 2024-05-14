package games.twentyfortyeight;

import java.awt.Color;
import java.util.HashMap;

public class ColorScheme {

    public final static Color WINBG = new Color(0XFAF8EF);
    public final static Color GRIDBG = new Color(0XBBADA0);

    public final static Color BRIGHT = new Color(0X776E65);
    public final static Color LIGHT = new Color(0XF9F6F2);

    private HashMap<Integer, Color> background = new HashMap<>();

    public ColorScheme() {
        initBackrounds();
    }

    private void initBackrounds() {
        background.put(0, new Color(238, 228, 218, 90));
        background.put(2, new Color(0XEEE4DA));
        background.put(4, new Color(0XEDE0C8));
        background.put(8, new Color(0XF2B179));
        background.put(16, new Color(0XF59563));
        background.put(32, new Color(0XF67C5F));
        background.put(64, new Color(0XF65E3B));
        background.put(128, new Color(0XEDCF72));
        background.put(256, new Color(0XEDCC61));
        background.put(512, new Color(0XEDC850));
        background.put(1024, new Color(0XEDC53F));
        background.put(2048, new Color(0XEDC22E));
    }

    public Color getTileBackground(int value) {
        return background.get(value);
    }

    public Color getTileColor(int value) {
        if (value <= 8) {
            return BRIGHT;
        } else {
            return LIGHT;
        }
    }

}