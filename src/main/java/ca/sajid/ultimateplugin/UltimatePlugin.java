package ca.sajid.ultimateplugin;

import ca.sajid.ultimateplugin.commands.Bricks;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltimatePlugin extends JavaPlugin {

    @Override
    public void onEnable() {

        this.getCommand("Bricks").setExecutor(new Bricks());

    }

    @Override
    public void onDisable() {
    }
}
