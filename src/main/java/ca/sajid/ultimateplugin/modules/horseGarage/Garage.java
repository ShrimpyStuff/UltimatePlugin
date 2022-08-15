package ca.sajid.ultimateplugin.modules.horseGarage;

import ca.sajid.ultimateplugin.util.BaseModule;
import ca.sajid.ultimateplugin.util.CustomConfig;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

import static ca.sajid.ultimateplugin.Utils.log;


public class Garage extends BaseModule implements Listener {
    private static final CustomConfig garages = new CustomConfig("horseGarages.yml");

    @Override
    public void onEnable() {
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }


    public static void openHorseGarage(Player player) {
        Inventory garage = player.getServer().createInventory(
                player,
                9,
                player.getName() + "'s horse garage"
        );

        for (int i = 0; i < 9; i++) {
            ItemStack horse = new ItemStack(Material.HORSE_SPAWN_EGG, 1);
            ItemMeta meta  = horse.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(garages.get().getString(player.getName() + "." + i + ".CustomName"));
            }
            horse.setItemMeta(meta);
            garage.setItem(i, horse);
        }

        player.openInventory(garage);
    }

    public static void addHorseToGarage(Player player, Entity horse) {
        log("Ok");
        log(horse);
        if (garages.get().getConfigurationSection(player.getName()) != null) {
            garages.get().set(player.getName() + "." + (Objects.requireNonNull(garages.get().getConfigurationSection(player.getName())).getKeys(false).size()), "I am a horse with the name " + horse.getCustomName());
        }

        garages.save();
    }

    public static void horseDeath(Player player, Entity horse) {

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        log(e.getView().getTitle());
        log(e.getWhoClicked().getName() + "'s horse garage");
        if (!e.getView().getTitle().equals(e.getWhoClicked().getName() + "'s horse garage")) return;
        HumanEntity whoClicked = e.getWhoClicked();
        whoClicked.closeInventory();
        if (garages.get().get(whoClicked.getName() + "." + e.getSlot()) == null) { return; }
        Horse entity = (Horse) whoClicked.getWorld().spawnEntity(whoClicked.getLocation(), EntityType.HORSE);
        entity.isTamed();
        entity.setOwner(whoClicked);
        String customName = garages.get().getString(whoClicked.getName() + "." + e.getSlot() + ".CustomName");
        if (customName == null) customName = "";
        entity.setCustomName(customName);
        entity.setColor(Horse.Color.BLACK);
        entity.setJumpStrength(1);
        String style = garages.get().getString(whoClicked.getName() + "." + e.getSlot() + ".Horse_Style");
        if (style != null) {
            entity.setStyle(Horse.Style.valueOf(style));
        }
        int moveSpeed = garages.get().getInt(whoClicked.getName() + "." + e.getSlot() + ".GENERIC_MOVEMENT_SPEED");
        if (moveSpeed != 0) {
            Objects.requireNonNull(entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(moveSpeed);
        }
    }
}
