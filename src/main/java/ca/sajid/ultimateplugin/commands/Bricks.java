package ca.sajid.ultimateplugin.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

public class Bricks implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            try {
                int amount = Integer.parseInt(args[0]);

                ItemStack bricks = new ItemStack(Material.BRICK, amount);

                player.getInventory().addItem(bricks);
            } catch (NumberFormatException e) {
                sender.sendMessage(ChatColor.RED + "You need to specify a number");
            }
        } else {
            sender.sendMessage(ChatColor.RED + "You need to be a player to use this command.");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> opts = new ArrayList<>();

        opts.add("<Number>");

        return opts;
    }
}
