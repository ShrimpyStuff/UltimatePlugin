package ca.sajid.ultimateplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryOpen implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You must be an operator to use this command");
            return true;
        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command");
            return true;

        }

        Player target = sender.getServer().getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(ChatColor.RED + "Player not found");
            return true;
        }

        Player player = (Player) sender;

        // Use .equals() for objects
        if (target.equals(player)) {
            sender.sendMessage(ChatColor.RED + "Can't access your own inventory");
            return true;
        }

        Inventory targetInv = target.getInventory();
        player.openInventory(targetInv);
        return true;
    }
}
