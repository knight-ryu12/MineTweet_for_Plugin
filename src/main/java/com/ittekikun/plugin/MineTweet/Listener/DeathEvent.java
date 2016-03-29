package com.ittekikun.plugin.MineTweet.Listener;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import com.ittekikun.plugin.MineTweet.Utility;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import twitter4j.TwitterException;

public class DeathEvent implements Listener
{
    MineTweet plugin;
    MineTweetConfig mtConfig;
    TwitterManager twitterManager;

    public DeathEvent(MineTweet plugin)
    {
        this.plugin = plugin;
        this.mtConfig = plugin.mtConfig;
        this.twitterManager = plugin.twitterManager;
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) throws TwitterException
    {
        String message = event.getDeathMessage();

        twitterManager.tweet(message);
    }
}