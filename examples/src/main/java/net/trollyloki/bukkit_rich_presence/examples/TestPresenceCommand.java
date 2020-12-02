package net.trollyloki.bukkit_rich_presence.examples;

import java.io.IOException;
import java.time.Instant;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.trollyloki.bukkit_rich_presence.bukkit.RichPresenceManager;
import net.trollyloki.bukkit_rich_presence.core.Activity;

public class TestPresenceCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;

			Activity activity = new Activity("Testing Presence", "In-game", "example_image",
					"Hello World!", "trollyloki", "TrollyLoki", Instant.now());

			try {
				RichPresenceManager.getInstance().updateActivity(player, activity);
				sender.sendMessage(ChatColor.GREEN + "Successfully updated your Rich Presence");
			} catch (IOException e) {
				sender.sendMessage(ChatColor.RED + "Failed to update your Rich Presence");
				e.printStackTrace();
			}
			return true;

		}
		return false;
	}

}
