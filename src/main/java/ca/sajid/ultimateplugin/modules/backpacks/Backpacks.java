package ca.sajid.ultimateplugin.modules.backpacks;

import ca.sajid.ultimateplugin.util.BaseModule;
import ca.sajid.ultimateplugin.util.CustomConfig;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Server;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.ArrayList;
import java.util.List;

public class Backpacks extends BaseModule {

    private final ItemStack backpack = createBackpack();
    private final CustomConfig items = new CustomConfig("backpacks.yml");
    private final NamespacedKey key = new NamespacedKey(getPlugin(), "ultimateplugin.backpack");

    @Override
    public void onEnable() {
        Server server = getPlugin().getServer();
        server.addRecipe(getRecipe());
        server.getPluginManager().registerEvents(new BackpacksListener(this), getPlugin());
    }

    private ItemStack createBackpack() {
        ItemStack backpack = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
        backpack.addEnchantment(Enchantment.DURABILITY, 1);

        LeatherArmorMeta meta = (LeatherArmorMeta) backpack.getItemMeta();
        assert meta != null;

        meta.addItemFlags(
                ItemFlag.HIDE_ENCHANTS,
                ItemFlag.HIDE_ATTRIBUTES,
                ItemFlag.HIDE_UNBREAKABLE,
                ItemFlag.HIDE_POTION_EFFECTS
        );
        meta.setDisplayName("Backpack #?");
        meta.setUnbreakable(true);
        meta.setColor(Color.ORANGE);

        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.DARK_GRAY + "Do NOT multi-craft");
        meta.setLore(lore);

        backpack.setItemMeta(meta);

        return backpack;
    }

    private ShapedRecipe getRecipe() {
        ShapedRecipe recipe = new ShapedRecipe(key, backpack);
        recipe.shape(
                "* *",
                "%%%",
                "*%*"
        );

        recipe.setIngredient('*', Material.TRIPWIRE_HOOK);
        recipe.setIngredient('%', Material.LEATHER);

        return recipe;
    }

    public NamespacedKey getKey() {
        return key;
    }

    public CustomConfig getItems() {
        return items;
    }

    public ItemStack getBackpack() {
        return backpack;
    }
}
