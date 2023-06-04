package io.github.birajrai;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.birajrai.commands.admin.FeedCommand;
import io.github.birajrai.commands.admin.FlyCommand;
import io.github.birajrai.commands.admin.ItemCommand;
import io.github.birajrai.commands.admin.SetSpawnCommand;
import io.github.birajrai.commands.admin.SkinCommand;
import io.github.birajrai.commands.global.SpawnCommand;

public class PixelCore extends JavaPlugin {
	private FileConfiguration config;
	private FileConfiguration messages;
	private FileConfiguration data;
	private FileConfiguration skinData;
	private File dataFile;
	private File skinDataFile;

	private Location spawnLocation;

	public Location getSpawnLocation() {
		return spawnLocation;
	}

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

	private void loadData() {
		dataFile = new File(getDataFolder(), "data.json");
		if (!dataFile.exists()) {
			try {
				dataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		data = YamlConfiguration.loadConfiguration(dataFile);

		if (data.contains("spawnLocation")) {
			double x = data.getDouble("spawnLocation.x");
			double y = data.getDouble("spawnLocation.y");
			double z = data.getDouble("spawnLocation.z");
			float yaw = (float) data.getDouble("spawnLocation.yaw");
			float pitch = (float) data.getDouble("spawnLocation.pitch");
			spawnLocation = new Location(Bukkit.getWorlds().get(0), x, y, z, yaw, pitch);
		}
	}

	private void loadSkinData() {
		skinDataFile = new File(getDataFolder(), "skindata.json");
		if (!skinDataFile.exists()) {
			try {
				skinDataFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		skinData = YamlConfiguration.loadConfiguration(skinDataFile);
	}

	@Override
	public void onDisable() {
		saveData();
		saveSkinData();
	}

	@Override
	public void onEnable() {
		// Load config.yml and messages.yml
		loadConfig();

		// Load data.json
		loadData();

		// Load skindata.json
		loadSkinData();

		// Register commands
		registerCommands();
	}

	private void registerCommands() {
		// Register admin commands
		getCommand("feed").setExecutor(new FeedCommand(config, messages));
		getCommand("fly").setExecutor(new FlyCommand(config, messages));
		getCommand("item").setExecutor(new ItemCommand(config, messages));
		getCommand("setspawn").setExecutor(new SetSpawnCommand(config, messages, this));

		// Register general commands
		getCommand("spawn").setExecutor(new SpawnCommand(messages, this));

		// Register skin command
		getCommand("skin").setExecutor(new SkinCommand(config, messages, skinData, this));
	}

	private void saveData() {
		if (spawnLocation != null) {
			data.set("spawnLocation.x", spawnLocation.getX());
			data.set("spawnLocation.y", spawnLocation.getY());
			data.set("spawnLocation.z", spawnLocation.getZ());
			data.set("spawnLocation.yaw", spawnLocation.getYaw());
			data.set("spawnLocation.pitch", spawnLocation.getPitch());
		}

		try {
			data.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void saveSkinData() {
		try {
			skinData.save(skinDataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setSpawnLocation(Location location) {
		this.spawnLocation = location;
	}
}
