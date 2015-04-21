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
		usage = "(text) <- tweet";
	}

	@Override
	public void execute() throws CommandException
	{
		String[] array = args.toArray(new String[0]);
		String source = Utility.JoinArray(array, 0);

		if(sender instanceof Player)
		{
			String message = replaceKeywords(mtConfig.cmd_message_temp, player.getName(), source);

			//今はツイートができなかったらエラーを起こさないようにしてるけど
			//色々と厄介だし仕様変更予定
			try
			{
				twitterManager.tweet(message);
			}
			catch(TwitterException e)
			{
				Utility.message(sender, "ツイートに失敗しました。");
				e.printStackTrace();
				return;
			}
			Utility.message(sender, "ツイートに成功しました。");
			player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 1);
		}
		else
		{
			//TODO ここのコンソールを変更可能にする
			String message = replaceKeywords(mtConfig.cmd_message_temp, "コンソール", source);

			try
			{
				twitterManager.tweet(message);
			}
			catch(TwitterException e)
			{
				e.printStackTrace();
				MineTweet.log.info("ツイートに失敗しました。");
				return;
			}

			MineTweet.log.info("ツイートに成功しました。");
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
		if (result.contains(MineTweet.KEYWORD_NEWLINE))
		{
			result = result.replace(MineTweet.KEYWORD_NEWLINE, MineTweet.SOURCE_NEWLINE);
		}
		if (result.contains(MineTweet.KEYWORD_TIME))
		{
			String time = Utility.timeGetter(mtConfig.dateformat);

			result = result.replace(MineTweet.KEYWORD_TIME, time);
		}
		return result;
	}

	@Override
	public boolean permission()
	{
		return Permission.TWEET.has(sender);
	}
}