package ca.sajid.ultimateplugin.commands;

import ca.sajid.ultimateplugin.modules.horseGarage.Garage;
import ca.sajid.ultimateplugin.util.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HorseGarageCommand extends BaseCommand {
    public HorseGarageCommand() {
        super("horses", true);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        Garage.openHorseGarage(player);
        return true;
    }
}
