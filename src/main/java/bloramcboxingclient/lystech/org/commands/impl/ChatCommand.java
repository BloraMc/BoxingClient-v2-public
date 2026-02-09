package bloramcboxingclient.lystech.org.commands.impl;

import bloramcboxingclient.lystech.org.commands.Command;
import net.minecraft.client.Minecraft;

public class ChatCommand extends Command {
    public ChatCommand() {
        super("chat", "Sends a message in the chat.");
    }

    @Override
    public void execute(String[] args) {
        if (args.length > 0) {
            String message = String.join(" ", args);
            Minecraft.getMinecraft().thePlayer.sendChatMessage(message);
        }
    }
}