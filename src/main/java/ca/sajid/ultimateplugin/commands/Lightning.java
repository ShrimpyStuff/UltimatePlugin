package ca.sajid.ultimateplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Lightning implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You need to be a player to use this command");
            return true;
        }

        Player player = (Player) sender;

        int[] sum = {0, 0};
        int Y = 63;

        Chunk[] chunks = player.getWorld().getLoadedChunks();
        for (Chunk c : chunks) {
            sum[0] += c.getX();
            sum[1] += c.getZ();
            int avgX = sum[0] / chunks.length;
            int avgZ = sum[1] / chunks.length;
            Location location = new Location(player.getWorld(), avgX, Y, avgZ);
            player.getWorld().strikeLightning(location);
        }

        /*int avgX = sumX / chunks.length;
        int avgZ = sumZ / chunks.length;
        Location location = new Location(player.getWorld(), avgX, sumY, avgZ);
        player.getWorld().strikeLightning(location);*/

        return true;
    }
}
