package farm.the.spawnerselector.listener;

import farm.the.spawnerselector.util.GuiUtils;
import farm.the.spawnerselector.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpawnerInteractListener implements Listener {
    /**
     * Open spawner selector gui, but only if the player is allowed to build there
     */
    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInteractWithSpawner(PlayerInteractEvent e) {
        if (e.hasItem() && e.hasBlock() && e.getAction() == Action.RIGHT_CLICK_BLOCK
                && e.getClickedBlock().getType() == Material.MOB_SPAWNER
                && ItemUtils.isSpawnerSelector(e.getItem())) {
            // player rightclicked on a spawner with the spawner selector (and should be allowed to build in the area)
            GuiUtils.openSpawnerSelector(e.getPlayer(), e.getClickedBlock());
        }
    }
}
