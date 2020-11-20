package ca.sajid.ultimateplugin;

import org.bukkit.ChatColor;

public class Utils {

    public static void log(Object msg, Object... optionalArgs) {
        UltimatePlugin.getPlugin().getServer().getConsoleSender().sendMessage(
                ChatColor.translateAlternateColorCodes(
                        '&',
                        String.format(msg.toString(), optionalArgs)
                )
        );
    }
}
