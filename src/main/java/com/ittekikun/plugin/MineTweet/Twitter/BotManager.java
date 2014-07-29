package com.ittekikun.plugin.MineTweet.Twitter;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.MineTweet;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BotManager
{
	public MineTweet plugin;
	public TwitterManager twitterManager;
	public MineTweetConfig mtConfig;
	public BukkitScheduler bukkitScheduler;

	public BotManager(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twitterManager = plugin.twitterManager;
	}

	public void botSetup()
	{
		if(mtConfig.useBot)
		{
			bukkitScheduler = Bukkit.getServer().getScheduler();
			bukkitScheduler.runTaskTimer(plugin, new BotTweetTask(mtConfig.botMessageList), 0, convertSecondToTick(mtConfig.TweetCycle));
		}
	}

	public void taskCancel()
	{
		if(mtConfig.useBot)
		{
			bukkitScheduler.cancelTasks(plugin);
		}
	}

	public int convertSecondToTick(int second)
	{
		return second*20;
	}

	public class BotTweetTask extends BukkitRunnable
	{
		public List<String> botMessageList;

		public BotTweetTask(List<String> botMessageList)
		{
			this.botMessageList = new ArrayList<String>(botMessageList);
		}

		@Override
		public void run()
		{
			Collections.shuffle(botMessageList);

			if(botMessageList.get(0).length() <= 115)
			{
				try
				{
					twitterManager.tweet(botMessageList.get(0));
				}
				catch(TwitterException e)
				{
					e.printStackTrace();
				}
			}
			else
			{
				MineTweet.log.severe("[BOT]次のメッセージは116字以上の為ツイートできません。→" + mtConfig.botMessageList.get(0));
			}
		}
	}
}