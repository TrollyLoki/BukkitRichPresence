package net.trollyloki.bukkit_rich_presence.bukkit;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.bukkit.entity.Player;

import net.trollyloki.bukkit_rich_presence.core.Activity;
import net.trollyloki.bukkit_rich_presence.core.Constants;

public class RichPresenceManager {

	private static RichPresenceManager instance;

	public static RichPresenceManager getInstance() {
		return instance;
	}

	private final BukkitRichPresencePlugin plugin;

	public RichPresenceManager(BukkitRichPresencePlugin plugin) {
		this.plugin = plugin;

		plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, Constants.CLIENT_ID_REQUEST_PACKET_ID,
				(channel, player, message) -> sendClientId(player));
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, Constants.CLIENT_ID_PACKET_ID);
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, Constants.ACTIVITY_UPDATE_PACKET_ID);

		instance = this;
	}

	public void sendClientId(Player player) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(plugin.getConfig().getLong("client_id"));
		player.sendPluginMessage(plugin, Constants.CLIENT_ID_PACKET_ID, buffer.array());
	}

	public void updateActivity(Player player, Activity activity) throws IOException {
		player.sendPluginMessage(plugin, Constants.ACTIVITY_UPDATE_PACKET_ID, activity.serialize());
	}

	public void clearActivity(Player player) {
		player.sendPluginMessage(plugin, Constants.ACTIVITY_UPDATE_PACKET_ID, new byte[0]);
	}

}
