package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;

public class CommandMPGetPlayerLookVector {
	public double[] execute(){
		Minecraft minecraft = Minecraft.getMinecraft();
		Vec3 lookVector = minecraft.thePlayer.getLook(1.0f);
		return new double[]{
			lookVector.xCoord, 
			lookVector.yCoord, 
			lookVector.zCoord
		};
	}
}
