package ca.sajid.ultimateplugin.util;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class ModuleManager {

    private final List<BaseModule> modules = new ArrayList<>();

    // idk why im using generics and reflect but hey it looks cool doesnt it
    public <T extends BaseModule> T load(Class<T> clazz) {
        try {
            Constructor<T> constructor = clazz.getConstructor();
            T module = constructor.newInstance();
            modules.add(module);

            module.onEnable();
            return module;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void disable() {
        modules.forEach(BaseModule::onDisable);
    }

    public List<BaseModule> getModules() {
        return modules;
    }
}
