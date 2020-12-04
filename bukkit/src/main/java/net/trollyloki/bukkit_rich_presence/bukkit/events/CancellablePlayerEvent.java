package net.trollyloki.bukkit_rich_presence.bukkit.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

public abstract class CancellablePlayerEvent extends PlayerEvent implements Cancellable {

	private boolean cancelled = false;

	public CancellablePlayerEvent(Player who) {
		super(who);
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

}
