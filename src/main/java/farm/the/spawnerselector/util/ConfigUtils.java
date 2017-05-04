package farm.the.spawnerselector.util;

import farm.the.spawnerselector.SpawnerSelector;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ConfigUtils {
    private static LinkedHashSet<EntityType> allowedEntityTypes;
    private static Set<Material> spawnerSelectorDropper;

    /**
     * Get formatted message from config.yml
     *
     * @param key config key
     * @return formatted config entry
     */
    public static String get(String key) {
        return ChatColor.translateAlternateColorCodes('&',
                SpawnerSelector.plugin.getConfig().getString(key, "&c✘ &7Invalid entry in config.yml: " + key));
    }

    /**
     * Get formatted and placeholder-less message from config.yml
     *
     * @param key     config key
     * @param search  search for these placeholders
     * @param replace replace the placeholders with these entries
     * @return formatted config entry
     */
    public static String get(String key, String[] search, String[] replace) {
        return StringUtils.replaceEach(get(key), search, replace);
    }

    /**
     * Get formatted and placeholder-less message from config.yml
     *
     * @param key     config key
     * @param search  search for this placeholder
     * @param replace replace the placeholder with this value
     * @return formatted config entry
     */
    public static String get(String key, String search, String replace) {
        return StringUtils.replace(get(key), search, replace);
    }

    /**
     * Get formatted string list from config.yml
     *
     * @param key config key
     * @return formatted string list
     */
    public static List<String> getList(String key) {
        List<String> list = SpawnerSelector.plugin.getConfig().getStringList(key);
        for (int i = 0; i < list.size(); i++) {
            list.set(i, ChatColor.translateAlternateColorCodes('&', list.get(i)));
        }
        return list;
    }

    public static void setAllowedEntityTypes(LinkedHashSet<EntityType> allowedEntityTypes) {
        ConfigUtils.allowedEntityTypes = allowedEntityTypes;
    }

    public static LinkedHashSet<EntityType> getAllowedEntityTypes() {
        return allowedEntityTypes;
    }

    public static void setSpawnerSelectorDropper(Set<Material> spawnerSelectorDropper) {
        ConfigUtils.spawnerSelectorDropper = spawnerSelectorDropper;
    }

    public static boolean isSpawnerSelectorDropper(Material material) {
        return spawnerSelectorDropper.contains(material);
    }
}
