package org.sapphon.minecraft.modding.techmage;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.LanguageRegistry;
import org.sapphon.minecraft.modding.base.CommonProxy;
import org.sapphon.minecraft.modding.base.ModConfigurationFlags;
import org.sapphon.minecraft.modding.minecraftpython.ScriptLoaderConstants;

@Mod(modid = TechMageMod.MODID, version = TechMageMod.VERSION, name = TechMageMod.MODID)
public class TechMageMod {
	public static final String MODID = "techmage";
	public static final String VERSION = "1.8.8-0.3.5";

	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
	}

	@Mod.Instance(value = TechMageMod.MODID)
	public static TechMageMod instance;

	@SidedProxy(clientSide = "org.sapphon.minecraft.modding.base.ClientProxy", serverSide = "org.sapphon.minecraft.modding.base.CommonProxy")
	public static CommonProxy proxy;

	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {

		if (isEnabled()) {
			if(!ScriptLoaderConstants.resourcePathExists()){
				ScriptLoaderConstants.setResourcePath(event);
			}
				for (MagicWand wand : ArcaneArmory.SINGLETON().getWands()) {
					GameRegistry.registerItem(wand, wand.getUnlocalizedName(),
							TechMageMod.MODID);
					String wandDisplayName = wand.getSpell().getDisplayName();
					if (wandDisplayName != SpellMetadataConstants.NONE) {
						LanguageRegistry.instance().addStringLocalization(
								wand.getUnlocalizedName() + ".name", "en_US",
								wandDisplayName);
					}
				}
		}
	}

	@Mod.EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRenderers();
	}

	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
	}

	private boolean isEnabled() {
		return ModConfigurationFlags.SPELLCRAFTERS();
	}

}