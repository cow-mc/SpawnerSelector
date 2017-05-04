package farm.the.spawnerselector.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiUtils {
    private static final Pattern SPAWNER_GUI_COORDS_PATTERN = Pattern.compile(ChatColor.stripColor(ConfigUtils.get("spawnergui.title")) + " \\[(-?[0-9]+),([0-9]+),(-?[0-9]+)]");

    /**
     * Opens spawner selector gui
     *
     * @param player involved player
     * @param block  corresponding spawner block
     */
    public static void openSpawnerSelector(Player player, Block block) {
        CreatureSpawner spawner = (CreatureSpawner) block.getState();
        EntityType currentSpawnedType = spawner.getSpawnedType();

        Inventory spawnerGui = Bukkit.createInventory(player, getSmallestPossibleGuiSize(ConfigUtils.getAllowedEntityTypes().size()),
                ConfigUtils.get("spawnergui.title") + ChatColor.translateAlternateColorCodes('&',
                        String.format(" &8[&7%d&8,&7%d&8,&7%d&8]", block.getX(), block.getY(), block.getZ())));

        for (EntityType entityType : ConfigUtils.getAllowedEntityTypes()) {
            ItemStack item;
            if (entityType == currentSpawnedType) {
                item = new ItemStack(Material.BARRIER);
                ItemMeta itemMeta = item.getItemMeta();

                itemMeta.setDisplayName(ConfigUtils.get("spawnergui.itemname-prefix-current-type") + entityType.toString());
                item.setItemMeta(itemMeta);
            } else {
                item = new ItemStack(Material.MONSTER_EGG, 1, entityType.getTypeId());
                ItemMeta itemMeta = item.getItemMeta();

                itemMeta.setDisplayName(ConfigUtils.get("spawnergui.itemname-prefix-new-type") + entityType.toString());
                item.setItemMeta(itemMeta);
            }
            spawnerGui.addItem(item);
        }

        player.openInventory(spawnerGui);
    }

    /**
     * Check if open inventory is a spawner selector gui
     *
     * @param inventory opened inventory
     * @param player    involved player
     * @return true if it is
     */
    public static boolean isSpawnerGui(Inventory inventory, HumanEntity player) {
        return inventory != null && inventory.getName().startsWith(ConfigUtils.get("spawnergui.title")) && player.equals(inventory.getHolder());
    }

    /**
     * Extracts coordinates from inventory title and returns the corresponding block
     *
     * @param inventory opened spawner selector inventory
     * @param player    involved player
     * @return block at coordinates
     */
    public static Block getSpawner(Inventory inventory, HumanEntity player) {
        Block spawner = null;
        Matcher matcher = SPAWNER_GUI_COORDS_PATTERN.matcher(ChatColor.stripColor(inventory.getName()));
        if (matcher.find()) {
            spawner = player.getWorld().getBlockAt(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
        }
        return spawner;
    }

    /**
     * Calculates the smallest possible gui size
     *
     * @param minimumSlots minimum amount of slots that have to be available
     * @return next upper multiplicative of 9, max value is 54
     */
    private static int getSmallestPossibleGuiSize(int minimumSlots) {
        return Math.min(54, ((minimumSlots / 9) + ((minimumSlots % 9 == 0) ? 0 : 1)) * 9);
    }
}
