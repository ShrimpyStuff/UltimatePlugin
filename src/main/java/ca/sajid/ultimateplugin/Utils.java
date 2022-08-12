package ca.sajid.ultimateplugin;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.bukkit.ChatColor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final UltimatePlugin plugin = UltimatePlugin.getPlugin();

    public static void log(Object s, Object... args) {
        String prefix = "[" + plugin.getName() + "]";
        plugin.getServer().getConsoleSender().sendMessage(
                prefix + " " + color(String.format(s.toString(), args))
        );
    }

    public static JsonElement fetch(String url) throws IOException {
        URLConnection con = new URL(url).openConnection();
        con.connect();

        InputStreamReader data = new InputStreamReader((InputStream) con.getContent());
        return JsonParser.parseReader(data);
    }

    public static String color(String s) {
        try {
            // Check that class and method exist
            Class.forName("net.md_5.bungee.api.ChatColor").getMethod("of", String.class);

            // regex is cool isn't it
            Pattern pattern = Pattern.compile("#[a-fA-F\\d]{6}");
            Matcher matcher = pattern.matcher(s);

            while (matcher.find()) {
                String color = s.substring(matcher.start(), matcher.end());
                s = s.replace(color, net.md_5.bungee.api.ChatColor.of(color).toString());

                matcher = pattern.matcher(s);
            }
        } catch (ClassNotFoundException | NoSuchMethodException ignored) {
        }

        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
