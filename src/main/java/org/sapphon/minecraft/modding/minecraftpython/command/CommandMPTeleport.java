package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

import java.util.ArrayList;
import java.util.List;

public class CommandMPTeleport extends CommandMPServer {

	public double x;
	public double y;
	public double z;
	public String teleportingPlayer;

	public CommandMPTeleport(double x, double y, double z){
		this(x,y,z, Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText());
	}
	
	public CommandMPTeleport(double x, double y, double z, String teleportingPlayerDisplayName){
		this.x = x;
		this.y = y;
		this.z = z;
		this.teleportingPlayer = teleportingPlayerDisplayName;
	}
	
	public CommandMPTeleport(String[] commandAndArgsToDeserialize) {
		this(Double.parseDouble(commandAndArgsToDeserialize[1]),
				Double.parseDouble(commandAndArgsToDeserialize[2]),
				Double.parseDouble(commandAndArgsToDeserialize[3]), commandAndArgsToDeserialize[4]);
	}


	public void doWork(){
		WorldServer world = MinecraftServer.getServer().worldServerForDimension(0);
		List<EntityPlayer> players = new ArrayList<EntityPlayer>(world.playerEntities);
		for (EntityPlayer entityPlayerMP : players) {
			if(entityPlayerMP.getDisplayName().equals(teleportingPlayer)){
				entityPlayerMP.setPositionAndRotation(x, y, z, entityPlayerMP.rotationYaw, entityPlayerMP.rotationPitch);	//TODO UNTESTED thrfr SUSPECT
				return;
			}
		}
	}
	
	@Override
	public String serialize() {
		return CommandMPServer.TELEPORT_NAME + CommandMPAbstract.SERIAL_DIV + x + CommandMPAbstract.SERIAL_DIV + y + CommandMPAbstract.SERIAL_DIV + z + CommandMPAbstract.SERIAL_DIV + teleportingPlayer;
	}
	
	
}