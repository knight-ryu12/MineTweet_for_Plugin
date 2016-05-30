package com.ittekikun.plugin.minetweet.listener;

import com.ittekikun.plugin.minetweet.MineTweetConfig;
import com.ittekikun.plugin.minetweet.MineTweet;
import org.bukkit.plugin.PluginManager;

public class RegistrationListener
{
	public static void registrationListener(MineTweet plugin)
	{
		PluginManager pluginmanager = plugin.getServer().getPluginManager();
		MineTweetConfig mtConfig = plugin.mtConfig;

		//バージョンチェック
		if (mtConfig.versionCheck)
		{
			pluginmanager.registerEvents(new VersionCheckEvent(plugin), plugin);
		}

		//ログイン
		if (mtConfig.playerJoinTweet)
		{
			pluginmanager.registerEvents(new JoinPlayerEvent(plugin), plugin);
		}

		//ログアウト
		if (mtConfig.playerQuitTweet)
		{
			pluginmanager.registerEvents(new QuitPlayerEvent(plugin), plugin);
		}

		//MCBANSとのBAN連携
		if(mtConfig.mcBansBanTweet != 0)
		{
			if(mtConfig.mcBansBanTweet >= 1 && mtConfig.mcBansBanTweet <= 3)
			{
				if (pluginmanager.isPluginEnabled("MCBans") )
				{
					pluginmanager.registerEvents(new MCBansBANEvent(plugin), plugin);
					MineTweet.log.info(plugin.messageLoader.loadMessage("system.link.complete") + " [MCBans(BAN)]");
				}
				else
				{
					MineTweet.log.warning(plugin.messageLoader.loadMessage("system.link.error.found") + " [MCBans(BAN)]");
				}
			}
			else
			{
				MineTweet.log.warning(plugin.messageLoader.loadMessage("system.link.error.setting") + " [MCBans(BAN)]");
			}
		}

		//MCBansとのKICK連携
		if(mtConfig.mcBansKickTweet)
		{
			if (pluginmanager.isPluginEnabled("MCBans") )
			{
				pluginmanager.registerEvents(new MCBansKICKEvent(plugin), plugin);
				MineTweet.log.info(plugin.messageLoader.loadMessage("system.link.complete") + " [MCBans(KICK)]");
			}
			else
			{
				MineTweet.log.warning(plugin.messageLoader.loadMessage("system.link.error.found") + " [MCBans(KICK)]");
			}
		}

		//LunaChat連携
		if (mtConfig.lunaChatTweet)
		{
			if (pluginmanager.isPluginEnabled("LunaChat") )
			{
				pluginmanager.registerEvents(new LunaChatEvent(plugin), plugin);
				MineTweet.log.info(plugin.messageLoader.loadMessage("system.link.complete") + " [LunaChat]");
			}
			else
			{
				MineTweet.log.warning(plugin.messageLoader.loadMessage("system.link.error.found") + " [LunaChat]");
			}
		}

		if (mtConfig.achievementAwardedTweet)
		{
			pluginmanager.registerEvents(new AchievementAwardedEvent(plugin), plugin);
		}

		if (mtConfig.votifierReceiveTweet)
		{
			if (pluginmanager.isPluginEnabled("Votifier"))
			{
				pluginmanager.registerEvents(new VotifierReceiveEvent(plugin), plugin);
				MineTweet.log.info(plugin.messageLoader.loadMessage("system.link.complete") + " [Votifier]");
			}
			else
			{
				MineTweet.log.warning(plugin.messageLoader.loadMessage("system.link.error.found") + " [Votifier]");
			}
		}

		if (mtConfig.playerDeathTweet)
		{
			pluginmanager.registerEvents(new DeathEvent(plugin), plugin);
		}

		if (mtConfig.playerDeathByPlayerTweet)
		{
			pluginmanager.registerEvents(new DeathByPlayerEvent(plugin), plugin);
		}

		if (mtConfig.zyari)
		{
			pluginmanager.registerEvents(new ZyariEvent(plugin), plugin);
		}
	}
}