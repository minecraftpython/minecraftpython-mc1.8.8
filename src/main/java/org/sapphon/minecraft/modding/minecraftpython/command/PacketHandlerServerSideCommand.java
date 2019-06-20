package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketHandlerServerSideCommand implements
		IMessageHandler<IMessage, IMessage> {
	public PacketHandlerServerSideCommand(){
		
	}
	@Override
	public IMessage onMessage(PacketServerSideCommand message, MessageContext ctx) {
		CommandQueueServerSide.SINGLETON().scheduleCommand(message.command);
		return null;
	}

	@Override
	public IMessage onMessage(IMessage message, MessageContext ctx) {
		return null;
	}
}
