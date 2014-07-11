package com.ittekikun.plugin.MineTweet.Listener;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import twitter4j.TwitterException;

public class QuitPlayerEvent implements Listener
{
	MineTweet plugin;
	MineTweetConfig mtConfig;
	TwitterManager twittermanager;

	public QuitPlayerEvent(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twittermanager = plugin.twitterManager;
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) throws TwitterException
	{
	    Player player = event.getPlayer(); // ログアウトした
	    String name = player.getName();

		Player[] Member = plugin.getServer().getOnlinePlayers();

		String number = Integer.toString((Member.length - 1));
		String message = replaceKeywords(mtConfig.quit_message_temp, name, number);
		
		twittermanager.tweet(message);

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
