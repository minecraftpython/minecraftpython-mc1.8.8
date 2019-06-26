package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class CommandMPBroadcast extends CommandMinecraftPythonServer {

	public String toBroadcast;

	public CommandMPBroadcast(String toBroadcast) {
		this.toBroadcast=toBroadcast;
	}

	public CommandMPBroadcast(String[] commandAndArgsToDeserialize) {
		toBroadcast=commandAndArgsToDeserialize[1];
	}

	@Override
	public void doWork() {
		MinecraftServer.getServer().getConfigurationManager().sendChatMsg(new ChatComponentText(this.toBroadcast));
	}

	@Override
	public String serialize() {
		return CommandMinecraftPythonServer.BROADCAST_NAME + CommandMinecraftPythonAbstract.SERIAL_DIV + toBroadcast;
	}


}
