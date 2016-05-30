package com.ittekikun.plugin.minetweet.listener;

import com.ittekikun.plugin.itkcore.UpdateCheck;
import com.ittekikun.plugin.minetweet.MineTweetConfig;
import com.ittekikun.plugin.minetweet.MineTweet;
import com.ittekikun.plugin.minetweet.twitter.TwitterManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import twitter4j.TwitterException;

public class VersionCheckEvent implements Listener 
{
	MineTweet plugin;
	MineTweetConfig mtConfig;
	TwitterManager twittermanager;

	public VersionCheckEvent(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twittermanager = plugin.twitterManager;
	}

	@EventHandler
	public void onPlayerJoinCheck(PlayerJoinEvent event) throws TwitterException
	{
		Player player = event.getPlayer();
		if(player.isOp() || player.hasPermission("minetweet.info"))
		{
			double NowVer = Double.valueOf(plugin.getServer().getPluginManager().getPlugin("MineTweet_for_Plugin").getDescription().getVersion());

			String url = "MineTweet_for_Plugin";

			Thread updateCheck = new Thread(new UpdateCheck(player, url, NowVer, MineTweet.prefix, MineTweet.log));
			updateCheck.start();
		}
	}
}