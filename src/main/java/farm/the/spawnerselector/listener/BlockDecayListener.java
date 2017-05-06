package farm.the.spawnerselector.listener;

import farm.the.spawnerselector.SpawnerSelector;
import farm.the.spawnerselector.util.ItemUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class BlockDecayListener implements Listener {
    @EventHandler
    public void onLeafDecay(LeavesDecayEvent e) {
        int probability = SpawnerSelector.plugin.getConfig().getInt("spawnerselector.drop-probability.leaf-decay", 20000);
        ItemUtils.spinSquealOfFortune(probability, e.getBlock());
    }
}
