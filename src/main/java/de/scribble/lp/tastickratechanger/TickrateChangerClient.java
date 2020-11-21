package de.scribble.lp.tastickratechanger;

import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.scribble.lp.tastickratechanger.quack.ClientDuck;
import net.minecraft.client.MinecraftClient;

public class TickrateChangerClient {
	
    private static TickrateChangerClient INSTANCE;
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

    
	public TickrateChangerClient() {
        INSTANCE = this;
    }
	
	public void updateClientTickrate(float tickrate, boolean log) {
		MinecraftClient mc = MinecraftClient.getInstance();
		if(log) LOGGER.info("Updating client tickrate to " + tickrate);

        TICKS_PER_SECOND = tickrate;
        
        if(CHANGE_SOUND) GAME_SPEED = tickrate / 20F;
        
		((ClientDuck)mc).setTickrate(tickrate);
	}
	
	public static TickrateChangerClient getInstance() {
		return INSTANCE;
	}
}
