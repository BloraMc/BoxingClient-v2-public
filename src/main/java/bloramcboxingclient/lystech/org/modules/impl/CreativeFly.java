package bloramcboxingclient.lystech.org.modules.impl;

import bloramcboxingclient.lystech.org.modules.Category;
import bloramcboxingclient.lystech.org.modules.Module;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class CreativeFly extends Module {

    public CreativeFly() {
        super("CreativeFly", "Allows you to fly in creative mode.", Category.MOVEMENT);
    }

    @Override
    public void onEnable() {
        if (mc.thePlayer != null) {
            mc.thePlayer.capabilities.allowFlying = true;
        }
    }

    @Override
    public void onDisable() {
        if (mc.thePlayer != null && !mc.thePlayer.capabilities.isCreativeMode) {
            mc.thePlayer.capabilities.allowFlying = false;
            mc.thePlayer.capabilities.isFlying = false;
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null) {
            return;
        }
        
        if (this.isEnabled()) {
            mc.thePlayer.capabilities.allowFlying = true;
        }
    }
}