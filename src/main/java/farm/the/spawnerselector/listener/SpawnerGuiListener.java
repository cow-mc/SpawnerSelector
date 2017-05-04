package farm.the.spawnerselector.listener;

import farm.the.spawnerselector.SpawnerSelector;
import farm.the.spawnerselector.util.ConfigUtils;
import farm.the.spawnerselector.util.GuiUtils;
import farm.the.spawnerselector.util.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.logging.Level;

public class SpawnerGuiListener implements Listener {
    @EventHandler
    public void onSpawnerSelectorGuiClick(InventoryClickEvent e) {
        if (GuiUtils.isSpawnerGui(e.getWhoClicked().getOpenInventory().getTopInventory(), e.getWhoClicked()) && e.getCurrentItem() != null) {
            // player clicked onto an item inside the spawner selector gui
            e.setCancelled(true);

            if ((e.getCurrentItem().getType() != Material.MONSTER_EGG && e.getCurrentItem().getType() != Material.BARRIER)
                    || !e.getCurrentItem().hasItemMeta() || !e.getCurrentItem().getItemMeta().hasDisplayName()
                    || e.getSlot() != e.getRawSlot()) {
                return;
            }

            String itemName = e.getCurrentItem().getItemMeta().getDisplayName();
            if (itemName.startsWith(ConfigUtils.get("spawnergui.itemname-prefix-current-type"))) {
                // selected entity which already the spawned entity
                e.getWhoClicked().closeInventory();
            } else if (itemName.startsWith(ConfigUtils.get("spawnergui.itemname-prefix-new-type"))) {
                // selected a new entity for the spawner
                EntityType newSpawnerType;
                try {
                    newSpawnerType = EntityType.valueOf(itemName.substring(ConfigUtils.get("spawnergui.itemname-prefix-new-type").length()));
                } catch (IllegalArgumentException ex) {
                    e.getWhoClicked().sendMessage(ChatColor.translateAlternateColorCodes('&', "&c✘ &7Woops, that shouldn't have happened. Please contact a staff member and tell him what you did."));
                    e.getWhoClicked().closeInventory();
                    SpawnerSelector.log.log(Level.SEVERE, "Invalid EntityType ended up in spawner selector gui - this shouldn't ever happen.", ex);
                    return;
                }

                Block spawner = GuiUtils.getSpawner(e.getInventory(), e.getWhoClicked());
                if (spawner.getState().getType() != Material.MOB_SPAWNER) {
                    // somebody destroyed the spawner while the spawner gui was open, abort!
                    e.getWhoClicked().sendMessage(ConfigUtils.get("spawnergui.not-a-spawner",
                            new String[]{"%x%", "%y%", "%z%"},
                            new String[]{String.valueOf(spawner.getX()), String.valueOf(spawner.getY()), String.valueOf(spawner.getZ())}));
                    e.getWhoClicked().closeInventory();
                    return;
                } else if (((CreatureSpawner) spawner.getState()).getSpawnedType() == newSpawnerType) {
                    // somebody else already changed the spawner to the same type we wanted to change to, abort!
                    e.getWhoClicked().sendMessage(ConfigUtils.get("spawnergui.same-entitytype", "%entitytype%", newSpawnerType.toString()));
                    e.getWhoClicked().closeInventory();
                    return;
                }

                // try to remove 1 spawner selector from the player's inventory
                HashMap<Integer, ItemStack> unfoundItems = e.getWhoClicked().getInventory().removeItem(ItemUtils.getSpawnerSelector());
                if (unfoundItems.isEmpty()) {
                    // everything is fine, update spawner type
                    e.getWhoClicked().sendMessage(ConfigUtils.get("spawnergui.success", "%entitytype%", newSpawnerType.toString()));
                    CreatureSpawner creatureSpawner = (CreatureSpawner) spawner.getState();
                    creatureSpawner.setSpawnedType(newSpawnerType);
                    creatureSpawner.update(true);
                } else {
                    // player somehow doesn't have a spawner selector in his inventory anymore, abort!
                    e.getWhoClicked().sendMessage(ConfigUtils.get("spawnergui.no-spawner-selector"));
                }
            }
            e.getWhoClicked().closeInventory();
        }
    }
}
