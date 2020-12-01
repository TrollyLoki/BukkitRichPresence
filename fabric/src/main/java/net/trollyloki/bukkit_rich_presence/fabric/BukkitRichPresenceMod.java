package net.trollyloki.bukkit_rich_presence.fabric;

import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.fabricmc.api.ClientModInitializer;

public class BukkitRichPresenceMod implements ClientModInitializer {

	public static final Logger LOGGER = LogManager.getLogger();

	public static BukkitRichPresenceMod instance;

	public static BukkitRichPresenceMod getInstance() {
		return instance;
	}

	private String version;
	private DiscordGameSDK discordGameSDK;
	private PacketManager manager;

	@Override
	public void onInitializeClient() {
		instance = this;

		try (InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream("/fabric.mod.json"))) {
			JsonObject json = new Gson().fromJson(reader, JsonObject.class);
			version = json.get("version").getAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (!DiscordGameSDK.init()) {
			LOGGER.error("Failed to initialize Discord Game SDK");
		} else {
			discordGameSDK = new DiscordGameSDK();
			manager = new PacketManager(this);
		}

	}

	public String getVersion() {
		return version;
	}

	public DiscordGameSDK getDiscordGameSDK() {
		return discordGameSDK;
	}

	public PacketManager getManager() {
		return manager;
	}

}
