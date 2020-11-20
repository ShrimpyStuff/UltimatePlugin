package ca.sajid.ultimateplugin.listeners;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChatMessage implements Listener {

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
    }
}
