package bloramcboxingclient.lystech.org.modules;

import bloramcboxingclient.lystech.org.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;


import java.util.ArrayList;
import java.util.List;

public abstract class Module {
    protected Minecraft mc = Minecraft.getMinecraft();
    private String name;
    private String description;
    private boolean enabled;
    private Category category;
    private List<Setting> settings = new ArrayList<Setting>();

    public Module(String name, String description, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public Module(String name, String description, int key, Category category) {
        this.name = name;
        this.description = description;
        this.category = category;
    }

    public void addSettings(Setting... settings) {
        for (Setting setting : settings) {
            addSetting(setting);
        }
    }
    
    public void addSetting(Setting setting) {
        settings.add(setting);
    }

    public List<Setting> getSettings() {
        return settings;
    }

    public void onEnable() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void enable() {
        this.enabled = true;
        onEnable();
    }

    public void disable() {
        this.enabled = false;
        onDisable();
    }

    public void toggle() {
        if (this.enabled) {
            disable();
        } else {
            enable();
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public Category getCategory() {
        return category;
    }
}