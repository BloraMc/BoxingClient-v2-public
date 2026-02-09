package bloramcboxingclient.lystech.org.modules;

import bloramcboxingclient.lystech.org.modules.impl.AimAssist;
import bloramcboxingclient.lystech.org.modules.impl.AutoClicker;
import bloramcboxingclient.lystech.org.modules.impl.CreativeFly;
import bloramcboxingclient.lystech.org.modules.impl.Derp;
import bloramcboxingclient.lystech.org.modules.impl.ESP;
import bloramcboxingclient.lystech.org.modules.impl.FastPlace;
import bloramcboxingclient.lystech.org.modules.impl.Fly;
import bloramcboxingclient.lystech.org.modules.impl.FullBright;
import bloramcboxingclient.lystech.org.modules.impl.KillAura;

import java.util.ArrayList;
import java.util.List;

public class ModuleManager {
    private List<Module> modules = new ArrayList<Module>();

    public ModuleManager() {
        modules.add(new FullBright());
        modules.add(new ESP());
        modules.add(new FastPlace());
        modules.add(new AimAssist());
        modules.add(new AutoClicker());
        modules.add(new Fly());
        modules.add(new CreativeFly());
        modules.add(new Derp());
        modules.add(new KillAura());
    }

    public List<Module> getModules() {
        return modules;
    }

    public Module getModule(String name) {
        for (Module module : modules) {
            if (module.getName().equalsIgnoreCase(name)) {
                return module;
            }
        }
        return null;
    }

    public List<Module> getModulesByCategory(Category category) {
        List<Module> modulesByCategory = new ArrayList<Module>();
        for (Module module : modules) {
            if (module.getCategory() == category) {
                modulesByCategory.add(module);
            }
        }
        return modulesByCategory;
    }
}