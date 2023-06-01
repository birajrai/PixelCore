package io.github.birajrai.commands.admin;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Check if the sender is a player
		if (!(sender instanceof Player)) {
			sender.sendMessage("This command can only be used by players.");
			return true;
		}

		// Check if the player has permission to use this command
		if (!sender.hasPermission("pixelcore.itemcommand")) {
			sender.sendMessage("You do not have permission to use this command.");
			return true;
		}

		Player player = (Player) sender;

		if (args.length < 1) {
			player.sendMessage("Usage: /i <itemname> [amount]");
			return true;
		}

		String itemName = args[0].toUpperCase();
		Material material = Material.getMaterial(itemName);

		if (material == null) {
			player.sendMessage("Invalid item name.");
			return true;
		}

		int amount = 1;
		if (args.length > 1) {
			try {
				amount = Integer.parseInt(args[1]);
				if (amount <= 0 || amount > 64) {
					player.sendMessage("Amount must be between 1 and 64.");
					return true;
				}
			} catch (NumberFormatException e) {
				player.sendMessage("Invalid amount.");
				return true;
			}
		}

		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(itemName);
		item.setItemMeta(meta);

		player.getInventory().addItem(item);
		player.sendMessage("You have been given " + amount + " " + itemName + "(s).");

		return true;
	}
}
