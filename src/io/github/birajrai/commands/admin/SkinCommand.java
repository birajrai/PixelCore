package io.github.birajrai.commands.admin;

import java.io.File;
import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SkinCommand implements CommandExecutor {

    private final FileConfiguration messages;
    private final FileConfiguration skinData;
    private final Plugin plugin;

    public SkinCommand(FileConfiguration config, FileConfiguration messages, FileConfiguration skinData, Plugin plugin) {
        this.messages = messages;
        this.skinData = skinData;
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(messages.getString("skin-command.only-player"));
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("pixelcore.admin.skin")) {
            player.sendMessage(messages.getString("no-permission"));
            return true;
        }

        if (args.length < 1) {
            player.sendMessage(messages.getString("skin-command.usage"));
            return true;
        }

        String playerName = player.getName();
        String skinValue = "";
        String skinSignature = "";

        if (args[0].equalsIgnoreCase("url")) {
            if (args.length < 2) {
                player.sendMessage(messages.getString("skin-command.url-usage"));
                return true;
            }

            skinValue = args[1];
            skinSignature = "";

        } else {
            // Fetch the skin value and signature from the NameMC API
            // Code to fetch skin from NameMC API goes here

            // Example:
            // skinValue = "skinValueFromAPI";
            // skinSignature = "skinSignatureFromAPI";
        }

        skinData.set(playerName + ".value", skinValue);
        skinData.set(playerName + ".signature", skinSignature);

        try {
            File skinDataFile = new File(plugin.getDataFolder(), "skindata.yml");
            skinData.save(skinDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.sendMessage(messages.getString("skin-command.success"));

        return true;
    }
}
