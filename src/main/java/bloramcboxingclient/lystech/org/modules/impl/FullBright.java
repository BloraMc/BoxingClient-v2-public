package bloramcboxingclient.lystech.org.modules.impl;

import bloramcboxingclient.lystech.org.modules.Category;
import bloramcboxingclient.lystech.org.modules.Module;

public class FullBright extends Module {
    private float oldGamma;

    public FullBright() {
        super("FullBright", "Makes the world brighter.", Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.oldGamma = mc.gameSettings.gammaSetting;
        mc.gameSettings.gammaSetting = 100.0F;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        mc.gameSettings.gammaSetting = this.oldGamma;
    }
}