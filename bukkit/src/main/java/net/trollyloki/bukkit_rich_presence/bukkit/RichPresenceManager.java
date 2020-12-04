package net.trollyloki.bukkit_rich_presence.bukkit;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import org.bukkit.entity.Player;

import net.trollyloki.bukkit_rich_presence.bukkit.events.ClientIdRequestEvent;
import net.trollyloki.bukkit_rich_presence.bukkit.events.ClientIdSendEvent;
import net.trollyloki.bukkit_rich_presence.bukkit.events.PostClientIdSendEvent;
import net.trollyloki.bukkit_rich_presence.bukkit.events.RichPresenceUpdateEvent;
import net.trollyloki.bukkit_rich_presence.core.Activity;
import net.trollyloki.bukkit_rich_presence.core.Constants;

public class RichPresenceManager {

	private static RichPresenceManager instance;

	public static RichPresenceManager getInstance() {
		return instance;
	}

	private final BukkitRichPresencePlugin plugin;

	RichPresenceManager(BukkitRichPresencePlugin plugin) {
		this.plugin = plugin;

		plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin, Constants.CLIENT_ID_REQUEST_PACKET_ID,
				(channel, player, message) -> {
					ClientIdRequestEvent event = new ClientIdRequestEvent(player,
							new String(message, StandardCharsets.UTF_8));
					plugin.getServer().getPluginManager().callEvent(event);
					sendClientId(player, event.getModVersion());
				});
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, Constants.CLIENT_ID_PACKET_ID);
		plugin.getServer().getMessenger().registerOutgoingPluginChannel(plugin, Constants.ACTIVITY_UPDATE_PACKET_ID);

		instance = this;
	}

	public void sendClientId(Player player, String modVersion) {
		long clientId = plugin.getConfig().getLong("client_id");
		ClientIdSendEvent event = new ClientIdSendEvent(player, clientId, modVersion);
		plugin.getServer().getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			player.sendPluginMessage(plugin, Constants.CLIENT_ID_PACKET_ID,
					ByteBuffer.allocate(8).putLong(event.getClientId()).array());
			plugin.getServer().getPluginManager()
					.callEvent(new PostClientIdSendEvent(player, event.getClientId(), event.getModVersion()));
		}
	}

	public void updateActivity(Player player, Activity activity) throws IOException {
		RichPresenceUpdateEvent event = new RichPresenceUpdateEvent(player, activity);
		plugin.getServer().getPluginManager().callEvent(event);
		if (!event.isCancelled())
			player.sendPluginMessage(plugin, Constants.ACTIVITY_UPDATE_PACKET_ID,
					event.getActivity() != null ? event.getActivity().serialize() : new byte[0]);
	}

	public void clearActivity(Player player) throws IOException {
		updateActivity(player, null);
	}

}
