package net.trollyloki.bukkit_rich_presence.fabric;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

public class PacketManager {

	public static final String CLIENT_ID_REQUEST_PACKET_ID = "bukkit_rich_presence:request_client_id";
	public static final String CLIENT_ID_PACKET_ID = "bukkit_rich_presence:client_id";
	public static final String ACTIVITY_UPDATE_PACKET_ID = "bukkit_rich_presence:update_activity";

	private final BukkitRichPresenceMod mod;
	private PacketByteBuf tempData;

	public PacketManager(BukkitRichPresenceMod mod) {
		this.mod = mod;

		ClientTickEvents.END_CLIENT_TICK.register(client -> mod.getDiscordGameSDK().runCallbacks());
		ClientLifecycleEvents.CLIENT_STOPPING.register(client -> mod.getDiscordGameSDK().closeCore());

		ClientSidePacketRegistry.INSTANCE.register(new Identifier(CLIENT_ID_PACKET_ID), (context, data) -> {
			mod.getDiscordGameSDK().newCore(data.readLong());
			if (tempData != null) {
				parseActivityUpdate(tempData);
				tempData = null;
			}
		});

		ClientSidePacketRegistry.INSTANCE.register(new Identifier(ACTIVITY_UPDATE_PACKET_ID), (context, data) -> {
			if (mod.getDiscordGameSDK().hasCore()) {
				parseActivityUpdate(data);
			} else {
				tempData = data;
				requestClientId();
			}
		});

	}

	public void parseActivityUpdate(PacketByteBuf data) {

	}

	public void requestClientId() {
		PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
		buf.writeString(mod.getVersion());
		ClientSidePacketRegistry.INSTANCE.sendToServer(new Identifier(CLIENT_ID_REQUEST_PACKET_ID), buf);
	}

}
