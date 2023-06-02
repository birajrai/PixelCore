package io.github.birajrai;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.birajrai.commands.admin.FeedCommand;
import io.github.birajrai.commands.admin.FlyCommand;
import io.github.birajrai.commands.admin.ItemCommand;

public class PixelCore extends JavaPlugin {

	private FileConfiguration config;
	private FileConfiguration messages;

	private void loadConfig() {
		// Load config.yml
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();
		config = getConfig();

		// Load messages.yml
		File messagesFile = new File(getDataFolder(), "messages.yml");
		if (!messagesFile.exists()) {
			saveResource("messages.yml", false);
		}
		messages = YamlConfiguration.loadConfiguration(messagesFile);
	}

	@Override
	public void onEnable() {
		// Load config.yml and messages.yml
		loadConfig();

		// Register commands
		registerCommands();
	}

	private void registerCommands() {
		// Register the commands with their respective executors
		getCommand("feed").setExecutor(new FeedCommand(config, messages));
		getCommand("fly").setExecutor(new FlyCommand(config, messages));
		getCommand("i").setExecutor(new ItemCommand(config, messages));
	}
}
