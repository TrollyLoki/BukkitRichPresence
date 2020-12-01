package net.trollyloki.bukkit_rich_presence.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.trollyloki.bukkit_rich_presence.fabric.BukkitRichPresenceMod;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

	@Inject(at = @At("TAIL"), method = "onGameJoin(Lnet/minecraft/network/packet/s2c/play/GameJoinS2CPacket;)V")
	private void onGameJoin(GameJoinS2CPacket packet, CallbackInfo info) {
		BukkitRichPresenceMod.getInstance().getManager().requestClientId();
	}

}
