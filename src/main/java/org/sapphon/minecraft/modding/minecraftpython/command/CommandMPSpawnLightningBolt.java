package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandMPSpawnLightningBolt extends CommandMPServer{

	
	
	public CommandMPSpawnLightningBolt(String[] commandAndArgsToDeserialize) {
	}
	
	@Override
	public void doWork() {
		WorldServer world = MinecraftServer.getServer().worldServerForDimension(0);
			int x = -175;
			int z = 60;
			
			int y = world.getTopSolidOrLiquidBlock(x, z) - 1;
			EntityLightningBolt entityLightningBolt = new EntityLightningBolt(world, x, y, z);
			world.addWeatherEffect(entityLightningBolt);
			world.spawnEntityInWorld(entityLightningBolt);
	}
	@Override
	public String serialize() {
		return CommandMPServer.LIGHTNINGBOLT_NAME;
	}
}
