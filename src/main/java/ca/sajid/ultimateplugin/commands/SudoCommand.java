package ca.sajid.ultimateplugin.commands;

import ca.sajid.ultimateplugin.util.BaseCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SudoCommand extends BaseCommand {

    public SudoCommand() {
        super("sudo");
    }

    @Override
    public boolean execute(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length < 2) return false;

        Player victim = sender.getServer().getPlayer(args[0]);
        if (victim == null) {
            sender.sendMessage(ChatColor.RED + "Player not found");
            return true;
        }

        if (victim.equals(sender)) {
            sender.sendMessage(ChatColor.RED + "You can't use /sudo on yourself");
            return true;
        }

        String name = victim.getName();

        String[] words = new String[args.length - 1];
        System.arraycopy(args, 1, words, 0, words.length);
        String action = String.join(" ", words);

        if (action.startsWith("c:")) {
            // Chat message
            victim.chat(action.replaceFirst("c:", ""));
            sender.sendMessage(ChatColor.YELLOW + "Message sent as " + name);
        } else {
            boolean op = victim.isOp();
            victim.setOp(true);

            if (victim.performCommand(action.replaceAll("^/", ""))) {
                // Command successful
                sender.sendMessage(ChatColor.YELLOW + "Command executed as " + name);
            } else {
                // Command error
                sender.sendMessage(ChatColor.RED + "Error while executing command as " + name);
            }
            victim.setOp(op);
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> opts = new ArrayList<>();

        switch (args.length) {
            case 1:
                for (Player p : sender.getServer().getOnlinePlayers()) {
                    if (!p.equals(sender)) opts.add(p.getName());
                }
                break;
            case 2:
                opts.add("c:");
        }

        opts.removeIf(s -> !s.startsWith(args[args.length - 1]));
        return opts;
    }
}
