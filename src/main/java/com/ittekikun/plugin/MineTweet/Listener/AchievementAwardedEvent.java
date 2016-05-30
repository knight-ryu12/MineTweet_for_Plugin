package com.ittekikun.plugin.minetweet.listener;

import com.ittekikun.plugin.itkcore.utility.VariousUtility;
import com.ittekikun.plugin.minetweet.MineTweetConfig;
import com.ittekikun.plugin.minetweet.MineTweet;
import com.ittekikun.plugin.minetweet.twitter.TwitterManager;
import com.ittekikun.plugin.minetweet.Utility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import twitter4j.TwitterException;

import static com.ittekikun.plugin.minetweet.Keyword.*;

public class AchievementAwardedEvent implements Listener
{
	MineTweet plugin;
	MineTweetConfig mtConfig;
	TwitterManager twitterManager;

	public AchievementAwardedEvent(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twitterManager = plugin.twitterManager;
	}

	@EventHandler
	public void onAchievementAwarded(PlayerAchievementAwardedEvent event) throws TwitterException
	{
		String player = event.getPlayer().getName();
		String achievement = event.getAchievement().name();

		//指定された項目がなければ渡された物がそのまま帰ってくる（はず）
		String name = mtConfig.loadAchievementName(achievement);

		String message = replaceKeywords(mtConfig.achievement_message_temp, player, name);

		twitterManager.tweet(message);
	}

	private String replaceKeywords(String source,String name,String achievement)
	{
		String result = source;
		if (result.contains(KEYWORD_PLAYER) )
		{
			result = result.replace(KEYWORD_PLAYER, name);
		}
		if (result.contains(KEYWORD_ACHIEVEMENT) )
		{
			result = result.replace(KEYWORD_ACHIEVEMENT, achievement);
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