package bloramcboxingclient.lystech.org.ui.clickgui.frame;

import bloramcboxingclient.lystech.org.settings.NumberSetting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Slider extends Gui {
    private NumberSetting setting;
    public int x, y, width, height;
    private boolean dragging;
    private Minecraft mc = Minecraft.getMinecraft();

    public Slider(NumberSetting setting, int x, int y, int width, int height) {
        this.setting = setting;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        double value = setting.getValue();
        double min = setting.getMin();
        double max = setting.getMax();
        double percent = (value - min) / (max - min);

        boolean isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        Color fillColor = (isHovered || dragging) ? new Color(20, 50, 120) : new Color(10, 40, 100);

        drawRect(x, y, x + width, y + height, new Color(0, 0, 0, 180).getRGB());
        drawRect(x, y, (int) (x + width * percent), y + height, fillColor.getRGB());
        mc.fontRendererObj.drawString(setting.getName() + ": " + roundToPlace(setting.getValue(), 2), x + 2, y + 2, Color.LIGHT_GRAY.getRGB());

        if (dragging) {
            updateValue(mouseX);
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0 && mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            dragging = true;
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            dragging = false;
        }
    }

    private void updateValue(int mouseX) {
        double min = setting.getMin();
        double max = setting.getMax();
        double increment = setting.getIncrement();

        double percent = (double) (mouseX - x) / (double) width;
        double value = min + percent * (max - min);

        value = Math.max(min, Math.min(max, value));
        value = Math.round(value / increment) * increment;

        setting.setValue(value);
    }

    private double roundToPlace(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public int getHeight() {
        return height;
    }
}