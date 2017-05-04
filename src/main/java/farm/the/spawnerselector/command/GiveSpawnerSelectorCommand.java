package farm.the.spawnerselector.command;

import farm.the.spawnerselector.util.ItemUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class GiveSpawnerSelectorCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Command is for players only!");
        } else if (!sender.hasPermission("spawnerselector.give")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c✘ &7You don't have permission to use this command."));
        } else {
            ((Player) sender).getInventory().addItem(ItemUtils.getSpawnerSelector());
        }
        return true;
    }
}
