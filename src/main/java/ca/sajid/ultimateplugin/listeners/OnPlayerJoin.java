package ca.sajid.ultimateplugin.listeners;

import ca.sajid.ultimateplugin.util.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin extends BaseListener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("Welcome to the server, " + event.getPlayer().getName() + "!");
    }
}
