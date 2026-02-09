package bloramcboxingclient.lystech.org.settings;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {
    private String mode;
    private List<String> modes;
    private int index;

    public ModeSetting(String name, String defaultMode, String... modes) {
        super(name);
        this.modes = Arrays.asList(modes);
        this.mode = defaultMode;
        this.index = this.modes.indexOf(defaultMode);
    }

    public String getMode() {
        return mode;
    }

    public List<String> getModes() {
        return modes;
    }

    public void setMode(String mode) {
        this.mode = mode;
        this.index = this.modes.indexOf(mode);
    }

    public void cycle() {
        if (index < modes.size() - 1) {
            index++;
        } else {
            index = 0;
        }
        this.mode = modes.get(index);
    }
}