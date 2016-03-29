package com.ittekikun.plugin.MineTweet.Listener;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import com.ittekikun.plugin.MineTweet.Utility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import twitter4j.TwitterException;

import static com.ittekikun.plugin.MineTweet.Keyword.*;

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
			String time = Utility.timeGetter(mtConfig.dateformat);

			result = result.replace(KEYWORD_TIME, time);
		}
		return result;
	}
}