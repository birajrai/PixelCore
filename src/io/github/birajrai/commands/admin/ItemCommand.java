package io.github.birajrai.commands.admin;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemCommand implements CommandExecutor {
	private final FileConfiguration config;
	private final FileConfiguration messages;

	public ItemCommand(FileConfiguration config, FileConfiguration messages) {
		this.config = config;
		this.messages = messages;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Check if the command is enabled in the configuration
		if (!config.getBoolean("item-command.enabled", true)) {
			sender.sendMessage(messages.getString("item-command.disabled"));
			return true;
		}

		// Check if the sender is a player
		if (!(sender instanceof Player)) {
			sender.sendMessage(messages.getString("item-command.only-player"));
			return true;
		}

		// Check if the player has permission to use the command
		if (!sender.hasPermission("pixelcore.itemcommand")) {
			sender.sendMessage(messages.getString("no-permission"));
			return true;
		}

		Player player = (Player) sender;

		if (args.length < 1) {
			player.sendMessage(messages.getString("item-command.usage"));
			return true;
		}

		String itemName = args[0].toUpperCase();
		Material material = Material.getMaterial(itemName);

		if (material == null) {
			player.sendMessage(messages.getString("item-command.invalid-item-name"));
			return true;
		}

		int amount = 1;
		if (args.length > 1) {
			try {
				amount = Integer.parseInt(args[1]);
				if (amount <= 0 || amount > 64) {
					player.sendMessage(messages.getString("item-command.invalid-amount"));
					return true;
				}
			} catch (NumberFormatException e) {
				player.sendMessage(messages.getString("item-command.invalid-amount"));
				return true;
			}
		}

		ItemStack item = new ItemStack(material, amount);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(itemName);
		item.setItemMeta(meta);

		player.getInventory().addItem(item);
		player.sendMessage(messages.getString("item-command.item-given").replace("%amount%", String.valueOf(amount))
				.replace("%item%", itemName));

		return true;
	}
}
