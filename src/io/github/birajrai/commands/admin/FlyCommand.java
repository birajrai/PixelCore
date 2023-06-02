package io.github.birajrai.commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

	private final FileConfiguration config;
	private final FileConfiguration messages;

	public FlyCommand(FileConfiguration config, FileConfiguration messages) {
		this.config = config;
		this.messages = messages;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// Check if the command is enabled in the configuration
		if (!config.getBoolean("fly-command.enabled", true)) {
			sender.sendMessage(messages.getString("fly-command.disabled"));
			return true;
		}

		// Check if the command was run by a player or console
		if (!(sender instanceof Player)) {
			sender.sendMessage(messages.getString("fly-command.only-player"));
			return true;
		}

		// Check if the player has permission to use the command
		if (!sender.hasPermission("pixelcore.fly")) {
			sender.sendMessage(messages.getString("no-permission"));
			return true;
		}

		Player player = (Player) sender;

		// Toggle the player's flight mode
		if (player.getAllowFlight()) {
			player.setAllowFlight(false);
			player.setFlying(false);
			player.sendMessage(messages.getString("fly-command.disabled-message"));
		} else {
			player.setAllowFlight(true);
			player.sendMessage(messages.getString("fly-command.enabled-message"));
		}

		return true;
	}
}
