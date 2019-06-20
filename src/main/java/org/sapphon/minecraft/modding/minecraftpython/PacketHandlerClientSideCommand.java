package org.sapphon.minecraft.modding.minecraftpython;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import org.sapphon.minecraft.modding.minecraftpython.command.CommandQueueClientSide;

public class PacketHandlerClientSideCommand implements IMessageHandler<PacketClientSideCommand, IMessage> {

	@Override
	public IMessage onMessage(PacketClientSideCommand message,
			MessageContext ctx) {
		CommandQueueClientSide.SINGLETON().scheduleCommand(message.getCommand());
		return null;
	}

}
