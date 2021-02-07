package ca.sajid.ultimateplugin.modules;

import ca.sajid.ultimateplugin.UltimatePlugin;
import ca.sajid.ultimateplugin.Utils;
import ca.sajid.ultimateplugin.util.BaseModule;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class WelcomeMessage extends BaseModule implements Listener {

    private final UltimatePlugin plugin = UltimatePlugin.getPlugin();

    @Override
    public void onEnable() {
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String message = plugin.getConfig().getString("join_message");

        if (message != null && !message.equals("")) {
            e.setJoinMessage(Utils.color(message.replace("{player}", p.getName())));
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        String message = plugin.getConfig().getString("quit_message");

        if (message != null && !message.equals("")) {
            e.setQuitMessage(Utils.color(message.replace("{player}", p.getName())));
        }
    }
}
