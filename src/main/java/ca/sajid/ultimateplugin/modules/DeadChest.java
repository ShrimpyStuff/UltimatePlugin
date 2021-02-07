package ca.sajid.ultimateplugin.modules;

import ca.sajid.ultimateplugin.util.BaseModule;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Arrays;
import java.util.Objects;

public class DeadChest extends BaseModule implements Listener {

    @Override
    public void onEnable() {
        getPlugin().getServer().getPluginManager().registerEvents(this, getPlugin());
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        PlayerInventory inv = player.getInventory();
        Block block = player.getLocation().getBlock();

        if (inv.isEmpty() || !block.getType().equals(Material.AIR)) return;

        block.setType(Material.CHEST);
        Chest chest = (Chest) block.getState();
        Inventory chestInv = chest.getBlockInventory();

        ItemStack[] deadInv = player.getInventory().getContents();

        e.getDrops().clear();

        // This is pretty cool isn't it huh
        Arrays.stream(deadInv).filter(Objects::nonNull).forEach(chestInv::addItem);
    }
}