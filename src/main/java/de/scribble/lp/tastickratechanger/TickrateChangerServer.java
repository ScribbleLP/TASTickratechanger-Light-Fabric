package de.scribble.lp.tastickratechanger;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.network.ServerSidePacketRegistry;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;

public class TickrateChangerServer {
	
    private static TickrateChangerServer INSTANCE;
    public static Logger LOGGER = LogManager.getLogger("TASTickrateChanger");
    public static final String NETWORK_VERSION = "1";
    public static TickrateCommand COMMAND = null;
    public static File CONFIG_FILE = null;

    public static final String MODID = "tickratechanger";
    public static final String VERSION = "2.0";

    public static final String GAME_RULE = "tickrate";

    // Default tickrate - can be changed in the config file
    public static float DEFAULT_TICKRATE = 20;
    // Stored client-side tickrate
    public static float TICKS_PER_SECOND = 20;
    // Server-side tickrate in miliseconds
    public static long MILISECONDS_PER_TICK = 50L;
    // Sound speed
    public static float GAME_SPEED = 1;
    // Min Tickrate
    public static float MIN_TICKRATE = 0.1F;
    // Max Tickrate
    public static float MAX_TICKRATE = 1000;
    // Show Messages
    public static boolean SHOW_MESSAGES = true;
    // Change sound speed
    public static boolean CHANGE_SOUND = true;

    
	public TickrateChangerServer() {
        INSTANCE = this;
    }
	public void updateTickrate(float tickrate) {
		updateServerTickrate(tickrate);
		
		updateAllClientTickrate(tickrate);
	}
    public void updateServerTickrate(float tickrate) {
        TickrateChangerServer.MILISECONDS_PER_TICK=(long) Math.floor(1000F/tickrate);
    }
	
	public static TickrateChangerServer getInstance() {
		return INSTANCE;
	}
	public void updateAllClientTickrate(float tickrate) {
		PacketByteBuf passedData= new PacketByteBuf(Unpooled.buffer());
		passedData.writeFloat(tickrate);
		ModLoader.serverInstance.getPlayerManager().getPlayerList().forEach((players)->{
			ServerSidePacketRegistry.INSTANCE.sendToPlayer(players, ModLoader.tickratePacketClient, passedData);
		});
	}
	public void updatePlayerTickrate(ServerPlayerEntity player, float tickrate) {
		PacketByteBuf passedData= new PacketByteBuf(Unpooled.buffer());
		passedData.writeFloat(tickrate);
		ServerSidePacketRegistry.INSTANCE.sendToPlayer(player, ModLoader.tickratePacketClient, passedData);
	}
}
