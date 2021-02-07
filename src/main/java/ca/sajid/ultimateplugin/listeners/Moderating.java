package ca.sajid.ultimateplugin.listeners;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class Moderating implements Listener {

    // Try to specify the exception type tho
    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) throws IOException {
        String m = e.getMessage();
        String link = "https://www.purgomalum.com/service/json?text=" + URLEncoder.encode(m, "UTF-8");
        URLConnection request = new URL(link).openConnection();

        request.connect();
        InputStreamReader info = new InputStreamReader((InputStream) request.getContent());

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(info);
        JsonObject rootObj = root.getAsJsonObject();
        String text = rootObj.get("result").getAsString();

        e.setMessage(text);
    }
}
