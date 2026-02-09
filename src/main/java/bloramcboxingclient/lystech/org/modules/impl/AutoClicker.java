package bloramcboxingclient.lystech.org.modules.impl;

import bloramcboxingclient.lystech.org.modules.Category;
import bloramcboxingclient.lystech.org.modules.Module;
import bloramcboxingclient.lystech.org.settings.BooleanSetting;
import bloramcboxingclient.lystech.org.settings.NumberSetting;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.Random;

public class AutoClicker extends Module {
    private NumberSetting cps = new NumberSetting("CPS", 12, 1, 20, 1);
    private NumberSetting randomization = new NumberSetting("Randomization", 15, 0, 50, 1);
    private BooleanSetting onlyWhenHold = new BooleanSetting("Only when hold", true);
    private Random random = new Random();
    private long lastClickTime = 0;

    public AutoClicker() {
        super("AutoClicker", "Automatically clicks for you.", Category.COMBAT);
        addSetting(cps);
        addSetting(randomization);
        addSetting(onlyWhenHold);
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if (mc.theWorld == null || mc.thePlayer == null) {
            return;
        }

        if (onlyWhenHold.isEnabled() && !mc.gameSettings.keyBindAttack.isKeyDown()) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        
        double cpsValue = cps.getValue();
        double randomPercent = randomization.getValue() / 100.0;
        double cpsMin = cpsValue * (1 - randomPercent);
        double cpsMax = cpsValue * (1 + randomPercent);
        double randomizedCps = cpsMin + (random.nextDouble() * (cpsMax - cpsMin));
        
        long delay = (long) (1000 / randomizedCps);

        if (currentTime - lastClickTime >= delay) {
            KeyBinding.onTick(mc.gameSettings.keyBindAttack.getKeyCode());
            lastClickTime = currentTime;
        }
    }
}