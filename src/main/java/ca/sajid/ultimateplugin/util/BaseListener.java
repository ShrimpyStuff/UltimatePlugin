package ca.sajid.ultimateplugin.util;

import ca.sajid.ultimateplugin.UltimatePlugin;
import org.bukkit.event.Listener;

public abstract class BaseListener implements Listener {

    private final UltimatePlugin plugin = UltimatePlugin.getPlugin();

    public void listen() {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public UltimatePlugin getPlugin() {
        return plugin;
    }
}
