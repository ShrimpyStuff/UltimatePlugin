package ca.sajid.ultimateplugin.modules.horseGarage;

import ca.sajid.ultimateplugin.util.BaseModule;
import ca.sajid.ultimateplugin.util.CustomConfig;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Garage extends BaseModule implements Listener {
    private static final CustomConfig garages = new CustomConfig("horseGarages.yml");
    private static Inventory garageForOtherCheck;

    @Override
    public void onEnable() {
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }


    public static void openHorseGarage(Player player) {
        Inventory garage = player.getServer().createInventory(
                player,
                9,
                "Your Horse Garage"
        );

        for (int i = 0; i < 9; i++) {
            ItemStack horse = new ItemStack(Material.HORSE_SPAWN_EGG, 1);
            horse.getItemMeta().setDisplayName(garages.get().getString(player.getName() + "." + i + ".CustomName"));
            garage.setItem(i, horse);
        }

        garageForOtherCheck = garage;

        player.openInventory(garage);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getClickedInventory() != garageForOtherCheck) return;
        e.getWhoClicked().closeInventory();
        ItemStack horse = e.getClickedInventory().getItem(e.getSlot());
        Horse entity = (Horse) e.getWhoClicked().getWorld().spawnEntity(e.getWhoClicked().getLocation(), EntityType.HORSE);
        entity.setCustomName(garages.get().getString(e.getWhoClicked().getName() + "." + e.getSlot() + ".CustomName"));
        entity.setColor(Horse.Color.BLACK);
        entity.setJumpStrength(1);
        entity.setStyle(Horse.Style.WHITE_DOTS);
        entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(1);
    }
}
