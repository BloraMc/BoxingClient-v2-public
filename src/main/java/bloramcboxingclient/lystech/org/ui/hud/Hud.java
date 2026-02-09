package bloramcboxingclient.lystech.org.ui.hud;

import bloramcboxingclient.lystech.org.modules.Module;
import bloramcboxingclient.lystech.org.modules.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;

public class Hud extends Gui {
    private Minecraft mc = Minecraft.getMinecraft();
    private ModuleManager moduleManager;

    public Hud(ModuleManager moduleManager) {
        this.moduleManager = moduleManager;
    }

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        mc.fontRendererObj.drawString("BoxingClient v2", 2, 2, Color.BLACK.getRGB());
        int y = 12;
        for (Module module : moduleManager.getModules()) {
            if (module.isEnabled()) {
                mc.fontRendererObj.drawString(module.getName(), 2, y, Color.BLACK.getRGB());
                y += 10;
            }
        }
    }
}