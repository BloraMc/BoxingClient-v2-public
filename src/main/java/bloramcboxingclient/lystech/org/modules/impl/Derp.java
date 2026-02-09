package bloramcboxingclient.lystech.org.modules.impl;

import bloramcboxingclient.lystech.org.modules.Category;
import bloramcboxingclient.lystech.org.modules.Module;
import bloramcboxingclient.lystech.org.settings.BooleanSetting;
import bloramcboxingclient.lystech.org.settings.NumberSetting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class Derp extends Module {

    private BooleanSetting headless = new BooleanSetting("Headless", false);
    private BooleanSetting spinny = new BooleanSetting("Spinny", false);
    private NumberSetting increment = new NumberSetting("Increment", 1, 0, 50, 1);

    private Random random = new Random();

    public Derp() {
        super("Derp", "Makes you look silly.", Category.TROLL);
        addSetting(headless);
        addSetting(spinny);
        addSetting(increment);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.thePlayer == null || mc.theWorld == null || !this.isEnabled()) {
            return;
        }

        float newPitch;
        if (headless.isEnabled()) {
            newPitch = 180f;
        } else {
            newPitch = random.nextFloat() * 180f - 90f;
        }

        float newYaw = mc.thePlayer.rotationYaw;
        if (spinny.isEnabled()) {
            newYaw += increment.getValue();
        } else {
            newYaw += random.nextFloat() * 360f - 180f;
        }

        mc.thePlayer.rotationYaw = newYaw;
        mc.thePlayer.rotationPitch = newPitch;
    }
}