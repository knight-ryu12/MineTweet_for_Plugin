package com.ittekikun.plugin.MineTweet.Command;

import com.ittekikun.plugin.MineTweet.Data.Permission;
import com.ittekikun.plugin.MineTweet.Listener.RegistrationListener;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Utility;
import org.bukkit.entity.Player;
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
			HandlerList.unregisterAll(plugin);
			plugin.botManager.taskCancel();
			plugin.mtConfig.loadConfig();
			plugin.botManager.botSetup();
			RegistrationListener.registrationListener(plugin);
		}
		catch (Exception ex)
		{
			if(sender instanceof Player)
			{
				Utility.message(sender, "全てのconfigファイルのリロードに失敗しました。");
			}
			else
			{
				MineTweet.log.severe("全てconfigファイルのリロードに失敗しました。");
			}

			ex.printStackTrace();
			return;
		}

		if(sender instanceof Player)
		{
			Utility.message(sender, "全てのconfigファイルをリロードしました。");
		}
		else
		{
			MineTweet.log.info("全てのconfigファイルをリロードしました。");
		}
	}

	@Override
	public boolean permission()
	{
		return Permission.RELOAD.has(sender);
	}
}