package ca.sajid.ultimateplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Author implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        //sender.sendMessage("Authors are " + ChatColor.DARK_AQUA + "ElCholoGamer and ServantChild");
        Player player = (Player) sender;
        player.sendTitle("Authors are " + ChatColor.DARK_AQUA + "ElCholoGamer and ServantChild", null, 1, 5, 1);

        return true;
    }

}
