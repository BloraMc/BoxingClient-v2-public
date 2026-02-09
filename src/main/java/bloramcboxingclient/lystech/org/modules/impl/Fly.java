package bloramcboxingclient.lystech.org.modules.impl;

import bloramcboxingclient.lystech.org.modules.Category;
import bloramcboxingclient.lystech.org.modules.Module;
import bloramcboxingclient.lystech.org.settings.NumberSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Fly extends Module {
    private NumberSetting speed = new NumberSetting("Speed", 1, 0.1, 5, 0.1);

    public Fly() {
        super("Fly", "Allows you to fly.", Category.MOVEMENT);
        this.addSetting(speed);
    }

    @Override
    public void onEnable() {
        if (mc.thePlayer != null) {
            mc.thePlayer.capabilities.allowFlying = true;
            mc.thePlayer.capabilities.isFlying = true;
        }
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer != null) {
            if (!mc.thePlayer.capabilities.isCreativeMode) {
                mc.thePlayer.capabilities.allowFlying = false;
            }
            mc.thePlayer.capabilities.isFlying = false;
            mc.thePlayer.capabilities.setFlySpeed(0.05f); // Reset to default speed
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.thePlayer == null || !this.isEnabled()) {
            return;
        }
        mc.thePlayer.capabilities.setFlySpeed((float) (speed.getValue() * 0.1f));
    }
}