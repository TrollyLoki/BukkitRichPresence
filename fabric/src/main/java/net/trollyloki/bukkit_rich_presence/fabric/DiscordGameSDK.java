package net.trollyloki.bukkit_rich_presence.fabric;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

public class DiscordGameSDK {

	private Core core;

	public boolean hasCore() {
		return core != null;
	}

	public void newCore(long clientId) {
		closeCore();

		CreateParams params = new CreateParams();
		params.setClientID(clientId);
		params.setFlags(CreateParams.getDefaultFlags());
		core = new Core(params);
	}

	public boolean runCallbacks() {
		if (hasCore()) {
			core.runCallbacks();
			return true;
		}
		return false;
	}

	public boolean closeCore() {
		if (hasCore()) {
			core.close();
			core = null;
			return true;
		}
		return false;
	}

	public static boolean init() {

		String path = "/discord_game_sdk/x86";
		if (SystemUtils.IS_OS_WINDOWS) { // Windows
			if (System.getenv("ProgramFiles(x86)") != null) // 64 bit
				path += "_64";
			path += "/discord_game_sdk.dll";
		} else { // Linux
			path += "_64/discord_game_sdk.so";
		}

		try {

			File tempFile = new File(
					System.getProperty("java.io.tmpdir") + File.separator + path.substring(path.lastIndexOf('/') + 1));
			BukkitRichPresenceMod.LOGGER.debug("Extracting \"" + path + "\" to \"" + tempFile.getPath() + "\"...");
			tempFile.deleteOnExit();
			ClientLifecycleEvents.CLIENT_STOPPING.register(client -> tempFile.delete());

			try (InputStream in = BukkitRichPresenceMod.class.getResourceAsStream(path);
					FileOutputStream out = new FileOutputStream(tempFile)) {
				IOUtils.copy(in, out);
				out.flush();
			}

			Core.init(tempFile);
			return true;

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

}
