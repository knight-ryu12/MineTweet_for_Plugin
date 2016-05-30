package com.ittekikun.plugin.minetweet.listener;

import com.github.ucchyocean.lc.event.LunaChatChannelCreateEvent;
import com.github.ucchyocean.lc.event.LunaChatChannelRemoveEvent;
import com.ittekikun.plugin.itkcore.utility.VariousUtility;
import com.ittekikun.plugin.minetweet.MineTweetConfig;
import com.ittekikun.plugin.minetweet.MineTweet;
import com.ittekikun.plugin.minetweet.twitter.TwitterManager;
import com.ittekikun.plugin.minetweet.Utility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import twitter4j.TwitterException;

import static com.ittekikun.plugin.minetweet.Keyword.*;

public class LunaChatEvent implements Listener 
{
	MineTweet plugin;
	MineTweetConfig mtConfig;
	TwitterManager twittermanager;

	public LunaChatEvent(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twittermanager = plugin.twitterManager;
	}

	@EventHandler
	public void onChannelCreate(LunaChatChannelCreateEvent event) throws TwitterException 
	{
	    String channelName = event.getChannelName();
	    
	    //プライベートチャンネルの場合はツイートしない
	    if(channelName.contains(">"))
	    {
	    	String Message = replaceKeywords(mtConfig.CC_message_temp, channelName);
	    	
	    	twittermanager.tweet(Message);
	    }
	}

	@EventHandler
	public void onChannelRemove(LunaChatChannelRemoveEvent event) throws TwitterException 
	{
		String channelName = event.getChannelName();

		if(channelName.contains(">"))
	    {
	    	String Message = replaceKeywords(mtConfig.CD_message_temp, channelName);
	    	
	    	twittermanager.tweet(Message);
	    }
	}

	private String replaceKeywords(String source,String channel)
	{
		String result = source;
        if (result.contains(KEYWORD_CHANNEL) )
        {
            result = result.replace(KEYWORD_CHANNEL, channel);
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