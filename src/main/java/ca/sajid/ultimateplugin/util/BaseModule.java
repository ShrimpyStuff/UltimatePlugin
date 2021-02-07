package ca.sajid.ultimateplugin.util;

import ca.sajid.ultimateplugin.UltimatePlugin;

public class BaseModule {

    private final UltimatePlugin plugin = UltimatePlugin.getPlugin();

    public void onEnable() {
    }

    public void onDisable() {
    }

    public UltimatePlugin getPlugin() {
        return plugin;
    }
}
