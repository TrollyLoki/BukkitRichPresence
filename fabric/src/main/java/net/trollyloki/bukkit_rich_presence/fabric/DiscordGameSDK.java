package net.trollyloki.bukkit_rich_presence.fabric;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.SystemUtils;

import de.jcm.discordgamesdk.Core;
import de.jcm.discordgamesdk.CreateParams;
import de.jcm.discordgamesdk.activity.Activity;
import de.jcm.discordgamesdk.activity.ActivityType;
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

	public boolean updateActivity(net.trollyloki.bukkit_rich_presence.core.Activity activity) {
		if (!hasCore())
			return false;

		try (Activity sdkActivity = new Activity()) {

			if (activity.getType() != null)
				sdkActivity.setType(toSDK(activity.getType()));
			if (activity.getDetails() != null)
				sdkActivity.setDetails(activity.getDetails());
			if (activity.getState() != null)
				sdkActivity.setState(activity.getState());

			if (activity.getLargeImage() != null)
				sdkActivity.assets().setLargeImage(activity.getLargeImage());
			if (activity.getLargeImageText() != null)
				sdkActivity.assets().setLargeText(activity.getLargeImageText());
			if (activity.getSmallImage() != null)
				sdkActivity.assets().setSmallImage(activity.getSmallImage());
			if (activity.getSmallImageText() != null)
				sdkActivity.assets().setSmallText(activity.getSmallImageText());

			if (activity.getStart() != null)
				sdkActivity.timestamps().setStart(activity.getStart());
			if (activity.getEnd() != null)
				sdkActivity.timestamps().setEnd(activity.getEnd());

			if (activity.getPartyId() != null)
				sdkActivity.party().setID(activity.getPartyId());
			if (activity.getPartyMaxSize() != 0) {
				sdkActivity.party().size().setCurrentSize(activity.getPartySize());
				sdkActivity.party().size().setMaxSize(activity.getPartyMaxSize());
			}

			if (activity.getJoinSecret() != null)
				sdkActivity.secrets().setJoinSecret(activity.getJoinSecret());
			if (activity.getMatchSecret() != null)
				sdkActivity.secrets().setMatchSecret(activity.getMatchSecret());
			if (activity.getSpectateSecret() != null)
				sdkActivity.secrets().setSpectateSecret(activity.getSpectateSecret());

			core.activityManager().updateActivity(sdkActivity);
			return true;
		}
	}

	public boolean clearActivity() {
		if (!hasCore())
			return false;

		core.activityManager().clearActivity();
		return true;
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

	public static ActivityType toSDK(net.trollyloki.bukkit_rich_presence.core.Activity.Type type) {
		return ActivityType.valueOf(type.name());
	}

}
