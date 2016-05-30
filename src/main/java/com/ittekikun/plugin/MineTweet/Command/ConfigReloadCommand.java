package com.ittekikun.plugin.minetweet.command;

import com.ittekikun.plugin.itkcore.utility.MessageUtility;
import com.ittekikun.plugin.minetweet.data.Permission;
import com.ittekikun.plugin.minetweet.listener.RegistrationListener;
import com.ittekikun.plugin.minetweet.MineTweet;
import com.ittekikun.plugin.minetweet.Utility;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

import static com.ittekikun.plugin.itkcore.utility.MessageUtility.MessageType.INFO;
import static com.ittekikun.plugin.itkcore.utility.MessageUtility.MessageType.SEVERE;

public class ConfigReloadCommand extends BaseCommand
{
	public ConfigReloadCommand()
	{
		bePlayer = false;
		name = "reload";
		argLength = 0;
		usage = "<- reload config files";
	}

	@Override
	public void execute()
	{
		try
		{
			HandlerList.unregisterAll(plugin);
			plugin.botManager.taskCancel();
			//plugin.twitterManager.shutdownRecieveStream();

			plugin.mtConfig.loadConfig();
			messageLoader.saveMessages();

//			if((mtConfig.noticeEew || mtConfig.receiveStream) && twitterManager.status)
//			{
//				twitterManager.startRecieveStream();
//			}
			plugin.botManager.botSetup();
			RegistrationListener.registrationListener(plugin);

			MessageUtility.messageToSender(sender, INFO, messageLoader.loadMessage("config.reload.successful"), MineTweet.prefix, log);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			MessageUtility.messageToSender(sender, SEVERE, messageLoader.loadMessage("config.reload.unsuccessful"), MineTweet.prefix, log);
			return;
		}
	}

	@Override
	public boolean permission()
	{
		return Permission.RELOAD.has(sender);
	}
}