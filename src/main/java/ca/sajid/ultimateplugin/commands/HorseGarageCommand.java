package ca.sajid.ultimateplugin.commands;

import ca.sajid.ultimateplugin.modules.horseGarage.Garage;
import ca.sajid.ultimateplugin.util.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

import java.util.Objects;

public class HorseGarageCommand extends BaseCommand {
    public HorseGarageCommand() {
        super("horses", true);
    }

    @Override
    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (args.length > 0 && Objects.equals(args[0], "add")) {
            if (player.getVehicle() == null || player.getVehicle().getType() != EntityType.HORSE) return false;
            Garage.addHorseToGarage(player, (Horse) player.getVehicle());
        }
        Garage.openHorseGarage(player);
        return true;
    }
}
