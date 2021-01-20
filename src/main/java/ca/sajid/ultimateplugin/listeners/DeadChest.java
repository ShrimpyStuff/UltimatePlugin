package ca.sajid.ultimateplugin.listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class DeadChest implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        Player player = event.getEntity();
        Location loc = player.getLocation();


            Block block = loc.getBlock();

            if ( block.getType().equals(Material.AIR) ) {

                block.setType(Material.CHEST);

                ItemStack[] deadInv = player.getInventory().getContents();

                event.getDrops().clear();


                for (ItemStack item : deadInv) {

                    Chest chest = (Chest) block.getState();
                    Inventory chestInv = chest.getBlockInventory();
                    if (item != null) {
                        chestInv.addItem(item);
                    }
                }
            }
    }
}