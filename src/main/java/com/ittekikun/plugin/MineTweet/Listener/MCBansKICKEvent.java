package com.ittekikun.plugin.minetweet.listener;

import com.ittekikun.plugin.itkcore.utility.VariousUtility;
import com.ittekikun.plugin.minetweet.MineTweetConfig;
import com.ittekikun.plugin.minetweet.MineTweet;
import com.ittekikun.plugin.minetweet.twitter.TwitterManager;
import com.ittekikun.plugin.minetweet.Utility;
import com.mcbans.firestar.mcbans.events.PlayerKickEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import twitter4j.TwitterException;

import static com.ittekikun.plugin.minetweet.Keyword.*;

public class MCBansKICKEvent implements Listener 
{
	MineTweet plugin;
	MineTweetConfig mtConfig;
	TwitterManager twittermanager;

	public MCBansKICKEvent(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twittermanager = plugin.twitterManager;
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event) throws TwitterException
	{
	    String Player = event.getPlayer();
	    String Sender =  event.getSender();
	    String Reason = event.getReason();

		String Message = replaceKeywords(mtConfig.kick_message_temp, Player,Reason,Sender);

		twittermanager.tweet(Message);
	}

	private String replaceKeywords(String source,String name, String reason, String sender)
	{
		String result = source;
        if ( result.contains(KEYWORD_PLAYER))
        {
            result = result.replace(KEYWORD_PLAYER, name);
        }
        if ( result.contains(KEYWORD_REASON) )
        {
            result = result.replace(KEYWORD_REASON, reason);
        }
        if ( result.contains(KEYWORD_SENDER) )
        {
            result = result.replace(KEYWORD_SENDER, sender);
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