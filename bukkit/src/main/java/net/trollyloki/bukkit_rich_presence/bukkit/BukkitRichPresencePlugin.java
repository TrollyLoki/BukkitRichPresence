package net.trollyloki.bukkit_rich_presence.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class BukkitRichPresencePlugin extends JavaPlugin {

	private RichPresenceManager manager;

	@Override
	public void onEnable() {
		saveDefaultConfig();

		manager = new RichPresenceManager(this);
	}

	@Override
	public void onDisable() {

	}

	public RichPresenceManager getManager() {
		return manager;
	}

}
