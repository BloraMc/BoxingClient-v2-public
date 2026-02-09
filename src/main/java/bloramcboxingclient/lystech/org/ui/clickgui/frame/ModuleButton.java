package bloramcboxingclient.lystech.org.ui.clickgui.frame;

import bloramcboxingclient.lystech.org.modules.Module;
import bloramcboxingclient.lystech.org.settings.BooleanSetting;
import bloramcboxingclient.lystech.org.settings.ColorSetting;
import bloramcboxingclient.lystech.org.settings.NumberSetting;
import bloramcboxingclient.lystech.org.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleButton extends Gui {
    private Module module;
    public int x, y, width, height;
    private boolean open;
    private Minecraft mc = Minecraft.getMinecraft();
    private List<ColorButton> colorButtons = new ArrayList<ColorButton>();
    private List<Slider> sliders = new ArrayList<Slider>();
    private List<Checkbox> checkboxes = new ArrayList<Checkbox>();

    public ModuleButton(Module module, int x, int y, int width, int height) {
        this.module = module;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        int yOffset = y + height;
        for (Setting setting : module.getSettings()) {
            if (setting instanceof ColorSetting) {
                colorButtons.add(new ColorButton((ColorSetting) setting, x, yOffset, width, 12));
                yOffset += 12;
            }
            if (setting instanceof NumberSetting) {
                sliders.add(new Slider((NumberSetting) setting, x, yOffset, width, 12));
                yOffset += 12;
            }
            if (setting instanceof BooleanSetting) {
                checkboxes.add(new Checkbox((BooleanSetting) setting, x, yOffset, width, 12));
                yOffset += 12;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        boolean isHovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
        if (module.isEnabled()) {
            drawRect(x, y, x + width, y + height, isHovered ? new Color(20, 50, 120).getRGB() : new Color(10, 40, 100).getRGB());
        } else {
            drawRect(x, y, x + width, y + height, isHovered ? new Color(30, 30, 30, 180).getRGB() : new Color(0, 0, 0, 180).getRGB());
        }
        mc.fontRendererObj.drawString(module.getName(), x + 2, y + 2, Color.LIGHT_GRAY.getRGB());

        if (open) {
            int yOffset = y + height;
            for (ColorButton button : colorButtons) {
                button.x = x;
                button.y = yOffset;
                button.drawScreen(mouseX, mouseY, partialTicks);
                yOffset += button.getHeight();
            }
            for (Slider slider : sliders) {
                slider.x = x;
                slider.y = yOffset;
                slider.drawScreen(mouseX, mouseY, partialTicks);
                yOffset += slider.getHeight();
            }
            for (Checkbox checkbox : checkboxes) {
                checkbox.x = x;
                checkbox.y = yOffset;
                checkbox.drawScreen(mouseX, mouseY, partialTicks);
                yOffset += checkbox.getHeight();
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
            if (mouseButton == 0) {
                module.toggle();
            } else if (mouseButton == 1) {
                open = !open;
            }
        }

        if (open) {
            for (ColorButton button : colorButtons) {
                button.mouseClicked(mouseX, mouseY, mouseButton);
            }
            for (Slider slider : sliders) {
                slider.mouseClicked(mouseX, mouseY, mouseButton);
            }
            for (Checkbox checkbox : checkboxes) {
                checkbox.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        if (open) {
            for (Slider slider : sliders) {
                slider.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    public int getHeight() {
        if (open) {
            return height + colorButtons.size() * 12 + sliders.size() * 12 + checkboxes.size() * 12;
        }
        return height;
    }
}