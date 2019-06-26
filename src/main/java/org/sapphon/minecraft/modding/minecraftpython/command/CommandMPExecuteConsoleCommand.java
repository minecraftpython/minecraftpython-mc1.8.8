package org.sapphon.minecraft.modding.minecraftpython.command;

import net.minecraft.client.Minecraft;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandNotFoundException;
import net.minecraft.command.ICommand;
import net.minecraft.command.PlayerSelector;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;

public class CommandMPExecuteConsoleCommand extends
        CommandMinecraftPythonServer {
	
	private String commandString;
	private String playerName;
	
	public CommandMPExecuteConsoleCommand(String commandText){
		this.commandString = commandText;
		this.playerName = Minecraft.getMinecraft().thePlayer.getDisplayName().getUnformattedText();
	}
	
	public CommandMPExecuteConsoleCommand(String[] commandAndArgsToDeserialize) {
		this.commandString = commandAndArgsToDeserialize[1];
		this.playerName = commandAndArgsToDeserialize[2];
	}
    private static String[] dropFirstString(String[] par0ArrayOfStr)
    {
        String[] astring1 = new String[par0ArrayOfStr.length - 1];

        for (int i = 1; i < par0ArrayOfStr.length; ++i)
        {
            astring1[i - 1] = par0ArrayOfStr[i];
        }

        return astring1;
    }


	@Override
	public void doWork() {
		EntityPlayer playerObject = getPlayerByName(playerName);
        String[] astring = commandString.split(" ");
        String s1 = astring[0];
        astring = dropFirstString(astring);
        ICommand icommand = MinecraftServer.getServer().getCommandManager().getCommands().get(s1);
        int i = this.getUsernameIndex(icommand, astring);
        int j = 0;

        try
        {
            if (icommand == null)
            {
                throw new CommandNotFoundException();
            }

                if (i > -1)
                {
                    EntityPlayerMP[] aentityplayermp = new EntityPlayerMP[0];
                    aentityplayermp = PlayerSelector.matchEntities(playerObject, astring[i], EntityPlayerMP.class).toArray(aentityplayermp);
                    String s2 = astring[i];
                    EntityPlayerMP[] aentityplayermp1 = aentityplayermp;
                    int k = aentityplayermp.length;

                    for (int l = 0; l < k; ++l)
                    {
                        EntityPlayerMP entityplayermp = aentityplayermp1[l];
                        astring[i] = entityplayermp.getCommandSenderName();

                        try
                        {
                            icommand.processCommand(playerObject, astring);
                            ++j;
                        }
                        catch (CommandException commandexception)
                        {
                            ChatComponentTranslation chatcomponenttranslation1 = new ChatComponentTranslation(commandexception.getMessage(), commandexception.getErrorObjects());
                            chatcomponenttranslation1.getChatStyle().setColor(EnumChatFormatting.RED);
                            playerObject.addChatMessage(chatcomponenttranslation1);
                        }
                    }

                    astring[i] = s2;
                }
                else
                {
                    icommand.processCommand(playerObject, astring);
                    ++j;
                }
        }catch(Exception e){
        	
        }

	}

	private EntityPlayer getPlayerByName(String name) {
		return MinecraftServer.getServer().getEntityWorld().getPlayerEntityByName(name);
	}

    private int getUsernameIndex(ICommand par1ICommand, String[] par2ArrayOfStr)
    {
        if (par1ICommand == null)
        {
            return -1;
        }
        else
        {
            for (int i = 0; i < par2ArrayOfStr.length; ++i)
            {
                if (par1ICommand.isUsernameIndex(par2ArrayOfStr, i) && PlayerSelector.matchesMultiplePlayers(par2ArrayOfStr[i]))
                {
                    return i;
                }
            }

            return -1;
        }
    }
				
	@Override
	public String serialize() {
		//TODO this will never work if it ever needs to, commands must certainly contain commas sometimes...?
		return CONSOLECOMMAND_NAME + SERIAL_DIV + commandString + SERIAL_DIV + playerName;
	}
}
