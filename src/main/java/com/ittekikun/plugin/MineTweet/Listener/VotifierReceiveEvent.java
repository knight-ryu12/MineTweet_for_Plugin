package com.ittekikun.plugin.MineTweet.Listener;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import twitter4j.TwitterException;

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
		if (result.contains(MineTweet.KEYWORD_USER) )
		{
			result = result.replace(MineTweet.KEYWORD_USER, name);
		}
		if (result.contains(MineTweet.KEYWORD_SERVICE) )
		{
			result = result.replace(MineTweet.KEYWORD_SERVICE, service);
		}
		return result;
	}
}