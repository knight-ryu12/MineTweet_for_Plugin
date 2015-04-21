package com.ittekikun.plugin.MineTweet.Listener;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import com.ittekikun.plugin.MineTweet.Utility;
import com.mcbans.firestar.mcbans.events.PlayerBannedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import twitter4j.TwitterException;

public class MCBansBANEvent implements Listener 
{
	MineTweet plugin;
	MineTweetConfig mtConfig;
	TwitterManager twittermanager;

	public MCBansBANEvent(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twittermanager = plugin.twitterManager;
	}

	@EventHandler
    public void onPlayerBanned(PlayerBannedEvent event) throws TwitterException
    {
		String name = event.getPlayerName();
	    String senderName =  event.getSenderName();
	    String reason = event.getReason();
	    String message = null;
	    
		switch(mtConfig.mcBansBanTweet)
		{
			//GBAN LBAN
			case 1: 
				message = replaceKeywords(mtConfig.ban_message_temp, name,reason,senderName);
				break;

			//GBAN
			case 2: 
				if (!event.isGlobalBan()) 
					return;

				message = replaceKeywords(mtConfig.ban_message_temp, name,reason,senderName);
				break;

			//LBAN
			case 3:
				if (event.isGlobalBan()) 
					return;
				
				message = replaceKeywords(mtConfig.ban_message_temp, name,reason,senderName);
				break;
	    }

        twittermanager.tweet(message);
    }

	private String replaceKeywords(String source,String name, String reason, String sender)
	{
		String result = source;
        if ( result.contains(MineTweet.KEYWORD_USER) )
        {
            result = result.replace(MineTweet.KEYWORD_USER, name);
        }
        if ( result.contains(MineTweet.KEYWORD_REASON) )
        {
            result = result.replace(MineTweet.KEYWORD_REASON, reason);
        }
        if ( result.contains(MineTweet.KEYWORD_SENDER) )
        {
            result = result.replace(MineTweet.KEYWORD_SENDER, sender);
        }
		if (result.contains(MineTweet.KEYWORD_NEWLINE))
		{
			result = result.replace(MineTweet.KEYWORD_NEWLINE, MineTweet.SOURCE_NEWLINE);
		}
		if (result.contains(MineTweet.KEYWORD_TIME))
		{
			String time = Utility.timeGetter(mtConfig.dateformat);

			result = result.replace(MineTweet.KEYWORD_TIME, time);
		}
        return result;
    }
}