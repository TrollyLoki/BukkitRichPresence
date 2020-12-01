package net.trollyloki.bukkit_rich_presence.bukkit;

import java.nio.ByteBuffer;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import net.trollyloki.bukkit_rich_presence.core.Constants;

public class PluginMessageManager implements PluginMessageListener {

	private final BukkitRichPresencePlugin plugin;

	public PluginMessageManager(BukkitRichPresencePlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, Constants.CLIENT_ID_REQUEST_PACKET_ID,
				this);
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, Constants.CLIENT_ID_PACKET_ID);
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, Constants.ACTIVITY_UPDATE_PACKET_ID);
	}

	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		System.out.println(player.getName() + " on " + channel);
		if (channel.equals(Constants.CLIENT_ID_REQUEST_PACKET_ID.toString())) {
			sendClientId(player);
		}
	}

	public void sendClientId(Player player) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(plugin.getConfig().getLong("client_id"));
		player.sendPluginMessage(plugin, Constants.CLIENT_ID_PACKET_ID, buffer.array());
	}

	public void updateActivity(Player player, byte[] data) {
		player.sendPluginMessage(plugin, Constants.ACTIVITY_UPDATE_PACKET_ID, data);
	}

}
