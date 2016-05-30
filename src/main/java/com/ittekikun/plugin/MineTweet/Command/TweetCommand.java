package com.ittekikun.plugin.minetweet.command;

import com.ittekikun.plugin.itkcore.utility.MessageUtility;
import com.ittekikun.plugin.itkcore.utility.VariousUtility;
import com.ittekikun.plugin.minetweet.data.Permission;
import com.ittekikun.plugin.minetweet.exception.CommandException;
import com.ittekikun.plugin.minetweet.MineTweet;
import com.ittekikun.plugin.minetweet.Utility;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import twitter4j.TwitterException;

import static com.ittekikun.plugin.itkcore.utility.MessageUtility.MessageType.INFO;
import static com.ittekikun.plugin.minetweet.Keyword.*;

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
	public void execute() throws CommandException, TwitterException
	{
		String[] array = args.toArray(new String[0]);
		String source = VariousUtility.joinArray(array, 0);

		if(sender instanceof Player)
		{
			String message = replaceKeywords(mtConfig.cmd_message_temp, player.getName(), source);

			try
			{
				twitterManager.tweet(message);
				MessageUtility.messageToSender(sender, INFO, messageLoader.loadMessage("command.tweet.successful"), prefix,log);
			}
			catch(TwitterException e)
			{
				e.printStackTrace();
				MineTweet.log.info(messageLoader.loadMessage("command.tweet.unsuccessful"));
				return;
			}

			//1.9から
			Class<?> cl = Sound.class;

			for (Object o: cl.getEnumConstants())
			{
				if (o.toString().equals("ENTITY_PLAYER_LEVELUP") || (o.toString().equals("LEVEL_UP")))
				{
					player.playSound(player.getLocation(), (Sound)o, 10, 1);
				}
			}
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
				MineTweet.log.info(messageLoader.loadMessage("command.tweet.unsuccessful"));
				return;
			}

			MineTweet.log.info(messageLoader.loadMessage("command.tweet.successful"));
		}
	}

	private String replaceKeywords(String source,String name, String message)
	{
		String result = source;
		if (result.contains(KEYWORD_PLAYER))
		{
			result = result.replace(KEYWORD_PLAYER, name);
		}
		if ( result.contains(KEYWORD_MESSAGE ) )
		{
			result = result.replace(KEYWORD_MESSAGE , message);
		}
		if (result.contains(KEYWORD_NEWLINE))
		{
			result = result.replace(KEYWORD_NEWLINE, SOURCE_NEWLINE);
		}
		if (result.contains(KEYWORD_TIME))
		{
			String time = VariousUtility.timeGetter(mtConfig.dateformat);

			result = result.replace(KEYWORD_TIME, time);
		}
		return result;
	}

	@Override
	public boolean permission()
	{
		return Permission.TWEET.has(sender);
	}
}