package farm.the.spawnerselector;

import farm.the.spawnerselector.command.GiveSpawnerSelectorCommand;
import farm.the.spawnerselector.listener.BlockBreakListener;
import farm.the.spawnerselector.listener.BlockDecayListener;
import farm.the.spawnerselector.listener.SpawnerGuiListener;
import farm.the.spawnerselector.listener.SpawnerInteractListener;
import farm.the.spawnerselector.util.ConfigUtils;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

public class SpawnerSelector extends JavaPlugin {
    public static SpawnerSelector plugin;
    public static Logger log;

    @Override
    public void onEnable() {
        plugin = this;
        log = getLogger();

        loadConfig();

        getCommand("spawnerselector").setExecutor(new GiveSpawnerSelectorCommand());

        getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
        getServer().getPluginManager().registerEvents(new BlockDecayListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnerGuiListener(), this);
        getServer().getPluginManager().registerEvents(new SpawnerInteractListener(), this);
    }

    @Override
    public void onDisable() {
    }

    private void loadConfig() {
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists()) {
            log.info("config.yml not found, creating!");
            saveDefaultConfig();
        }

        // load allowed entities for spawner
        LinkedHashSet<EntityType> allowedEntityTypes = new LinkedHashSet<>();
        for (String entityType : SpawnerSelector.plugin.getConfig().getStringList("allowed-spawner-types")) {
            try {
                EntityType allowedEntityType = EntityType.valueOf(entityType.toUpperCase());
                if (allowedEntityType.isSpawnable()) {
                    allowedEntityTypes.add(allowedEntityType);
                } else {
                    throw new IllegalArgumentException();
                }
            } catch (IllegalArgumentException e) {
                SpawnerSelector.log.severe("Invalid EntityType in config.yml, allowed-spawner-types: " + entityType);
            }
        }
        ConfigUtils.setAllowedEntityTypes(allowedEntityTypes);
        log.info("Loaded " + allowedEntityTypes.size() + " entity types as allowed spawner types.");

        // load blocks that drop spawner selectors
        Set<Material> spawnerSelectorDropper = new HashSet<>();
        for (String material : SpawnerSelector.plugin.getConfig().getStringList("spawnerselector.drop-from-materials")) {
            spawnerSelectorDropper.add(Material.matchMaterial(material));
        }
        ConfigUtils.setSpawnerSelectorDropper(spawnerSelectorDropper);
        log.info("Spawner selector is dropped by " + spawnerSelectorDropper.size() + " different blocks.");
    }
}
