package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CommandMPPropelEntity extends CommandMPServer {

	public double xVel;
	public double yVel;
	public double zVel;
	public int idToPropel;

	public CommandMPPropelEntity(double x, double y, double z) {
		this(x, y, z, Minecraft.getMinecraft().thePlayer.getEntityId());
	}

	public CommandMPPropelEntity(double x, double y, double z,
			int entityIdToPropel) {
		this.xVel = x;
		this.yVel = y;
		this.zVel = z;
		this.idToPropel = entityIdToPropel;
	}

	public CommandMPPropelEntity(String[] commandAndArgsToDeserialize) {
		this(Double.parseDouble(commandAndArgsToDeserialize[1]), Double
				.parseDouble(commandAndArgsToDeserialize[2]), Double
				.parseDouble(commandAndArgsToDeserialize[3]), Integer
				.parseInt(commandAndArgsToDeserialize[4]));
	}

	public void doWork() {
		World world = MinecraftServer.getServer().worldServerForDimension(0);
		List<Entity> list = new ArrayList<Entity>(world.loadedEntityList);
		Entity toPropel = null;
		for (Entity entity : list) {
			if (entity.getEntityId() == this.idToPropel) {
				toPropel = entity;
				break;
			}
		}
		if (toPropel != null) {
			toPropel.addVelocity(xVel, yVel, zVel);
			toPropel.velocityChanged = true;
		}
	}

	@Override
	public String serialize() {
		return CommandMPServer.PROPEL_NAME
				+ CommandMPAbstract.SERIAL_DIV + xVel
				+ CommandMPAbstract.SERIAL_DIV + yVel
				+ CommandMPAbstract.SERIAL_DIV + zVel
				+ CommandMPAbstract.SERIAL_DIV + idToPropel;
	}
}