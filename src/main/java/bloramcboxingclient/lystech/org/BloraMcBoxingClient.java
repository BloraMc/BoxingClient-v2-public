package bloramcboxingclient.lystech.org;

import bloramcboxingclient.lystech.org.commands.CommandManager;
import bloramcboxingclient.lystech.org.commands.impl.ChatCommand;
import bloramcboxingclient.lystech.org.commands.impl.ClickGuiCommand;
import bloramcboxingclient.lystech.org.config.ConfigManager;
import bloramcboxingclient.lystech.org.modules.ModuleManager;
import bloramcboxingclient.lystech.org.ui.clickgui.ClickGuiScreen;
import bloramcboxingclient.lystech.org.ui.hud.Hud;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

@Mod(modid = BloraMcBoxingClient.MODID, name = "BloraMc BoxingClient", version = BloraMcBoxingClient.VERSION)
public class BloraMcBoxingClient
{
    public static final String MODID = "bloramcboxingclient";
    public static final String VERSION = "1.0";

    private CommandManager commandManager;
    private ModuleManager moduleManager;
    private KeyBinding clickGuiKeyBinding;
    private ConfigManager configManager;

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        commandManager = new CommandManager();
        commandManager.register(new ChatCommand());
        commandManager.register(new ClickGuiCommand());

        moduleManager = new ModuleManager();
        configManager = new ConfigManager(moduleManager, "config.json");
        configManager.loadConfig();

        Runtime.getRuntime().addShutdownHook(new Thread(configManager::saveConfig));

        clickGuiKeyBinding = new KeyBinding("Open ClickGUI", Keyboard.KEY_RSHIFT, "BloraMc BoxingClient");
        ClientRegistry.registerKeyBinding(clickGuiKeyBinding);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new Hud(moduleManager));

        System.out.println("BloraMc BoxingClient is loading ...");
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (clickGuiKeyBinding.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new ClickGuiScreen(moduleManager));
        }
    }

    @SubscribeEvent
    public void onChatMessage(net.minecraftforge.client.event.ClientChatReceivedEvent event) {
        commandManager.execute(event.message.getUnformattedText());
    }
}