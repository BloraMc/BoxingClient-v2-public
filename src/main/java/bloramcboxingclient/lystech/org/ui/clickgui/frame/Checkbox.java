package bloramcboxingclient.lystech.org.ui.clickgui.frame;

import bloramcboxingclient.lystech.org.settings.BooleanSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.Color;

public class Checkbox extends Gui {
    private BooleanSetting setting;
    public int x, y, width, height;
    private Minecraft mc = Minecraft.getMinecraft();

    public Checkbox(BooleanSetting setting, int x, int y, int width, int height) {
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        boolean isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        Color enabledColor = isHovered ? new Color(20, 50, 120) : new Color(10, 40, 100);
        Color disabledColor = isHovered ? new Color(30, 30, 30, 180) : new Color(0, 0, 0, 180);

        drawRect(x, y, x + width, y + height, setting.isEnabled() ? enabledColor.getRGB() : disabledColor.getRGB());
        mc.fontRendererObj.drawString(setting.getName(), x + 2, y + 2, Color.LIGHT_GRAY.getRGB());
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            if (mouseButton == 0) {
                setting.toggle();
            }
        }
    }

    public int getHeight() {
        return height;
    }
}