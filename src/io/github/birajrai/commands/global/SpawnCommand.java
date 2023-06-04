package io.github.birajrai.commands.global;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SpawnCommand implements CommandExecutor {

	private final FileConfiguration messages;
	private final Plugin plugin;

	public SpawnCommand(FileConfiguration messages, Plugin plugin) {
		this.messages = messages;
		this.plugin = plugin;
	}

	private Location getSpawnLocation() {
		FileConfiguration config = plugin.getConfig();
		if (config.contains("spawnLocation")) {
			World world = plugin.getServer().getWorld(config.getString("spawnLocation.world"));
			double x = config.getDouble("spawnLocation.x");
			double y = config.getDouble("spawnLocation.y");
			double z = config.getDouble("spawnLocation.z");
			float yaw = (float) config.getDouble("spawnLocation.yaw");
			float pitch = (float) config.getDouble("spawnLocation.pitch");
			return new Location(world, x, y, z, yaw, pitch);
		}
		return null;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(messages.getString("spawn-command.only-player"));
			return true;
		}

		Player player = (Player) sender;

		if (!player.hasPermission("pixelcore.spawn")) {
			player.sendMessage(messages.getString("no-permission"));
			return true;
		}

		Location spawnLocation = getSpawnLocation();
		if (spawnLocation == null) {
			player.sendMessage(messages.getString("spawn-command.not-set"));
			return true;
		}

		player.teleport(spawnLocation);
		player.sendMessage(messages.getString("spawn-command.teleport"));

		return true;
	}
}
