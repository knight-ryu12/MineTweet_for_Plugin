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
import org.bukkit.event.player.PlayerJoinEvent;
import twitter4j.TwitterException;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import static com.ittekikun.plugin.minetweet.Keyword.*;

public class JoinPlayerEvent implements Listener
{
	MineTweet plugin;
	MineTweetConfig mtConfig;
	TwitterManager twitterManager;

	public JoinPlayerEvent(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twitterManager = plugin.twitterManager;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoinPlayer(final PlayerJoinEvent event) throws TwitterException
	{
		if (!mtConfig.tweetWithIma)
		{
			Player player = event.getPlayer();
			String name = player.getName();

			ArrayList players = BukkitUtility.getOnlinePlayers();
			String number = Integer.toString((players.size()));

			String Message = replaceKeywords(mtConfig.join_message_temp, name, number);

			twitterManager.tweet(Message);
		}
		else
		{
			Player player = event.getPlayer();
			final String name = player.getName();

			ArrayList players = BukkitUtility.getOnlinePlayers();
			final String number = Integer.toString((players.size()));
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

						Utility.generationPlayerImage(name, "JOIN THE GAME!", tweetImage);

						String message = replaceKeywords(mtConfig.join_message_temp, name, number);
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