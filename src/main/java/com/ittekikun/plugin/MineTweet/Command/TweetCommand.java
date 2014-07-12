package com.ittekikun.plugin.MineTweet.Command;

import com.ittekikun.plugin.MineTweet.Data.Permission;
import com.ittekikun.plugin.MineTweet.Exception.CommandException;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import com.ittekikun.plugin.MineTweet.Utility;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import twitter4j.TwitterException;

public class TweetCommand extends BaseCommand
{
	public TweetCommand()
	{
		bePlayer = false;
		name = "tw";
		argLength = 1;
		usage = "(text)";
	}

	@Override
	public void execute() throws CommandException
	{
		if(TwitterManager.status)
		{
			String[] array = (String[])args.toArray(new String[0]);
			String mes = Utility.arrayUnion(array, 0);

			if(sender instanceof Player)
			{
				String name = player.getName();

				String Message = replaceKeywords(mtConfig.cmd_message_temp, name,mes);

				try
				{
					twitterManager.tweet(Message);
				}
				catch(TwitterException e)
				{
					e.printStackTrace();
				}
				Utility.message(sender, "&bツイートに成功しました。");
				player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 1);
			}
			else
			{
				String Message = replaceKeywords(mtConfig.cmd_message_temp, "コンソール",mes);

				try
				{
					twitterManager.tweet(Message);
				}
				catch(TwitterException e)
				{
					e.printStackTrace();
				}

				Utility.message(sender, "&bツイートに成功しました。");
			}
		}
		else
		{
			Utility.message(sender, "&cまだ認証を行っていないのでツイート出来ません！");
		}
	}

	private String replaceKeywords(String source,String name, String message)
	{
		String result = source;
		if (result.contains(MineTweet.KEYWORD_USER))
		{
			result = result.replace(MineTweet.KEYWORD_USER, name);
		}
		if ( result.contains(MineTweet.KEYWORD_MESSAGE ) )
		{
			result = result.replace(MineTweet.KEYWORD_MESSAGE , message);
		}
		return result;
	}

	@Override
	public boolean permission()
	{
		return Permission.TWEET.has(sender);
	}
}