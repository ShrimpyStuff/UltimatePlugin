package ca.sajid.ultimateplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Author implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        sender.sendMessage("Authors are" + ChatColor.DARK_AQUA + "ElCholoGamer and ServantChild");

        return true;
    }

}
