package com.ittekikun.plugin.MineTweet.Command;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.Data.Permission;
import com.ittekikun.plugin.MineTweet.Utility;

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
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			return;
		}
		Utility.message(sender, "&aConfigをリロードしました！");
	}

	@Override
	public boolean permission()
	{
		return Permission.RELOAD.has(sender);
	}
}