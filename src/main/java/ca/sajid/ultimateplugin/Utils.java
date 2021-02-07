package ca.sajid.ultimateplugin;

import org.bukkit.ChatColor;

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
}
