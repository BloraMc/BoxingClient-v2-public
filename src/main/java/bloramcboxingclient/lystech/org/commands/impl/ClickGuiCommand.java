package bloramcboxingclient.lystech.org.commands.impl;

import bloramcboxingclient.lystech.org.BloraMcBoxingClient;
import bloramcboxingclient.lystech.org.commands.Command;
import bloramcboxingclient.lystech.org.modules.ModuleManager;
import bloramcboxingclient.lystech.org.ui.clickgui.ClickGuiScreen;
import net.minecraft.client.Minecraft;

public class ClickGuiCommand extends Command {
    public ClickGuiCommand() {
        super("clickgui", "Opens the ClickGUI.");
    }

    @Override
    public void execute(String[] args) {
        Minecraft.getMinecraft().displayGuiScreen(new ClickGuiScreen(new ModuleManager()));
    }
}