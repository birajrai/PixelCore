package io.github.birajrai.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

	private final FileConfiguration config;
	private final FileConfiguration messages;

	public FeedCommand(FileConfiguration config, FileConfiguration messages) {
		this.config = config;
		this.messages = messages;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Check if the command is enabled in the configuration
		if (!config.getBoolean("feed-command.enabled", true)) {
			sender.sendMessage(messages.getString("feed-command.disabled"));
			return true;
		}

		// Check if the command was run by a player or console
		if (sender instanceof Player) {
			// If the command was run by a player, check if they have permission to use the
			// command
			if (!sender.hasPermission("pixelcore.feed")) {
				sender.sendMessage(messages.getString("no-permission"));
				return true;
			}
		} else {
			// If the command was run by console, check if a player was specified
			if (args.length < 1) {
				sender.sendMessage(messages.getString("invalid-usage"));
				return true;
			}
		}

		Player player;
		if (args.length < 1) {
			// If no player was specified, feed the sender
			if (!(sender instanceof Player)) {
				sender.sendMessage(messages.getString("feed-command.only-player"));
				return true;
			}

			player = (Player) sender;
		} else {
			// If a player was specified, feed that player
			player = Bukkit.getPlayer(args[0]);

			if (player == null) {
				sender.sendMessage(messages.getString("player-not-found"));
				return true;
			}
		}

		player.setFoodLevel(20);
		player.setSaturation(10f);

		if (player == sender) {
			player.sendMessage(messages.getString("self-fed"));
		} else {
			sender.sendMessage(messages.getString("sender-fed").replace("%player%", player.getName()));
			player.sendMessage(messages.getString("player-fed").replace("%sender%", sender.getName()));
		}

		return true;
	}
}
