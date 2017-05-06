package farm.the.spawnerselector.util;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.concurrent.ThreadLocalRandom;

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
     * Spins the Squeal of Fortune.<br>
     * If it hits 0, it drops a spawner selector at the block's location.
     *
     * @param probability chance to get a drop (1 in probability)
     * @param block       block where the magic should happen
     */
    public static void spinSquealOfFortune(int probability, Block block) {
        if(probability < 1) {
            // function is disabled, don't drop anything!
            return;
        }
        int RNGesus = ThreadLocalRandom.current().nextInt(probability);
        // 1 in <probability> chance
        if (RNGesus == 0) {
            block.getWorld().dropItem(block.getLocation().add(0.5, 0, 0.5), ItemUtils.getSpawnerSelector());
            block.getWorld().playSound(block.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1, 1.5f);
        }
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
