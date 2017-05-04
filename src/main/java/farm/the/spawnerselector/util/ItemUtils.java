package farm.the.spawnerselector.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtils {
    private static final ItemStack spawnerSelector;

    static {
        spawnerSelector = new ItemStack(Material.matchMaterial(ConfigUtils.get("spawnerselector.material")));
        spawnerSelector.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        ItemMeta itemMeta = spawnerSelector.getItemMeta();
        itemMeta.setDisplayName(ConfigUtils.get("spawnerselector.name"));
        itemMeta.setLore(ConfigUtils.getList("spawnerselector.lore"));
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        spawnerSelector.setItemMeta(itemMeta);
    }

    /**
     * @return spawner selector
     */
    public static ItemStack getSpawnerSelector() {
        return spawnerSelector.clone();
    }

    /**
     * Compare item type and item metadata of used item with the spawner selector
     *
     * @param item used item
     * @return true, if item is a spawner selector
     */
    public static boolean isSpawnerSelector(ItemStack item) {

        return spawnerSelector.isSimilar(item);
    }
}
