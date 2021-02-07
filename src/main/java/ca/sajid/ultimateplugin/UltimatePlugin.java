package ca.sajid.ultimateplugin;

import ca.sajid.ultimateplugin.commands.Author;
import ca.sajid.ultimateplugin.commands.InventoryOpen;
import ca.sajid.ultimateplugin.commands.SudoCommand;
import ca.sajid.ultimateplugin.listeners.DeadChest;
import ca.sajid.ultimateplugin.listeners.Moderating;
import ca.sajid.ultimateplugin.listeners.OnChatMessage;
import ca.sajid.ultimateplugin.listeners.OnPlayerJoin;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltimatePlugin extends JavaPlugin {

    private static UltimatePlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        new OnChatMessage().listen();
        new OnPlayerJoin().listen();
        new Moderating().listen();
        new DeadChest().listen();

        new Author().register();
        new SudoCommand().register();
        new InventoryOpen().register();
        // new Lightning().register();

        Utils.log("&a%s v%s enabled!", getName(), getDescription().getVersion());
    }

    public static UltimatePlugin getPlugin() {
        return plugin;
    }
}
