package com.ittekikun.plugin.MineTweet.Command;

import com.ittekikun.plugin.MineTweet.Data.Permission;
import com.ittekikun.plugin.MineTweet.Listener.RegistrationListener;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Utility;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitScheduler;

public class ConfigReloadCommand extends BaseCommand
{
	public ConfigReloadCommand()
	{
		bePlayer = false;
		name = "reload";
		argLength = 0;
		usage = "<- reload config.yml";
	}

	@Override
	public void execute()
	{
		try
		{
			plugin.mtConfig.loadConfig();
			plugin.botManager.taskCancel();
			plugin.botManager.botSetup();
			HandlerList.unregisterAll(plugin);
			RegistrationListener.registrationListener(plugin);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return;
		}
		MineTweet.log.info("&aConfigをリロードしました！");
	}

	@Override
	public boolean permission()
	{
		return Permission.RELOAD.has(sender);
	}
}