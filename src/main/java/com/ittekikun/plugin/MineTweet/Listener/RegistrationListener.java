package com.ittekikun.plugin.MineTweet.Listener;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.MineTweet;
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
					MineTweet.log.info("MCBansと連携しました。(BAN)");
				}
				else
				{
					MineTweet.log.warning("MCBansが導入されてないので連携を無効化します。(BAN)");
				}
			}
			else
			{
				MineTweet.log.warning("MCBansとの連携設定が正しく設定されていません。(BAN)");
			}
		}

		//MCBansとのKICK連携
		if(mtConfig.mcBansKickTweet)
		{
			if (pluginmanager.isPluginEnabled("MCBans") )
			{
				pluginmanager.registerEvents(new MCBansKICKEvent(plugin), plugin);
				MineTweet.log.info( "MCBansと連携しました。(KICK)");
			}
			else
			{
				MineTweet.log.warning("MCBansが導入されてないので連携を無効化します。(KICK)");
			}
		}

		//LunaChat連携
		if (mtConfig.lunaChatTweet)
		{
			if (pluginmanager.isPluginEnabled("LunaChat") )
			{
				pluginmanager.registerEvents(new LunaChatEvent(plugin), plugin);
				MineTweet.log.info("LunaChatと連携しました。");
			}
			else
			{
				MineTweet.log.warning("LunaChatが導入されてないので連携を無効化します。");
			}
		}
	}
}