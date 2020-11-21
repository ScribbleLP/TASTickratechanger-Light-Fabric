package de.scribble.lp.tastickratechanger.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

import de.scribble.lp.tastickratechanger.TickrateChangerServer;
import net.minecraft.server.MinecraftServer;

@Mixin(MinecraftServer.class)
public abstract class MixinMinecraftServer{

	@ModifyConstant(method = "runServer", constant = @Constant(longValue = 50L))
    private long serverTickWaitTime(long ignored) {
        return TickrateChangerServer.MILISECONDS_PER_TICK;
    }
}
