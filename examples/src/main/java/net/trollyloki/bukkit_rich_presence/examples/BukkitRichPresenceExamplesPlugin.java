package net.trollyloki.bukkit_rich_presence.examples;

import java.io.IOException;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import net.trollyloki.bukkit_rich_presence.bukkit.RichPresenceManager;
import net.trollyloki.bukkit_rich_presence.bukkit.events.PostClientIdSendEvent;
import net.trollyloki.bukkit_rich_presence.core.Activity;

public class BukkitRichPresenceExamplesPlugin extends JavaPlugin implements Listener {

	@Override
	public void onEnable() {
		getCommand("test-presence").setExecutor(new TestPresenceCommand());
		getServer().getPluginManager().registerEvents(this, this);
	}

	@Override
	public void onDisable() {

	}

	@EventHandler
	public void onClientIdSent(PostClientIdSendEvent event) {
		Activity activity = new Activity("Using BukkitRichPresence", "version " + event.getModVersion(), "logo",
				"BukkitRichPresence");
		try {
			RichPresenceManager.getInstance().updateActivity(event.getPlayer(), activity);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
