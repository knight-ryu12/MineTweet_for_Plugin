package com.ittekikun.plugin.minetweet.listener;

import com.ittekikun.plugin.itkcore.utility.BukkitUtility;
import com.ittekikun.plugin.itkcore.utility.VariousUtility;
import com.ittekikun.plugin.minetweet.MineTweetConfig;
import com.ittekikun.plugin.minetweet.MineTweet;
import com.ittekikun.plugin.minetweet.twitter.TwitterManager;
import com.ittekikun.plugin.minetweet.Utility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import twitter4j.TwitterException;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import static com.ittekikun.plugin.minetweet.Keyword.*;

public class QuitPlayerEvent implements Listener
{
	MineTweet plugin;
	MineTweetConfig mtConfig;
	TwitterManager twitterManager;

	public QuitPlayerEvent(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twitterManager = plugin.twitterManager;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onQuitPlayer(PlayerQuitEvent event) throws TwitterException
	{
		if (!mtConfig.debugMode)
		{

			Player player = event.getPlayer();
			String name = player.getName();

			ArrayList players = BukkitUtility.getOnlinePlayers();
			String number = Integer.toString((players.size() - 1));

			String message = replaceKeywords(mtConfig.quit_message_temp, name, number);
			twitterManager.tweet(message);
		}
		else
		{
			Player player = event.getPlayer();
			final String name = player.getName();

			ArrayList players = BukkitUtility.getOnlinePlayers();
			final String number = Integer.toString((players.size() - 1));

			//画像生成でラグが起きるので別スレッド
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						String uuid = UUID.randomUUID().toString();
						File tweetImage = new File(plugin.getDataFolder(), uuid + ".png");

						Utility.generationPlayerImage(name, "LEFT THE GAME!", tweetImage);

						String message = replaceKeywords(mtConfig.quit_message_temp, name, number);
						twitterManager.tweet(message, tweetImage);
						tweetImage.delete();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
			});
		}
	}

	private String replaceKeywords(String source, String name, String number)
	{
		String result = source;
		if (result.contains(KEYWORD_PLAYER))
		{
			result = result.replace(KEYWORD_PLAYER, name);
		}
		if (result.contains(KEYWORD_NUMBER))
		{
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