package net.trollyloki.bukkit_rich_presence.fabric.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.trollyloki.bukkit_rich_presence.fabric.BukkitRichPresenceMod;

@Mixin(MinecraftClient.class)
public class MinecraftClientMixin {

	@Inject(at = @At("RETURN"), method = "disconnect(Lnet/minecraft/client/gui/screen/Screen;)V")
	public void disconnectAfter(Screen screen, CallbackInfo info) {
		BukkitRichPresenceMod.getInstance().getDiscordGameSDK().closeCore();
	}

}
