package farm.the.spawnerselector.listener;

import farm.the.spawnerselector.SpawnerSelector;
import farm.the.spawnerselector.util.ConfigUtils;
import farm.the.spawnerselector.util.ItemUtils;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (ConfigUtils.isSpawnerSelectorDropper(e.getBlock().getType()) && e.getPlayer().getEquipment().getItemInMainHand().getType() != Material.SHEARS) {
            int probability = SpawnerSelector.plugin.getConfig().getInt("spawnerselector.drop-probability.manual", 500);
            ItemUtils.spinSquealOfFortune(probability, e.getBlock());
        }
    }
}
