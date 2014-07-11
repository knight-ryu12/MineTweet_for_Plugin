package com.ittekikun.plugin.MineTweet.Listener;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import twitter4j.TwitterException;

public class JoinPlayerEvent implements Listener
{
	MineTweet plugin;
	MineTweetConfig mtconfig;
	TwitterManager twittermanager;

	public JoinPlayerEvent(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtconfig = plugin.mtConfig;
		this.twittermanager = plugin.twitterManager;
	}

	@EventHandler
	public void onJoinPlayerEvent(PlayerJoinEvent event) throws TwitterException
	{
	    Player player = event.getPlayer();
	    String name = player.getName();
		
		Player[] member = plugin.getServer().getOnlinePlayers();
		String number = String.valueOf(member.length);
		
		String Message = replaceKeywords(mtconfig.join_message_temp, name, number);

        twittermanager.tweet(Message);
	}

	private String replaceKeywords(String source,String name, String number)
	{
		String result = source;
        if ( result.contains(MineTweet.KEYWORD_USER) )
        {
            result = result.replace(MineTweet.KEYWORD_USER, name);
        }
        if ( result.contains(MineTweet.KEYWORD_NUMBER) )
        {
            result = result.replace(MineTweet.KEYWORD_NUMBER, number);
        }
        return result;
    }
}
