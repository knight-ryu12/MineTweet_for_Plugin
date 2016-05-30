package com.ittekikun.plugin.minetweet.listener;

import com.ittekikun.plugin.itkcore.utility.VariousUtility;
import com.ittekikun.plugin.minetweet.MineTweetConfig;
import com.ittekikun.plugin.minetweet.MineTweet;
import com.ittekikun.plugin.minetweet.twitter.TwitterManager;
import com.ittekikun.plugin.minetweet.Utility;
import com.mcbans.firestar.mcbans.events.PlayerBannedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import twitter4j.TwitterException;

import static com.ittekikun.plugin.minetweet.Keyword.*;

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
				if(event.isGlobalBan())
				{
					message = replaceKeywords(mtConfig.ban_message_temp, name, reason, plugin.messageLoader.loadMessage("mcbans.global"), senderName);
				}
				else if(event.isLocalBan())
				{
					message = replaceKeywords(mtConfig.ban_message_temp, name, reason ,plugin.messageLoader.loadMessage("mcbans.local"), senderName);
				}
				break;

			//GBAN
			case 2: 
				if (event.isGlobalBan())
				{
					message = replaceKeywords(mtConfig.ban_message_temp, name, reason, plugin.messageLoader.loadMessage("mcbans.global"), senderName);
				}
				break;

			//LBAN
			case 3:
				if(event.isLocalBan())
				{
					message = replaceKeywords(mtConfig.ban_message_temp, name, reason ,plugin.messageLoader.loadMessage("mcbans.local"), senderName);
				}
				break;
	    }
        twittermanager.tweet(message);
    }

	private String replaceKeywords(String source, String name, String reason, String type, String sender)
	{
		String result = source;
        if ( result.contains(KEYWORD_PLAYER) )
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
		if ( result.contains(KEYWORD_BANTYPE) )
		{
			result = result.replace(KEYWORD_BANTYPE, type);
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