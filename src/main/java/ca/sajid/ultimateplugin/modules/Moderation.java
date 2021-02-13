package ca.sajid.ultimateplugin.modules;

import ca.sajid.ultimateplugin.UltimatePlugin;
import ca.sajid.ultimateplugin.Utils;
import ca.sajid.ultimateplugin.util.BaseModule;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;
import java.net.URLEncoder;

public class Moderation extends BaseModule implements Listener {

    private final UltimatePlugin plugin = UltimatePlugin.getPlugin();

    @Override
    public void onEnable() {
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) throws IOException {
        if (!plugin.getConfig().getBoolean("moderation")) return;
        String m = e.getMessage();
        String url = "https://www.purgomalum.com/service/json?text=" + URLEncoder.encode(m, "UTF-8");

        JsonElement root = Utils.fetch(url);
        JsonObject rootObj = root.getAsJsonObject();
        String text = rootObj.get("result").getAsString();
        
        e.setMessage(ChatColor.translateAlternateColorCodes('&', text));
    }
}
