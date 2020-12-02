package net.trollyloki.bukkit_rich_presence.examples;

import org.bukkit.plugin.java.JavaPlugin;

public class BukkitRichPresenceExamplesPlugin extends JavaPlugin {

	@Override
	public void onEnable() {
		getCommand("test-presence").setExecutor(new TestPresenceCommand());
	}

	@Override
	public void onDisable() {

	}

}
