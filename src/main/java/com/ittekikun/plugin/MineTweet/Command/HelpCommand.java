package com.ittekikun.plugin.MineTweet.Command;

import com.ittekikun.plugin.MineTweet.Utility;

public class HelpCommand extends BaseCommand
{
	public HelpCommand()
	{
		bePlayer = false;
		name = "help";
		argLength = 0;
		usage = "<- show command help";
	}

	@Override
	public void execute()
	{
		Utility.message(sender, "&c===================================");
		Utility.message(sender, "&b" + plugin.getDescription().getName() + " Plugin version &3"+ plugin.getDescription().getVersion()+" &bby ittekikun");
		Utility.message(sender, " &b<>&f = required, &b[]&f = optional");
		// 全コマンドをループで表示
		for (BaseCommand cmd : plugin.getCommands().toArray(new BaseCommand[0]))
		{
			cmd.sender = this.sender;
			if (cmd.permission())
			{
				Utility.message(sender, "&8-&7 /"+command+" &c" + cmd.name + " &7" + cmd.usage);
			}
		}
		Utility.message(sender, "&c===================================");
		return;
	}

	@Override
	public boolean permission()
	{
		return true;
	}
}