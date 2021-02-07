package ca.sajid.ultimateplugin.commands;

import ca.sajid.ultimateplugin.Utils;
import ca.sajid.ultimateplugin.util.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryCommand extends BaseCommand {

    public InventoryCommand() {
        super("inventory", true);
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        Player target = sender.getServer().getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(Utils.color("&cPlayer not found"));
            return true;
        }

        Player player = (Player) sender;

        // Use .equals() for objects
        if (target.equals(player)) {
            sender.sendMessage(Utils.color("&cCan't access your own inventory"));
            return true;
        }

        Inventory targetInv = target.getInventory();
        player.openInventory(targetInv);
        return true;
    }
}
