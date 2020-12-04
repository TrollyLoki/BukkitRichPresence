package net.trollyloki.bukkit_rich_presence.bukkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PostClientIdSendEvent extends PlayerEvent {

	private final long clientId;
	private final String modVersion;

	public PostClientIdSendEvent(Player who, long clientId, String modVersion) {
		super(who);
		this.clientId = clientId;
		this.modVersion = modVersion;
	}

	public long getClientId() {
		return clientId;
	}

	public String getModVersion() {
		return modVersion;
	}

	private static final HandlerList HANDLERS = new HandlerList();

	@Override
	public HandlerList getHandlers() {
		return HANDLERS;
	}

	public static HandlerList getHandlerList() {
		return HANDLERS;
	}

}
