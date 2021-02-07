package ca.sajid.ultimateplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage("Welcome to the server, " + event.getPlayer().getName() + "!");
    }
}
