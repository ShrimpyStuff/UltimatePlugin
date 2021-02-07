package ca.sajid.ultimateplugin.commands;

import ca.sajid.ultimateplugin.util.BaseCommand;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LightningCommand extends BaseCommand {

    public LightningCommand() {
        super("lightning", true);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        int[] sum = {0, 0};
        int y = 63;

        Chunk[] chunks = player.getWorld().getLoadedChunks();
        for (Chunk c : chunks) {
            sum[0] += c.getX();
            sum[1] += c.getZ();
        }

        int avgX = sum[0] / chunks.length;
        int avgZ = sum[1] / chunks.length;

        Location location = new Location(player.getWorld(), avgX, y, avgZ);
        player.getWorld().strikeLightning(location);

        return true;
    }
}
