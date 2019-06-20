package org.sapphon.minecraft.modding.minecraftpython;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import org.sapphon.minecraft.modding.base.CommonProxy;

public class EntityJoinWorldEventHandler {
	
	private CommonProxy proxy;
	public EntityJoinWorldEventHandler(CommonProxy proxy){
		this.proxy = proxy;
	}
	@SubscribeEvent
	public void joinWorldEventUSBCheck(PlayerEvent.PlayerLoggedInEvent event) {//doesn't work yet
		
	}
}
