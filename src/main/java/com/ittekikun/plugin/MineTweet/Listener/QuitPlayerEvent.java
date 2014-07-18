package com.ittekikun.plugin.MineTweet.Listener;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import com.ittekikun.plugin.MineTweet.Utility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

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
		if(!mtConfig.debugMode)
		{

			Player player = event.getPlayer();
			String name = player.getName();

			Player[] Member = plugin.getServer().getOnlinePlayers();

			String number = Integer.toString((Member.length - 1));
			String message = replaceKeywords(mtConfig.quit_message_temp, name, number);

			twitterManager.tweet(message);
		}
		else
		{
			Player player = event.getPlayer();
			final String name = player.getName();

			Player[] member = plugin.getServer().getOnlinePlayers();
			final String number = String.valueOf(member.length);

			//画像生成でラグが起きるので別スレッド
			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						Utility.generationPlayerImage(name, "LEFT THE GAME!");

						String message = replaceKeywords(mtConfig.quit_message_temp, name, number);
						twitterManager.tweet(message, (new File(plugin.getDataFolder(), "temp.png")));
					}
					catch(TwitterException e)
					{
						e.printStackTrace();
					}
				}
			});
		}
	}

	private String replaceKeywords(String source,String name, String number)
	{
		String result = source;
        if ( result.contains(MineTweet.KEYWORD_USER) )
        {
            result = result.replace(MineTweet.KEYWORD_USER, name);
        }
        if ( result.contains(MineTweet.KEYWORD_NUMBER) )
        {
            result = result.replace(MineTweet.KEYWORD_NUMBER, number);
        }
        return result;
    }
}