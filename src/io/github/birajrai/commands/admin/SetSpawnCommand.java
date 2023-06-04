package io.github.birajrai.commands.admin;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SetSpawnCommand implements CommandExecutor {

    private final FileConfiguration config;
    private final FileConfiguration messages;
    private final Plugin plugin;

    public SetSpawnCommand(FileConfiguration config, FileConfiguration messages, Plugin plugin) {
        this.config = config;
        this.messages = messages;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messages.getString("setspawn-command.only-player"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("pixelcore.admin.setspawn")) {
            player.sendMessage(messages.getString("no-permission"));
            return true;
        }

        Location spawnLocation = player.getLocation();
        config.set("spawnLocation.world", spawnLocation.getWorld().getName());
        config.set("spawnLocation.x", spawnLocation.getX());
        config.set("spawnLocation.y", spawnLocation.getY());
        config.set("spawnLocation.z", spawnLocation.getZ());
        config.set("spawnLocation.yaw", spawnLocation.getYaw());
        config.set("spawnLocation.pitch", spawnLocation.getPitch());
        plugin.saveConfig();

        player.sendMessage(messages.getString("setspawn-command.success"));

        return true;
    }
}
