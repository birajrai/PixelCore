package io.github.birajrai.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class FlyCommand implements CommandExecutor {
	private final JavaPlugin plugin;

	public FlyCommand(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Check if the sender is a player
		if (!(sender instanceof Player)) {
			sender.sendMessage("This command can only be run by a player.");
			return true;
		}

		// Get the player
		Player player = (Player) sender;

		// Check if the player has permission to use the command
		if (!player.hasPermission("pixelcore.fly")) {
			player.sendMessage("You do not have permission to use this command.");
			return true;
		}

		// Check if the command has arguments
		if (args.length > 1) {
			player.sendMessage("Invalid arguments. Usage: /fly [playername]");
			return true;
		}

		// Get the target player
		Player target = player;

		if (args.length == 1) {
			target = plugin.getServer().getPlayer(args[0]);

			if (target == null) {
				player.sendMessage("Player not found.");
				return true;
			}
		}

		// Toggle the player's flight mode
		if (target.getAllowFlight()) {
			target.setAllowFlight(false);
			target.setFlying(false);
			player.sendMessage(target.getName() + " can no longer fly.");
		} else {
			target.setAllowFlight(true);
			player.sendMessage(target.getName() + " can now fly.");
		}

		return true;
	}
}
