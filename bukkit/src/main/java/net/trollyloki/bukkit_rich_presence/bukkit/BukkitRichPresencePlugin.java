package net.trollyloki.bukkit_rich_presence.bukkit;

import org.bukkit.plugin.java.JavaPlugin;

public class BukkitRichPresencePlugin extends JavaPlugin {

	private PluginMessageManager manager;

	@Override
	public void onEnable() {
		saveDefaultConfig();

		manager = new PluginMessageManager(this);
	}

	@Override
	public void onDisable() {

	}

	public PluginMessageManager getManager() {
		return manager;
	}

}
