package bloramcboxingclient.lystech.org.config;

import bloramcboxingclient.lystech.org.modules.Module;
import bloramcboxingclient.lystech.org.modules.ModuleManager;
import bloramcboxingclient.lystech.org.settings.BooleanSetting;
import bloramcboxingclient.lystech.org.settings.ColorSetting;
import bloramcboxingclient.lystech.org.settings.ModeSetting;
import bloramcboxingclient.lystech.org.settings.NumberSetting;
import bloramcboxingclient.lystech.org.settings.Setting;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ConfigManager {

    private final File configFile;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final ModuleManager moduleManager;

    public ConfigManager(ModuleManager moduleManager, String configName) {
        this.moduleManager = moduleManager;
        this.configFile = new File(configName);
    }

    public void saveConfig() {
        JsonObject config = new JsonObject();
        for (Module module : moduleManager.getModules()) {
            JsonObject moduleConfig = new JsonObject();
            moduleConfig.addProperty("enabled", module.isEnabled());
            for (Setting setting : module.getSettings()) {
                if (setting instanceof BooleanSetting) {
                    moduleConfig.addProperty(setting.getName(), ((BooleanSetting) setting).isEnabled());
                } else if (setting instanceof NumberSetting) {
                    moduleConfig.addProperty(setting.getName(), ((NumberSetting) setting).getValue());
                } else if (setting instanceof ModeSetting) {
                    moduleConfig.addProperty(setting.getName(), ((ModeSetting) setting).getMode());
                } else if (setting instanceof ColorSetting) {
                    moduleConfig.addProperty(setting.getName(), ((ColorSetting) setting).getColor().getRGB());
                }
            }
            config.add(module.getName(), moduleConfig);
        }

        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        if (!configFile.exists()) {
            return;
        }

        try (FileReader reader = new FileReader(configFile)) {
            JsonElement jsonElement = new JsonParser().parse(reader);
            if (jsonElement.isJsonObject()) {
                JsonObject config = jsonElement.getAsJsonObject();
                for (Module module : moduleManager.getModules()) {
                    if (config.has(module.getName())) {
                        JsonObject moduleConfig = config.getAsJsonObject(module.getName());
                        if (moduleConfig.has("enabled")) {
                            if (moduleConfig.get("enabled").getAsBoolean()) {
                                module.enable();
                            }
                        }
                        for (Setting setting : module.getSettings()) {
                            if (moduleConfig.has(setting.getName())) {
                                if (setting instanceof BooleanSetting) {
                                    ((BooleanSetting) setting).setEnabled(moduleConfig.get(setting.getName()).getAsBoolean());
                                } else if (setting instanceof NumberSetting) {
                                    ((NumberSetting) setting).setValue(moduleConfig.get(setting.getName()).getAsDouble());
                                } else if (setting instanceof ModeSetting) {
                                    ((ModeSetting) setting).setMode(moduleConfig.get(setting.getName()).getAsString());
                                } else if (setting instanceof ColorSetting) {
                                    ((ColorSetting) setting).setColor(moduleConfig.get(setting.getName()).getAsInt());
                                }
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}