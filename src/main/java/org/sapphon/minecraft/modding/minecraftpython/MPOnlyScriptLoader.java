package org.sapphon.minecraft.modding.minecraftpython;

import org.sapphon.minecraft.modding.minecraftpython.problemhandlers.PythonProblemHandler;
import org.sapphon.minecraft.modding.minecraftpython.spells.SpellFactory;
import org.sapphon.minecraft.modding.techmage.wands.MagicItemFactory;

import java.io.File;

public class MPOnlyScriptLoader {

	private RudimentaryMagicItem magicVessel;

	private static MPOnlyScriptLoader SINGLETON;

	public static MPOnlyScriptLoader SINGLETON() {
		if (SINGLETON == null)
			SINGLETON = new MPOnlyScriptLoader("your_code_here");
		return SINGLETON;
	}

	private MPOnlyScriptLoader(String scriptFileName) {
		File scriptsDirectory = new File(ScriptLoaderConstants.MINECRAFT_PROGRAMMING_PATH);
		if (scriptsDirectory.canRead() && scriptsDirectory.isDirectory()) {
			try {
				File script = new File(ScriptLoaderConstants.MINECRAFT_PROGRAMMING_PATH
						+ File.separatorChar + scriptFileName + ScriptLoaderConstants.PYTHON_SCRIPT_EXTENSION);
				magicVessel = MagicItemFactory.create(SpellFactory.createNonCachingSpell(script), MinecraftPythonProgrammingMod.SCRIPT_RUN_COOLDOWN);
			} catch (Exception e) {
				PythonProblemHandler.printErrorMessageToDialogBox(e);
			}
		}
	}

	public IArcane getMagicVessel(){
		return magicVessel;
	}

}
