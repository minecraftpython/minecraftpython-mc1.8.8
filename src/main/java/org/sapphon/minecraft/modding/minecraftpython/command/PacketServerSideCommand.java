package org.sapphon.minecraft.modding.minecraftpython.command;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import org.sapphon.minecraft.modding.minecraftpython.problemhandlers.JavaProblemHandler;
//TODO this whole class requires a re-factor to be at all data-driven.  Right now everything is minimally automated and honestly kinda questionable technically
public class PacketServerSideCommand implements IMessage {
	

	private String commandName;
	public CommandMPServer command;

	public PacketServerSideCommand() {

	}

	public PacketServerSideCommand(CommandMPServer commandToPackUp){
		this.command = commandToPackUp;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		String text = ByteBufUtils.readUTF8String(buf);
		String[] commandAndArgsToDeserialize = text.split(CommandMPAbstract.SERIAL_DIV);
		commandName = commandAndArgsToDeserialize[0].trim();

		if(commandName.equals(CommandMPSetBlock.SETBLOCK_NAME)){
			command = new CommandMPSetBlock(commandAndArgsToDeserialize);
			
		} else if (commandName.equals(CommandMPServer.CREATEEXPLOSION_NAME)) {
			command = new CommandMPCreateExplosion(commandAndArgsToDeserialize);
	
		} else if (commandName.equals(CommandMPServer.SPAWNENTITY_NAME)) {
			command = new CommandMPSpawnEntity(commandAndArgsToDeserialize);
			
		} else if (commandName.equals(CommandMPServer.TELEPORT_NAME)) {
			command = new CommandMPTeleport(commandAndArgsToDeserialize);
			
		} else if (commandName.equals(CommandMPServer.BROADCAST_NAME)) {
			command = new CommandMPBroadcast(commandAndArgsToDeserialize);
			
		} else if (commandName.equals(CommandMPServer.LIGHTNINGBOLT_NAME)) {
			command = new CommandMPSpawnLightningBolt(commandAndArgsToDeserialize);
		} else if(commandName.equals(CommandMPServer.PROPEL_NAME)){
			command = new CommandMPPropelEntity(commandAndArgsToDeserialize);
		}else if(commandName.equals(CommandMPServer.SPAWNITEM_NAME)){
			command = new CommandMPSpawnItem(commandAndArgsToDeserialize);
		}else if(commandName.equals(CommandMPServer.CONSOLECOMMAND_NAME)){
			command = new CommandMPExecuteConsoleCommand(commandAndArgsToDeserialize);	//TODO oops 1.8
		}
		else {
			JavaProblemHandler.printErrorMessageToDialogBox(new Exception(
					"A server-side command  (type " + commandName
							+ ")'s packet could not be interpreted."));
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		String serializedCommand = command.serialize();
		ByteBufUtils.writeUTF8String(buf, serializedCommand);
	}

}
