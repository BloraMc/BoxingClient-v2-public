package bloramcboxingclient.lystech.org.modules.impl;

import bloramcboxingclient.lystech.org.modules.Category;
import bloramcboxingclient.lystech.org.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.lang.reflect.Field;

public class FastPlace extends Module {

    private Field rightClickDelayTimerField;

    public FastPlace() {
        super("FastPlace", "Removes right-click delay.", Category.PLAYER);
        try {
            rightClickDelayTimerField = Minecraft.class.getDeclaredField("field_71467_ac");
            rightClickDelayTimerField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.theWorld == null || mc.thePlayer == null) {
            return;
        }
        if (rightClickDelayTimerField != null) {
            try {
                rightClickDelayTimerField.setInt(mc, 0);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}