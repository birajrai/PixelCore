package io.github.birajrai.commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FeedCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Check if the command was run by a player or console
		if (sender instanceof Player) {
			// If the command was run by a player, check if they have permission to use the
			// command
			if (!sender.hasPermission("pixelcore.feed")) {
				sender.sendMessage("You do not have permission to use this command.");
				return true;
			}
		} else {
			// If the command was run by console, check if a player was specified
			if (args.length < 1) {
				sender.sendMessage("Usage: /feed <playername>");
				return true;
			}
		}

		Player player;
		if (args.length < 1) {
			// If no player was specified, feed the sender
			if (!(sender instanceof Player)) {
				sender.sendMessage("You must be a player to use this command without a playername.");
				return true;
			}

			player = (Player) sender;
		} else {
			// If a player was specified, feed that player
			player = Bukkit.getPlayer(args[0]);

			if (player == null) {
				sender.sendMessage("Player not found.");
				return true;
			}
		}

		player.setFoodLevel(20);
		player.setSaturation(10f);

		if (player == sender) {
			player.sendMessage("You have been feed.");
		} else {
			sender.sendMessage("You have feed " + player.getName() + ".");
			player.sendMessage("You have been feed by " + sender.getName() + ".");
		}

		return true;
	}
}
