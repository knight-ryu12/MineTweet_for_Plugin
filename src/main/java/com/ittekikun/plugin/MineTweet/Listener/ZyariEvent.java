package com.ittekikun.plugin.minetweet.listener;

import com.ittekikun.plugin.itkcore.utility.VariousUtility;
import com.ittekikun.plugin.minetweet.MineTweetConfig;
import com.ittekikun.plugin.minetweet.MineTweet;
import com.ittekikun.plugin.minetweet.twitter.TwitterManager;
import com.ittekikun.plugin.minetweet.Utility;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import twitter4j.TwitterException;

import static com.ittekikun.plugin.minetweet.Keyword.*;

//例のアレ
public class ZyariEvent implements Listener
{
	MineTweet plugin;
	MineTweetConfig mtConfig;
	TwitterManager twitterManager;

	public  ZyariEvent(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twitterManager = plugin.twitterManager;
	}

	@EventHandler
	public void onBlockBreakEvent(BlockBreakEvent event) throws TwitterException
	{
		if(event.getBlock().getType() == Material.GRAVEL)
		{
			String name = event.getPlayer().getName();
			String message = replaceKeywords("あぁ＾〜$userのこころがじゃりじゃりするんじゃあ＾〜【自動投稿】", name);
			twitterManager.tweet(message);
		}
	}

	private String replaceKeywords(String source,String name)
	{
		String result = source;
		if (result.contains(KEYWORD_PLAYER) )
		{
			result = result.replace(KEYWORD_PLAYER, name);
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