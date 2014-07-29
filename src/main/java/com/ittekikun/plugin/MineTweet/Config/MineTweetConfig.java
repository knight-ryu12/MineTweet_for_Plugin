package com.ittekikun.plugin.MineTweet.Config;

import com.ittekikun.plugin.MineTweet.MineTweet;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MineTweetConfig
{
	public JavaPlugin plugin;

	public ConfigAccessor system;
	public ConfigAccessor twitter;
	public ConfigAccessor bot;

	public String consumerKey;
	public String consumerSecret;
	public String accessToken;
	public String accessTokenSecret;

	public Boolean GUICertify;

	public String dateformat;

	public Boolean serverStartTweet;
	public Boolean serverStopTweet;

	public String start_message_temp;
	public String stop_message_temp;

	public String join_message_temp;
	public String quit_message_temp;

	public String CC_message_temp;
	public String CD_message_temp;

	public String kick_message_temp;
	public String ban_message_temp;

	public String cmd_message_temp;

	public Boolean versionCheck;

	public Boolean playerJoinTweet;
	public Boolean playerQuitTweet;

	public int mcBansBanTweet;
	public Boolean mcBansKickTweet;

	public Boolean lunaChatTweet;

	public Boolean achievementAwardedTweet;
	public String achievement_message_temp;

	public Boolean votifierReceiveTweetTweet;
	public String votifier_message_temp;

	public Boolean zyari;//例のアレ;

	public Boolean useBot;
	public List<String> botMessageList;
	public int TweetCycle;

	//public List<String> UserList;

	public Boolean debugMode;

	public MineTweetConfig(MineTweet plugin)
	{
		this.plugin = plugin;
	}

	public void loadConfig()
	{
		system = new ConfigAccessor(plugin , "main.yml");
		twitter = new ConfigAccessor(plugin ,"twitter.yml");
		bot = new ConfigAccessor(plugin ,"bot.yml");

		system.saveDefaultConfig();
		twitter.saveDefaultConfig();
		bot.saveDefaultConfig();

		this.GUICertify = system.getConfig().getBoolean("GUICertify",true);
		this.consumerKey = system.getConfig().getString("consumerKey","xxxxxxxxxx");
		this.consumerSecret = system.getConfig().getString("consumerSecret","xxxxxxxxxx");
		this.accessToken = system.getConfig().getString("accessToken","xxxxxxxxxx");
		this.accessTokenSecret = system.getConfig().getString("accessTokenSecret","xxxxxxxxxx");

		this.versionCheck = system.getConfig().getBoolean("VersionCheck",true);

		this.debugMode = system.getConfig().getBoolean("DebugMode",false);

		this.dateformat = twitter.getConfig().getString("DateFormat", "EEE MMM d HH:mm:ss z");

		this.playerJoinTweet = twitter.getConfig().getBoolean("PlayerJoinTweet");
		this.join_message_temp = twitter.getConfig().getString("JoinMessageTemplate", "$userさんがサーバーにログインしました。現在$number人がログインしています。【自動投稿】");
		this.playerQuitTweet = twitter.getConfig().getBoolean("PlayerQuitTweet");
		this.quit_message_temp = twitter.getConfig().getString("QuitMessageTemplate", "$userさんがサーバーからログアウトしました。現在$number人がログインしています。【自動投稿】");

		this.serverStartTweet = twitter.getConfig().getBoolean("ServerStartTweet");
		this.start_message_temp = twitter.getConfig().getString("ServerStartTemplate", "サーバーを起動しました。【自動投稿】");
		this.serverStopTweet = twitter.getConfig().getBoolean("ServerStopTweet");
		this.stop_message_temp = twitter.getConfig().getString("ServerStopTemplate", "サーバーが停止しました。【自動投稿】");

		this.lunaChatTweet = twitter.getConfig().getBoolean("LunaChatTweet");
		this.CC_message_temp = twitter.getConfig().getString("ChannelCreateTemplate", "チャットチャンネル「$channel」が作成されました。【自動投稿】");
		this.CD_message_temp = twitter.getConfig().getString("ChannelDeleteTemplate", "チャットチャンネル「$channel」が削除されました。【自動投稿】");

		this.mcBansBanTweet = twitter.getConfig().getInt("MCBansBANTweet");
		this.ban_message_temp = twitter.getConfig().getString("BanMessageTemplate", "$userさんが、「$reason」という理由で、$senderによってグローバルBANされました。【自動投稿】");
		this.mcBansKickTweet = twitter.getConfig().getBoolean("MCBansKICKTweet");
		this.kick_message_temp = twitter.getConfig().getString("KickMessageTemplate", "$userさんが、「$reason」という理由で、$senderによってKICKされました。【自動投稿】");

		this.achievementAwardedTweet = twitter.getConfig().getBoolean("AchievementAwardedTweet");
		this.achievement_message_temp = twitter.getConfig().getString("AchievementAwardedTemplate", "$userさんが、「$achievement」という実績を取得しました。【自動投稿】");

		this.votifierReceiveTweetTweet = twitter.getConfig().getBoolean("VotifierReceiveTweet");
		this.votifier_message_temp = twitter.getConfig().getString("VotifierReceiveTemplate", "$userさんが、「$service」で当鯖に投票しました。【自動投稿】");

		this.cmd_message_temp = twitter.getConfig().getString("CommandTweetTemplate", "(サーバーから$userが投稿) $message");

		this.zyari = twitter.getConfig().getBoolean("Zyari",false);//例のアレ

		this.useBot = bot.getConfig().getBoolean("UseBot");
		this.botMessageList = bot.getConfig().getStringList("BotMessageList");
		this.TweetCycle = bot.getConfig().getInt("TweetCycle");

		//this.UserList = twitter.getConfig().getStringList("UserList");
	}
}