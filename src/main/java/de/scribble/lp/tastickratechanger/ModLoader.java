package de.scribble.lp.tastickratechanger;

import java.io.File;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.network.ClientSidePacketRegistry;
import net.fabricmc.loader.FabricLoader;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;

public class ModLoader implements ModInitializer, ClientModInitializer{

    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "tastickratechanger";
    public static final String MOD_NAME = "TASTickratechanger";
    
    public static MinecraftServer serverInstance;
    
    public static Config cfg;

    public static final Identifier tickratePacketClient= new Identifier("tickratechanger", "tickrate");
    
    @SuppressWarnings("deprecation")
	@Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        new TickrateChangerServer();
        CommandRegistrationCallback.EVENT.register((dispatcher,dedicated)->new TickrateCommand(dispatcher));
        
		cfg= new Config(new File(FabricLoader.INSTANCE.getConfigDirectory()+File.separator+"tastickratechanger.cfg"));
		
		
        TickrateChangerServer.DEFAULT_TICKRATE=cfg.getValue();
        ServerLifecycleEvents.SERVER_STARTED.register(server ->{
        	serverInstance=server;
        	TickrateChangerServer.DEFAULT_TICKRATE=cfg.getValue();
    		TickrateChangerServer.getInstance().updateServerTickrate(TickrateChangerServer.DEFAULT_TICKRATE);
        });
    }

    public static void log(Level level, String message){
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

	@Override
	public void onInitializeClient() {
		new TickrateChangerClient();
		TickrateChangerClient.getInstance().updateClientTickrate(20F, false);
		
		
		ClientSidePacketRegistry.INSTANCE.register(tickratePacketClient, (packetContext, attachedData)-> {
			//Network thread
			float tickrate=attachedData.readFloat();
			packetContext.getTaskQueue().execute(()->{
			//Main thread
				TickrateChangerClient.getInstance().updateClientTickrate(tickrate, false);
			});
		});
	}
	
}