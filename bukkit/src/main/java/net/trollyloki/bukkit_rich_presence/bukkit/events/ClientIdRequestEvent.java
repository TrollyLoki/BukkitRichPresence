package net.trollyloki.bukkit_rich_presence.bukkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ClientIdRequestEvent extends PlayerEvent {

	private final String modVersion;

	public ClientIdRequestEvent(Player who, String modVersion) {
		super(who);
		this.modVersion = modVersion;
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
