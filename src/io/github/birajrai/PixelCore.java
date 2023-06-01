package io.github.birajrai;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.birajrai.commands.admin.FeedCommand;
import io.github.birajrai.commands.admin.FlyCommand;
import io.github.birajrai.commands.admin.ItemCommand;

public class PixelCore extends JavaPlugin {

	@Override
	public void onEnable() {
		// Register the commands
		getCommand("i").setExecutor(new ItemCommand());
		getCommand("feed").setExecutor(new FeedCommand());
		getCommand("fly").setExecutor(new FlyCommand(this));

		// Log a message to the console
		getLogger().info("PixelCore has been enabled!");
	}

	@Override
	public void onDisable() {
		// Log a message to the console
		getLogger().info("PixelCore has been disabled!");
	}
}
