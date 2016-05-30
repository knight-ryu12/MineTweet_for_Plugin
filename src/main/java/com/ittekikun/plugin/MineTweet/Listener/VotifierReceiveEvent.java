package com.ittekikun.plugin.minetweet.listener;

import com.ittekikun.plugin.itkcore.utility.VariousUtility;
import com.ittekikun.plugin.minetweet.MineTweetConfig;
import com.ittekikun.plugin.minetweet.MineTweet;
import com.ittekikun.plugin.minetweet.twitter.TwitterManager;
import com.ittekikun.plugin.minetweet.Utility;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import twitter4j.TwitterException;

import static com.ittekikun.plugin.minetweet.Keyword.*;

public class VotifierReceiveEvent implements Listener
{
	MineTweet plugin;
	MineTweetConfig mtConfig;
	TwitterManager twitterManager;

	public  VotifierReceiveEvent(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twitterManager = plugin.twitterManager;
	}

	@EventHandler
	public void onVotifierEvent(VotifierEvent event) throws TwitterException
	{
		Vote vote = event.getVote();

		String name = vote.getUsername();
		String service = vote.getServiceName();

		String message = replaceKeywords(mtConfig.votifier_message_temp, name, service);

		twitterManager.tweet(message);
	}

	private String replaceKeywords(String source,String name,String service)
	{
		String result = source;
		if (result.contains(KEYWORD_PLAYER) )
		{
			result = result.replace(KEYWORD_PLAYER, name);
		}
		if (result.contains(KEYWORD_SERVICE) )
		{
			result = result.replace(KEYWORD_SERVICE, service);
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