package net.trollyloki.bukkit_rich_presence.fabric;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.trollyloki.bukkit_rich_presence.core.Activity;
import net.trollyloki.bukkit_rich_presence.core.Constants;

public class PacketManager {

	private final BukkitRichPresenceMod mod;
	private PacketByteBuf tempData;

	public PacketManager(BukkitRichPresenceMod mod) {
		this.mod = mod;

		ClientTickEvents.END_CLIENT_TICK.register(client -> mod.getDiscordGameSDK().runCallbacks());
		ClientLifecycleEvents.CLIENT_STOPPING.register(client -> mod.getDiscordGameSDK().closeCore());

		ClientSidePacketRegistry.INSTANCE.register(new Identifier(Constants.CLIENT_ID_PACKET_ID), (context, data) -> {
			mod.getDiscordGameSDK().newCore(data.readLong());
			if (tempData != null) {
				try {
					parseActivityUpdate(tempData);
				} catch (IOException e) {
					e.printStackTrace();
				}
				tempData = null;
			}
		});

		ClientSidePacketRegistry.INSTANCE.register(new Identifier(Constants.ACTIVITY_UPDATE_PACKET_ID),
				(context, data) -> {
					if (mod.getDiscordGameSDK().hasCore()) {
						try {
							parseActivityUpdate(data);
						} catch (IOException e) {
							e.printStackTrace();
						}
					} else {
						tempData = data;
						requestClientId();
					}
				});

	}

	public void parseActivityUpdate(PacketByteBuf data) throws IOException {
		if (data.isReadable()) {
			byte[] array = new byte[data.capacity()];
			data.getBytes(0, array);
			mod.getDiscordGameSDK().updateActivity(new Activity(array));
		} else {
			mod.getDiscordGameSDK().clearActivity();
		}
	}

	public void requestClientId() {
		ClientSidePacketRegistry.INSTANCE.sendToServer(new Identifier(Constants.CLIENT_ID_REQUEST_PACKET_ID),
				new PacketByteBuf(Unpooled.wrappedBuffer(BukkitRichPresenceMod.VERSION.getBytes(StandardCharsets.UTF_8))));
	}

}
