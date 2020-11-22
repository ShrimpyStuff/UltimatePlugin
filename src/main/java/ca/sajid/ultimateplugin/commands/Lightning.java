package ca.sajid.ultimateplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Lightning implements CommandExecutor  {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.getWorld().strikeLightning(player.getLocation());
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be a player to use this command");
        }
        return true;
    }
}
