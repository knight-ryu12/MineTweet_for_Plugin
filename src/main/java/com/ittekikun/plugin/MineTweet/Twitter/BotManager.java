package com.ittekikun.plugin.MineTweet.Twitter;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.MineTweet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import twitter4j.TwitterException;

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
			this.bukkitScheduler.runTaskTimer(this.plugin, new BotTweetTask(this.botMessageList), 0L, convertSecondToTick(this.mtConfig.TweetCycle));
		}
	}

	public void taskCancel()
	{
		if (this.mtConfig.useBot)
		{
			this.bukkitScheduler.cancelTasks(this.plugin);
		}
	}

	public int convertSecondToTick(int second)
	{
		return second * 20;
	}

	public class BotTweetTask extends BukkitRunnable
	{
		public List<String> botMessageList;

		public BotTweetTask(List<String> botMessageList)
		{
			this.botMessageList = this.botMessageList;
		}

		public void run()
		{
			Collections.rotate(this.botMessageList, 1);
			if (((String)this.botMessageList.get(0)).length() <= 115) {
				try
				{
					BotManager.this.twitterManager.tweet((String)this.botMessageList.get(0));
				}
				catch (TwitterException e)
				{
					e.printStackTrace();
				}
			} else {
				MineTweet.log.severe("[BOT]次のメッセージは116字以上の為ツイートできません。→" + (String)BotManager.this.mtConfig.botMessageList.get(0));
			}
		}
	}
}