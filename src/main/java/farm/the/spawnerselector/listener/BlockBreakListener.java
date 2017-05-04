package farm.the.spawnerselector.listener;

import farm.the.spawnerselector.SpawnerSelector;
import farm.the.spawnerselector.util.ConfigUtils;
import farm.the.spawnerselector.util.ItemUtils;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.concurrent.ThreadLocalRandom;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (ConfigUtils.isSpawnerSelectorDropper(e.getBlock().getType())) {
            int RNGesus = ThreadLocalRandom.current().nextInt(SpawnerSelector.plugin.getConfig().getInt("spawnerselector.drop-probability", 500));
            // 1 in (by default) 500 chance
            if (RNGesus == 0) {
                e.getBlock().getWorld().dropItem(e.getBlock().getLocation().add(0.5, 0, 0.5), ItemUtils.getSpawnerSelector());
                e.getBlock().getWorld().playSound(e.getBlock().getLocation(), Sound.ENTITY_PLAYER_LEVELUP, SoundCategory.MASTER, 1, 1.5f);
            }
        }
    }
}
