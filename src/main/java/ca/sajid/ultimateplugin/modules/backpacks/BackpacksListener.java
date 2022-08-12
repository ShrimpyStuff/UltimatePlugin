package ca.sajid.ultimateplugin.modules.backpacks;

import ca.sajid.ultimateplugin.util.CustomConfig;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BackpacksListener implements Listener {

    private final Backpacks module;

    public BackpacksListener(Backpacks module) {
        this.module = module;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        // Check that player used right-click
        Action action = e.getAction();
        if (!action.toString().contains("RIGHT_CLICK")) return;

        // Check that player isn't sneaking
        Player p = e.getPlayer();
        if (p.isSneaking()) return;

        // Check that player is holding backpack
        ItemStack mainItem = p.getInventory().getItemInMainHand();
        if (mainItem.getType() != Material.LEATHER_CHESTPLATE) return;

        ItemMeta meta = mainItem.getItemMeta();
        if (meta == null || !meta.getDisplayName().matches("Backpack #\\d+")) return;

        // Check that no other interactable block was clicked
        Block clicked = e.getClickedBlock();
        if (clicked != null && clicked.getType().isInteractable()) return;

        e.setCancelled(true);
        p.updateInventory();

        // Create inventory
        final int size = 9;
        String backpackIndex = meta.getDisplayName().split("#")[1];
        Inventory inv = module.getPlugin().getServer().createInventory(
                p,
                size,
                meta.getDisplayName()
        );


        // Load items
        ConfigurationSection items = module.getItems().get().getConfigurationSection(backpackIndex);
        if (items != null) {
            for (int i = 0; i < size; i++) {
                ItemStack savedItem = items.getItemStack(Integer.toString(i));
                if (savedItem != null) inv.setItem(i, savedItem);
            }
        }

        p.openInventory(inv);
        p.playSound(p.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 10, 1);
    }

    @EventHandler
    public void onInventoryClosed(InventoryCloseEvent e) {
        String title = e.getView().getTitle();
        if (!title.matches("Backpack #\\d+")) return;

        String backpackIndex = title.split("#")[1];

        CustomConfig itemsConfig = module.getItems();
        FileConfiguration items = itemsConfig.get();

        // Save inventory contents to backpacks.yml
        ItemStack[] contents = e.getInventory().getContents();
        for (int i = 0; i < contents.length; i++) {
            items.set(backpackIndex + "." + i, contents[i]);
        }

        itemsConfig.save();
    }

    @EventHandler
    public void onItemCrafted(CraftItemEvent e) {
        ItemStack item = e.getRecipe().getResult();
        if (!item.equals(module.getBackpack())) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        FileConfiguration items = module.getItems().get();
        int backpackIndex = 1;

        while (items.contains(Integer.toString(backpackIndex))) {
            backpackIndex++;
        }

        meta.setDisplayName(meta.getDisplayName().replace("?", Integer.toString(backpackIndex)));
        meta.setLore(null);

        item.setItemMeta(meta);
        e.setCurrentItem(item);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().discoverRecipe(module.getKey());
    }
}
