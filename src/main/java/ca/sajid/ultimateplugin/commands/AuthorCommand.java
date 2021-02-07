package ca.sajid.ultimateplugin.commands;

import ca.sajid.ultimateplugin.util.BaseCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AuthorCommand extends BaseCommand {

    public AuthorCommand() {
        super("author");
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            player.sendTitle("Authors are", ChatColor.DARK_AQUA + "ElCholoGamer and ServantChild", 10, 50, 10);
        } else {
            sender.sendMessage("Authors are ElCholoGamer and ServantChild");
        }

        return true;
    }

}
