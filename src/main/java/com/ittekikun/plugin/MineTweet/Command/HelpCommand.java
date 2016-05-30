package com.ittekikun.plugin.minetweet.command;

import com.ittekikun.plugin.itkcore.utility.MessageUtility;
import com.ittekikun.plugin.minetweet.Utility;

import static com.ittekikun.plugin.itkcore.utility.MessageUtility.MessageType.INFO;

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
		MessageUtility.messageToSender(sender, INFO, "&c===================================", prefix, log);
		MessageUtility.messageToSender(sender, INFO, "&b" + plugin.getDescription().getName() + " Plugin version &3"+ plugin.getDescription().getVersion()+" &bby ittekikun", prefix, log);
		MessageUtility.messageToSender(sender, INFO, " &b<>&f = required, &b[]&f = optional", prefix, log);
		// 全コマンドをループで表示
		for (BaseCommand cmd : plugin.getCommands().toArray(new BaseCommand[0]))
		{
			cmd.sender = this.sender;
			if (cmd.permission())
			{
				MessageUtility.messageToSender(sender, INFO ,"&8-&7 /"+command+" &c" + cmd.name + " &7" + cmd.usage, prefix, log);
			}
		}
		MessageUtility.messageToSender(sender, INFO, "&c===================================", prefix, log);
		return;
	}

	@Override
	public boolean permission()
	{
		return true;
	}
}