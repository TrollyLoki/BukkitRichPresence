package net.trollyloki.bukkit_rich_presence.bukkit;

import java.nio.ByteBuffer;

import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class PluginMessageManager implements PluginMessageListener {

	public static final String CLIENT_ID_REQUEST_PACKET_ID = "bukkit_rich_presence:request_client_id";
	public static final String CLIENT_ID_PACKET_ID = "bukkit_rich_presence:client_id";
	public static final String ACTIVITY_UPDATE_PACKET_ID = "bukkit_rich_presence:update_activity";

	private final BukkitRichPresencePlugin plugin;

	public PluginMessageManager(BukkitRichPresencePlugin plugin) {
		this.plugin = plugin;
		plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, CLIENT_ID_REQUEST_PACKET_ID, this);
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, CLIENT_ID_PACKET_ID);
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, ACTIVITY_UPDATE_PACKET_ID);
	}

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		System.out.println(player.getName() + " on " + channel);
		if (channel.equals(CLIENT_ID_REQUEST_PACKET_ID.toString())) {
			sendClientId(player);
		}
	}

	public void sendClientId(Player player) {
		ByteBuffer buffer = ByteBuffer.allocate(8);
		buffer.putLong(plugin.getConfig().getLong("client_id"));
		player.sendPluginMessage(plugin, CLIENT_ID_PACKET_ID, buffer.array());
	}

	public void updateActivity(Player player, byte[] data) {
		player.sendPluginMessage(plugin, ACTIVITY_UPDATE_PACKET_ID, data);
	}

}
