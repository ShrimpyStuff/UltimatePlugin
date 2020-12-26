package ca.sajid.ultimateplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvSee implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player targetPlayer = sender.getServer().getPlayer(args[0]);

            if (targetPlayer == null) {
                sender.sendMessage(ChatColor.RED + "Player not found");
                return true;
            }

            Player player = (Player) sender;
            
            if (targetPlayer == player) {
                sender.sendMessage(ChatColor.RED + "Can't access your own inventory");
                return true;
            }
            
            Inventory targetInv = targetPlayer.getInventory();
            player.openInventory(targetInv);
        } else sender.sendMessage("You must be a player to use this command");

        return true;
    }
}
