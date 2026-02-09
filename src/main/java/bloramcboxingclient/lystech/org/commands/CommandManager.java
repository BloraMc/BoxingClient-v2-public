package bloramcboxingclient.lystech.org.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager {
    private List<Command> commands = new ArrayList<Command>();

    public void register(Command command) {
        commands.add(command);
    }

    public void execute(String message) {
        if (message.startsWith(".")) {
            String[] parts = message.substring(1).split(" ");
            String commandName = parts[0];
            String[] args = Arrays.copyOfRange(parts, 1, parts.length);

            for (Command command : commands) {
                if (command.getName().equalsIgnoreCase(commandName)) {
                    command.execute(args);
                    return;
                }
            }
        }
    }
}