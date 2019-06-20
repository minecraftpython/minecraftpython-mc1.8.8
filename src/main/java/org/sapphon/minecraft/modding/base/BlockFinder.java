package org.sapphon.minecraft.modding.base;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameData;

public class BlockFinder {
	public static Block getBlockWithName(String name){
		Block registryResult = GameData.getBlockRegistry().getObject(new ResourceLocation(name.toLowerCase()));
		return registryResult == null ? Blocks.dirt : registryResult;
	}

}
