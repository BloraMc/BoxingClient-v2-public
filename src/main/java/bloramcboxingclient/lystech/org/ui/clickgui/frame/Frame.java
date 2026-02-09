package bloramcboxingclient.lystech.org.ui.clickgui.frame;

import bloramcboxingclient.lystech.org.modules.Category;
import bloramcboxingclient.lystech.org.modules.Module;
import bloramcboxingclient.lystech.org.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frame extends Gui {
    private int x, y, width, height, dragX, dragY;
    private boolean dragging;
    private Category category;
    private List<ModuleButton> buttons = new ArrayList<ModuleButton>();
    private Minecraft mc = Minecraft.getMinecraft();

    public Frame(Category category, int x, int y, int width, int height, ModuleManager moduleManager) {
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        int yOffset = y + height;
        for (Module module : moduleManager.getModulesByCategory(category)) {
            buttons.add(new ModuleButton(module, x, yOffset, width, 12));
            yOffset += 12;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }
        drawRect(x, y, x + width, y + height, new Color(0, 20, 80).getRGB());
        mc.fontRendererObj.drawString(category.name(), x + 2, y + 2, Color.LIGHT_GRAY.getRGB());

        int yOffset = y + height;
        for (ModuleButton button : buttons) {
            button.x = x;
            button.y = yOffset;
            button.drawScreen(mouseX, mouseY, partialTicks);
            yOffset += button.getHeight();
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            if (mouseButton == 0) {
                dragging = true;
                dragX = mouseX - x;
                dragY = mouseY - y;
            }
        }

        for (ModuleButton button : buttons) {
            button.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int state) {
        if (state == 0) {
            dragging = false;
        }
        for (ModuleButton button : buttons) {
            button.mouseReleased(mouseX, mouseY, state);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}