package com.ittekikun.plugin.minetweet.twitter;

import com.ittekikun.plugin.itkcore.utility.BukkitUtility;
import com.ittekikun.plugin.itkcore.utility.VariousUtility;
import com.ittekikun.plugin.minetweet.MineTweetConfig;
import com.ittekikun.plugin.minetweet.MineTweet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.ittekikun.plugin.minetweet.Utility;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import twitter4j.TwitterException;

import static com.ittekikun.plugin.minetweet.Keyword.*;

public class BotManager
{
	public MineTweet plugin;
	public TwitterManager twitterManager;
	public MineTweetConfig mtConfig;
	public List<String> botMessageList;
	public BukkitScheduler bukkitScheduler;

	public BotManager(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twitterManager = plugin.twitterManager;
	}

	public void botSetup()
	{
		if (this.mtConfig.useBot)
		{
			this.bukkitScheduler = Bukkit.getServer().getScheduler();

			this.botMessageList = new ArrayList(this.mtConfig.botMessageList);
			this.bukkitScheduler.runTaskTimer(this.plugin, new BotTweetTask(this.botMessageList), 0L, convertSecondToTick(this.mtConfig.tweetCycle));
		}
	}

	public void taskCancel()
	{
		if (this.mtConfig.useBot)
		{
			bukkitScheduler.cancelTasks(this.plugin);
		}
	}

	//簡単だけど分かりやすくするために
	public int convertSecondToTick(int second)
	{
		return second * 20;
	}

	public class BotTweetTask implements Runnable
	{
		public List<String> botMessageList;

		public BotTweetTask(List<String> botMessageList)
		{
			this.botMessageList = botMessageList;
		}

		public void run()
		{
			Collections.rotate(this.botMessageList, 1);
			String message = replaceKeywords(this.botMessageList.get(0));
			try
			{
				BotManager.this.twitterManager.tweet(message);
			}
			catch (TwitterException e)
			{
				e.printStackTrace();
			}
		}

		private String replaceKeywords(String source)
		{
			String result = source;
			if (result.contains(KEYWORD_NUMBER))
			{
				ArrayList players = BukkitUtility.getOnlinePlayers();
				String number = Integer.toString((players.size()));

				result = result.replace(KEYWORD_NUMBER, number);
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
	}
}