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

public class Moderating implements Listener {
    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) throws IOException {
        String message = e.getMessage();
        String link = "https://www.purgomalum.com/service/json?text=" + message;
        URL url = new URL(link);
        URLConnection request = url.openConnection();
        request.connect();

        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonObject rootObj = root.getAsJsonObject();
        String text = rootObj.get("result").getAsString();

        e.setMessage(text);
    }
}
