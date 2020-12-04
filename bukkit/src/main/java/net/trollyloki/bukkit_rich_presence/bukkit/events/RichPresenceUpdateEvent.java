package net.trollyloki.bukkit_rich_presence.bukkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import net.trollyloki.bukkit_rich_presence.core.Activity;

public class RichPresenceUpdateEvent extends CancellablePlayerEvent {

	private final Activity activity;

	public RichPresenceUpdateEvent(Player who, Activity activity) {
		super(who);
		this.activity = activity;
	}

	public Activity getActivity() {
		return activity;
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
