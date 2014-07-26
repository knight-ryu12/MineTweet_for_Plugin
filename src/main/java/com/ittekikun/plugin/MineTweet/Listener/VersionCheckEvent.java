package com.ittekikun.plugin.MineTweet.Listener;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import com.ittekikun.plugin.MineTweet.UpdateCheck;
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
		if(player.isOp() || player.hasPermission("MineTweet.info"))
		{
			double NowVer = Double.valueOf(plugin.getServer().getPluginManager().getPlugin("MineTweet_for_Plugin").getDescription().getVersion());

			//いつかUTF8に対応したいなって（動作確認済み）
			//↓
			//1.4より移行
			String url = "MineTweet_for_Plugin";

			Thread updateCheck = new Thread(new UpdateCheck(player,url,NowVer));
			updateCheck.start();
		}
	}
}