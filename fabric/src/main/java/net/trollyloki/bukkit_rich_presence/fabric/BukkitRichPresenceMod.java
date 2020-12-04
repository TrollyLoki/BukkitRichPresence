package net.trollyloki.bukkit_rich_presence.fabric;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;

public class BukkitRichPresenceMod implements ClientModInitializer {

	public static final Logger LOGGER = LogManager.getLogger();
	public static final String VERSION = "0.1.1";

	public static BukkitRichPresenceMod instance;

	public static BukkitRichPresenceMod getInstance() {
		return instance;
	}

	private DiscordGameSDK discordGameSDK;
	private PacketManager manager;

	@Override
	public void onInitializeClient() {
		instance = this;

		if (!DiscordGameSDK.init()) {
			LOGGER.error("Failed to initialize Discord Game SDK");
		} else {
			discordGameSDK = new DiscordGameSDK();
			manager = new PacketManager(this);
		}

	}

	public DiscordGameSDK getDiscordGameSDK() {
		return discordGameSDK;
	}

	public PacketManager getManager() {
		return manager;
	}

}
