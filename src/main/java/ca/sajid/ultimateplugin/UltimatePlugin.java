package ca.sajid.ultimateplugin;

import ca.sajid.ultimateplugin.commands.*;
import ca.sajid.ultimateplugin.modules.DeadChest;
import ca.sajid.ultimateplugin.modules.Moderation;
import ca.sajid.ultimateplugin.modules.SansAI;
import ca.sajid.ultimateplugin.modules.WelcomeMessage;
import ca.sajid.ultimateplugin.modules.backpacks.Backpacks;
import ca.sajid.ultimateplugin.modules.horseGarage.Garage;
import ca.sajid.ultimateplugin.util.ModuleManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltimatePlugin extends JavaPlugin {

    private static UltimatePlugin plugin;
    private static final ModuleManager modules = new ModuleManager();

    @Override
    public void onEnable() {
        plugin = this;

        saveDefaultConfig();

        // Register commands
        new AuthorCommand().register();
        new SudoCommand().register();
        new InventoryCommand().register();
        new LightningCommand().register();
        new HorseGarageCommand().register();

        modules.load(Backpacks.class);
        modules.load(DeadChest.class);
        modules.load(Moderation.class);
        modules.load(WelcomeMessage.class);
        modules.load(SansAI.class);
        modules.load(Garage.class);

        Utils.log("&a%s v%s enabled!", getName(), getDescription().getVersion());
    }

    @Override
    public void onDisable() {
        modules.disable();
    }

    public static UltimatePlugin getPlugin() {
        return plugin;
    }
}
