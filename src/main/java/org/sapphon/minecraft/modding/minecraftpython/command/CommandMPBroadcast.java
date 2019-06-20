package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;

public class CommandMPBroadcast extends CommandMPServer {

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
		return CommandMPServer.BROADCAST_NAME + CommandMPAbstract.SERIAL_DIV + toBroadcast;
	}


}
