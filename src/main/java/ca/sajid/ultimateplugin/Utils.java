package ca.sajid.ultimateplugin;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class Utils {

    private static final UltimatePlugin plugin = UltimatePlugin.getPlugin();

    public static void log(Object msg, Object... optionalArgs) {
        plugin.getServer().getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes(
                        '&',
                        String.format(msg.toString(), optionalArgs)
                )
        );
    }

    public static JsonElement fetch(String url) throws IOException {
        URLConnection con = new URL(url).openConnection();
        con.connect();

        InputStreamReader data = new InputStreamReader((InputStream) con.getContent());
        return new JsonParser().parse(data);
    }
}
