package ca.sajid.ultimateplugin.modules.horseGarage;

import ca.sajid.ultimateplugin.util.BaseModule;
import ca.sajid.ultimateplugin.util.CustomConfig;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;


public class Garage extends BaseModule implements Listener {
    private static final CustomConfig garages = new CustomConfig("horseGarages.yml");

    @Override
    public void onEnable() {
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }


    public static void openHorseGarage(Player player) {
        FileConfiguration config = garages.get();
        Inventory garage = player.getServer().createInventory(
                player,
                9,
                player.getName() + "'s horse garage"
        );

        for (int i = 0; i < 9; i++) {
            ItemStack horse = new ItemStack(Material.HORSE_SPAWN_EGG, 1);
            ItemMeta meta  = horse.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(config.getString(player.getName() + "." + i + ".CustomName"));
            }
            horse.setItemMeta(meta);
            garage.setItem(i, horse);
        }

        player.openInventory(garage);
    }

    public static void addHorseToGarage(Player player, Horse horse) {
        if (garages.get().contains(horse.getUniqueId().toString())) {
            player.sendMessage("This horse is already registered");
            return;
        }
        FileConfiguration config = garages.get();
        ConfigurationSection configSec = config.getConfigurationSection(player.getName());
        int index = 0;
        if (configSec != null) {
            index = configSec.getKeys(false).size();
        }
        String defaultKey = player.getName() + "." + index;
        config.set(defaultKey + ".CustomName", horse.getCustomName());
        //Americans using color instead of the proper colour smh
        config.set(defaultKey + ".Color", horse.getColor().toString());
        config.set(defaultKey + ".Style", horse.getStyle().toString());
        config.set(defaultKey + ".JumpStrength", horse.getJumpStrength());
        config.set(defaultKey + ".GENERIC_MAX_HEALTH", Objects.requireNonNull(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getBaseValue());
        config.set(defaultKey + ".GENERIC_MOVEMENT_SPEED", Objects.requireNonNull(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).getBaseValue());
        config.set(defaultKey + ".Invulnerable", horse.isInvulnerable());
        config.set(defaultKey + ".Saddle", horse.getInventory().getSaddle());
        config.set(defaultKey + ".Armor", horse.getInventory().getArmor());

        garages.save();
        horse.remove();

        player.sendTitle(horse.getCustomName(), "was saved in slot " + (index + 1), 10, 70, 20);
    }

    public static void horseDeath(Entity horse) {
        FileConfiguration config = garages.get();
        String a = "frog" + horse.getCustomName();
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        if (e.getEntity().getType() == EntityType.HORSE) {
            horseDeath(e.getEntity());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equals(e.getWhoClicked().getName() + "'s horse garage")) return;
        HumanEntity whoClicked = e.getWhoClicked();
        whoClicked.closeInventory();
        FileConfiguration config = garages.get();
        if (config.get(whoClicked.getName() + "." + e.getSlot()) == null) { e.getWhoClicked().sendMessage("No horse in this slot"); return; }
        Horse horse = (Horse) whoClicked.getWorld().spawnEntity(whoClicked.getLocation(), EntityType.HORSE);
        horse.setOwner(whoClicked);
        String defaultKey = whoClicked.getName() + "." + e.getSlot();
        String customName = config.getString(whoClicked.getName() + "." + e.getSlot() + ".CustomName");
        if (customName == null) customName = "";
        horse.setCustomName(customName);
        horse.setInvulnerable(config.getBoolean(defaultKey + ".Invulnerable"));
        horse.setColor(Horse.Color.valueOf(config.getString(defaultKey + ".Color")));
        horse.setJumpStrength(config.getDouble(defaultKey + ".JumpStrength"));
        String style = config.getString(defaultKey + ".Style");
        if (style != null) {
            horse.setStyle(Horse.Style.valueOf(style));
        }
        double moveSpeed = config.getDouble(defaultKey + ".GENERIC_MOVEMENT_SPEED");
        if (moveSpeed != 0) {
            Objects.requireNonNull(horse.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED)).setBaseValue(moveSpeed);
        }
        double maxHeath = config.getDouble(defaultKey + ".GENERIC_MAX_HEALTH");
        if (maxHeath != 0) {
            Objects.requireNonNull(horse.getAttribute(Attribute.GENERIC_MAX_HEALTH)).setBaseValue(maxHeath);
        }
        horse.getInventory().setSaddle(config.getItemStack(defaultKey + ".Saddle"));
        horse.getInventory().setArmor(config.getItemStack(defaultKey + ".Armor"));

        config.set(defaultKey + ".TempUUID", horse.getUniqueId().toString());
        garages.save();
    }
}
