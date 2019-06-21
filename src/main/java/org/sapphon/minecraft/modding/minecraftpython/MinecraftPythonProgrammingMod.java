package org.sapphon.minecraft.modding.minecraftpython;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sapphon.minecraft.modding.base.ClientProxy;
import org.sapphon.minecraft.modding.base.CommonProxy;
import org.sapphon.minecraft.modding.base.DedicatedServerProxy;
import org.sapphon.minecraft.modding.base.ModConfigurationFlags;
import org.sapphon.minecraft.modding.minecraftpython.command.PacketHandlerServerSideCommand;
import org.sapphon.minecraft.modding.minecraftpython.command.PacketServerSideCommand;
import org.sapphon.minecraft.modding.minecraftpython.spells.ThreadFactory;

@Mod(modid = MinecraftPythonProgrammingMod.MODID, version = MinecraftPythonProgrammingMod.VERSION, name = MinecraftPythonProgrammingMod.MODID)
public class MinecraftPythonProgrammingMod {
	public static final String MODID = "minecraftpython";
	public static final String VERSION = "1.8.8-0.4.0";
	public static final int SCRIPT_RUN_COOLDOWN = 1500;
	public static final Logger logger = LogManager.getLogger(MinecraftPythonProgrammingMod.MODID);
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
	}

	@Mod.Instance(value = MinecraftPythonProgrammingMod.MODID)
	public static MinecraftPythonProgrammingMod instance;

	@SidedProxy(clientSide = "org.sapphon.minecraft.modding.base.CombinedClientProxy", serverSide = "org.sapphon.minecraft.modding.base.DedicatedServerProxy")
	public static CommonProxy proxy;
	public static SimpleNetworkWrapper serverCommandPacketChannel;
	public static SimpleNetworkWrapper clientCommandPacketChannel;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		if (isEnabled()) {
			if(!ScriptLoaderConstants.resourcePathExists()){
				ScriptLoaderConstants.setResourcePath(event);
			}
				serverCommandPacketChannel = NetworkRegistry.INSTANCE
						.newSimpleChannel("GSSServerCommand");
				serverCommandPacketChannel.registerMessage(
						PacketHandlerServerSideCommand.class,
						PacketServerSideCommand.class, 0, Side.SERVER);
				clientCommandPacketChannel = NetworkRegistry.INSTANCE
						.newSimpleChannel("GSSClientCommand");
				clientCommandPacketChannel.registerMessage(
						PacketHandlerClientSideCommand.class,
						PacketClientSideCommand.class, 0, Side.CLIENT);
		}
	}

	private boolean isEnabled() {
		return ModConfigurationFlags.MINECRAFT_PYTHON_PROGRAMMING();
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		if (isEnabled() && !(proxy instanceof DedicatedServerProxy)) {
			FMLCommonHandler
					.instance()
					.bus()
					.register(
							new MinecraftProgrammingKeyHandler(
									MPOnlyScriptLoader.SINGLETON()
											.getMagicVessel()));
		}
		proxy.registerRenderers();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		if (!(proxy instanceof ClientProxy) && ModConfigurationFlags.MPPM_WEB()) {
			ThreadFactory.makeJavaGameLoopThread().start();
		}
	}
	
}