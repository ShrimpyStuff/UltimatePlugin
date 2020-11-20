package ca.sajid.ultimateplugin.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Bricks implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            ItemStack bricks = new ItemStack(Material.BRICK, 20);

            player.getInventory().addItem(bricks);
        } else {
            sender.sendMessage("You need to be a player to use this command.");
        }

        return true;
    }
}