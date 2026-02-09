package bloramcboxingclient.lystech.org.ui.clickgui.frame;

import bloramcboxingclient.lystech.org.settings.ColorSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class ColorButton extends Gui {
    private ColorSetting setting;
    public int x, y, width, height;
    private Minecraft mc = Minecraft.getMinecraft();

    public ColorButton(ColorSetting setting, int x, int y, int width, int height) {
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        boolean isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        drawRect(x, y, x + width, y + height, isHovered ? new Color(30, 30, 30, 180).getRGB() : new Color(0, 0, 0, 180).getRGB());
        mc.fontRendererObj.drawString(setting.getName(), x + 2, y + 2, Color.LIGHT_GRAY.getRGB());
        drawRect(x + width - 12, y + 2, x + width - 2, y + height - 2, setting.getColor().getRGB());
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            if (mouseButton == 0) {
                setting.setColor(new Color((int) (Math.random() * 0x1000000)));
            }
        }
    }

    public int getHeight() {
        return height;
    }
}