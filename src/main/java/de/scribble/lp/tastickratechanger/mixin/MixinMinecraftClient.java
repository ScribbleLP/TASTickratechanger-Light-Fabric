package de.scribble.lp.tastickratechanger.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import de.scribble.lp.tastickratechanger.quack.ClientDuck;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderTickCounter;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient implements ClientDuck{

	@Shadow @Final
	private RenderTickCounter renderTickCounter;

	@Override
	public float getTickrate() {
		return ((ClientDuck)renderTickCounter).getTickrate();
	}

	@Override
	public void setTickrate(float tickrate) {
		((ClientDuck)renderTickCounter).setTickrate(tickrate);
	}
	
}
