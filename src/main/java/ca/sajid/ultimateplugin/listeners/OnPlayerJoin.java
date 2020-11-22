package ca.sajid.ultimateplugin.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoin implements Listener {
        @EventHandler
        public void onPlayerJoin(PlayerJoinEvent event)
        {
            String serverName = event.getPlayer().getServer().getName();
            event.setJoinMessage("Welcome to" + serverName + "," + event.getPlayer().getName() + "!");
        }
    }
