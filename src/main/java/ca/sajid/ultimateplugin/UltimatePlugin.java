package ca.sajid.ultimateplugin;

import ca.sajid.ultimateplugin.commands.test;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltimatePlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        this.getCommand("test").setExecutor(new test());

    }

    @Override
    public void onDisable() {
    }
}
