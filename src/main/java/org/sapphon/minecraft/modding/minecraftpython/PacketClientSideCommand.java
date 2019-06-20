package org.sapphon.minecraft.modding.minecraftpython;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.sapphon.minecraft.modding.minecraftpython.command.*;

public class PacketClientSideCommand implements IMessage {

	
	private CommandMPClient command;

	public PacketClientSideCommand(CommandMPClient commandToPackUp){
		this.command = commandToPackUp;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		String text = ByteBufUtils.readUTF8String(buf);
		String[] commandAndArgsToDeserialize = text.split(CommandMPAbstract.SERIAL_DIV);
		String commandName = commandAndArgsToDeserialize[0].trim();

		if(commandName.equals(CommandMPClient.SPAWNPARTICLE_NAME)){
			command = new CommandMPSpawnParticle(commandAndArgsToDeserialize);
			
		} else if (commandName.equals(CommandMPClient.SECRETSETTINGS_NAME)) {
			command = new CommandMPApplyShader(commandAndArgsToDeserialize);
	
		} else if (commandName.equals(CommandMPClient.CHANGESETTINGS_NAME)) {
			command = new CommandMPChangeSettings(commandAndArgsToDeserialize);	
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		String serializedCommand = command.serialize();
		ByteBufUtils.writeUTF8String(buf, serializedCommand);
	}

	public CommandMPClient getCommand() {
		return command;
	}
}
