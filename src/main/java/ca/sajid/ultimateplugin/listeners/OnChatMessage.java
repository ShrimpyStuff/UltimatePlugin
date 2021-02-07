package ca.sajid.ultimateplugin.listeners;

import ca.sajid.ultimateplugin.util.BaseListener;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class OnChatMessage extends BaseListener {

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        e.setMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
    }
}
