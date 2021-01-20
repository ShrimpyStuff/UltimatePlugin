package ca.sajid.ultimateplugin;

import ca.sajid.ultimateplugin.commands.Author;
import ca.sajid.ultimateplugin.commands.InventoryOpen;
import ca.sajid.ultimateplugin.commands.Lightning;
import ca.sajid.ultimateplugin.commands.SudoCommand;
import ca.sajid.ultimateplugin.listeners.DeadChest;
import ca.sajid.ultimateplugin.listeners.Moderating;
import ca.sajid.ultimateplugin.listeners.OnChatMessage;
import ca.sajid.ultimateplugin.listeners.OnPlayerJoin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public final class UltimatePlugin extends JavaPlugin {

    private static UltimatePlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;

        getServer().getPluginManager().registerEvents(new OnChatMessage(), this);
        getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new Moderating(), this);
        getServer().getPluginManager().registerEvents(new DeadChest(), this);

        getCommand("author").setExecutor(new Author());
        getCommand("sudo").setExecutor(new SudoCommand());
        getCommand("inventory").setExecutor(new InventoryOpen());
        //getCommand("lightning").setExecutor(new Lightning());

        PluginDescriptionFile desc = getDescription();
        Utils.log("&a%s v%s enabled!", desc.getName(), desc.getVersion());
    }

    public static UltimatePlugin getPlugin() {
        return plugin;
    }
}
