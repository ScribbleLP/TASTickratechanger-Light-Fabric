package de.scribble.lp.tastickratechanger.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

import de.scribble.lp.tastickratechanger.quack.ClientDuck;
import net.minecraft.client.render.RenderTickCounter;

@Mixin(RenderTickCounter.class)
public class MixinRenderTickCounter implements ClientDuck{
	
	@Mutable @Shadow @Final
	float tickTime;
	
	@Override
	public float getTickrate() {
		return 1000 / tickTime;
	}

	@Override
	public void setTickrate(float tickrate) {
		this.tickTime = 1000f / tickrate;
	}
	
}
