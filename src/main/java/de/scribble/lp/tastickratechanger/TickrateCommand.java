package de.scribble.lp.tastickratechanger;

import java.util.Collection;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;

import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.MessageType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class TickrateCommand {
	public TickrateCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
		dispatcher.register(CommandManager.literal("tickrate").requires((serverCommandSource) -> {
	         return serverCommandSource.hasPermissionLevel(2);
	      }).
				then(CommandManager.argument("ticksPerSecond", FloatArgumentType.floatArg(0)).
					executes(source-> {
						source.getSource().getPlayer().sendMessage(new TranslatableText("Setting Tickrate to "+Float.toString(FloatArgumentType.getFloat(source, "ticksPerSecond")), new Object()), MessageType.SYSTEM, null);
						setTickrate(FloatArgumentType.getFloat(source, "ticksPerSecond"));
						return 0;
				}).then(CommandManager.literal("client").executes((source)->{
					source.getSource().getPlayer().sendMessage(new TranslatableText("Setting Client Tickrate to "+Float.toString(FloatArgumentType.getFloat(source, "ticksPerSecond")), new Object()), MessageType.SYSTEM, null);
					setTickrateClient(FloatArgumentType.getFloat(source, "ticksPerSecond"));
					return 0;
				})).then(CommandManager.literal("server").executes((source)->{
					source.getSource().getPlayer().sendMessage(new TranslatableText("Setting Server Tickrate to "+Float.toString(FloatArgumentType.getFloat(source, "ticksPerSecond")), new Object()), MessageType.SYSTEM, null);
					setTickrateServer(FloatArgumentType.getFloat(source, "ticksPerSecond"));
					return 0;
				})).then(CommandManager.argument("targets", EntityArgumentType.players()).executes((source)->{
					source.getSource().getPlayer().sendMessage(new TranslatableText("Setting Server Tickrate to "+Float.toString(FloatArgumentType.getFloat(source, "ticksPerSecond"))+" for players", new Object()), MessageType.SYSTEM, null);
					setTickratePlayer(FloatArgumentType.getFloat(source, "ticksPerSecond"), EntityArgumentType.getPlayers(source, "targets"));
					return 0;
				}))
			));
	}
	
	private void setTickratePlayer(float tickrate, Collection<ServerPlayerEntity> players) {
		players.forEach(player->{
			TickrateChangerServer.getInstance().updatePlayerTickrate(player, tickrate);
		});
	}

	private void setTickrateServer(float tickrate) {
		TickrateChangerServer.getInstance().updateServerTickrate(tickrate);
	}
	public void setTickrate(float tickrate) {
		ModLoader.cfg.setValue(tickrate);
		TickrateChangerServer.getInstance().updateTickrate(tickrate);
	}
	public void setTickrateClient(float tickrate) {
		TickrateChangerServer.getInstance().updateAllClientTickrate(tickrate);
	}
}
