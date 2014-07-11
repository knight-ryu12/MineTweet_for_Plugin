package com.ittekikun.plugin.MineTweet.Listener;

import com.github.ucchyocean.lc.event.LunaChatChannelCreateEvent;
import com.github.ucchyocean.lc.event.LunaChatChannelRemoveEvent;
import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import twitter4j.TwitterException;

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
	    if(!isPrivateChanne(channelName))
	    {
	    	String Message = replaceKeywords(mtConfig.CC_message_temp, channelName);
	    	
	    	twittermanager.tweet(Message);
	    }
	}

	@EventHandler
	public void onChannelRemove(LunaChatChannelRemoveEvent event) throws TwitterException 
	{
		String channelName = event.getChannelName();
	    
	    if(!isPrivateChanne(channelName))
	    {
	    	String Message = replaceKeywords(mtConfig.CD_message_temp, channelName);
	    	
	    	twittermanager.tweet(Message);
	    }
	}
	
	private Boolean isPrivateChanne(String channel)
	{
		return channel.contains(">");
    }

	private String replaceKeywords(String source,String channel)
	{
		String result = source;
        if (result.contains(MineTweet.KEYWORD_CHANNEL) )
        {
            result = result.replace(MineTweet.KEYWORD_CHANNEL, channel);
        }
        return result;
    }
}