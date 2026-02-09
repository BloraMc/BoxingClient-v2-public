package bloramcboxingclient.lystech.org.ui.clickgui;

import bloramcboxingclient.lystech.org.modules.Category;
import bloramcboxingclient.lystech.org.modules.ModuleManager;
import bloramcboxingclient.lystech.org.ui.clickgui.frame.Frame;
import bloramcboxingclient.lystech.org.ui.clickgui.frame.FrameManager;
import net.minecraft.client.gui.GuiScreen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGuiScreen extends GuiScreen {
    private List<Frame> frames = new ArrayList<Frame>();
    private FrameManager frameManager;

    public ClickGuiScreen(ModuleManager moduleManager) {
        int x = 20;
        for (Category category : Category.values()) {
            frames.add(new Frame(category, x, 20, 100, 12, moduleManager));
            x += 120;
        }
        frameManager = new FrameManager(frames);
        frameManager.load();
    }

    @Override
    public void onGuiClosed() {
        frameManager.save();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        for (Frame frame : frames) {
            frame.drawScreen(mouseX, mouseY, partialTicks);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        for (Frame frame : frames) {
            frame.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        for (Frame frame : frames) {
            frame.mouseReleased(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
}