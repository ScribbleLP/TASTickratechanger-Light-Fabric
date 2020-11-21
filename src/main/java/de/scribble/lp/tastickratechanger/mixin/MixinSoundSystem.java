package de.scribble.lp.tastickratechanger.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import de.scribble.lp.tastickratechanger.TickrateChangerServer;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.util.math.MathHelper;

@Mixin(SoundSystem.class)
public class MixinSoundSystem {
	@Inject(method = "getAdjustedPitch", at = @At("HEAD"), cancellable = true)
	private void redogetAdjustedPitch(SoundInstance soundInstance, CallbackInfoReturnable<Float> ci) {
	      ci.setReturnValue(TickrateChangerServer.GAME_SPEED*MathHelper.clamp(soundInstance.getPitch(), 0.5F, 2.0F));
	      ci.cancel();
	   }
}
