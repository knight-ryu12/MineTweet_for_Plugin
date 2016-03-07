package com.ittekikun.plugin.MineTweet;

import com.ittekikun.plugin.MineTweet.Command.BaseCommand;
import com.ittekikun.plugin.MineTweet.Command.ConfigReloadCommand;
import com.ittekikun.plugin.MineTweet.Command.HelpCommand;
import com.ittekikun.plugin.MineTweet.Command.TweetCommand;
import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.Listener.RegistrationListener;
import com.ittekikun.plugin.MineTweet.Twitter.BotManager;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MineTweet extends JavaPlugin
{
	public static MineTweet instance;
    public static Logger log;
	public static final String prefix = "[MineTweet] ";
	public static PluginManager pluginManager;
	public static boolean forceDisableMode;
	public MineTweetConfig mtConfig;
	public TwitterManager twitterManager;
	public BotManager botManager;

	private List<BaseCommand> commands = new ArrayList<BaseCommand>();

	public static final String KEYWORD_USER = "$user";
	public static final String KEYWORD_REASON = "$reason";
	public static final String KEYWORD_SENDER = "$sender";
	public static final String KEYWORD_NUMBER = "$number";
	public static final String KEYWORD_CHANNEL = "$channel";
	public static final String KEYWORD_MESSAGE = "$message";
	public static final String KEYWORD_ACHIEVEMENT = "$achievement";
	public static final String KEYWORD_SERVICE = "$service";
	public static final String KEYWORD_TIME = "$time";
	public static final String KEYWORD_NEWLINE = "$newline";
	public static final String SOURCE_NEWLINE = "\n";

	public static boolean isV19;

    @Override
    public void onEnable()
    {
		String ver = getServer().getBukkitVersion();
		//念のために1.9.4まで拾えるように
		isV19 = (ver.startsWith("1.9-R") || ver.startsWith("1.9.1-R") || ver.startsWith("1.9.2-R") || ver.startsWith("1.9.3-R") || ver.startsWith("1.9.4-R"));

	    instance = this;
		pluginManager = instance.getServer().getPluginManager();

	    log = Logger.getLogger("MineTweet");
	    log.setFilter(new LogFilter(prefix));

		if(!(Double.parseDouble(System.getProperty("java.specification.version")) >= 1.7))
		{
			//JAVA6以前の環境では動きません
			log.severe("JAVA7以上がインストールされていません。");
			log.severe("プラグインを無効化します。");
			forceDisableMode = true;
			pluginManager.disablePlugin(this);

			return;
		}

	    mtConfig = new MineTweetConfig(this);
	    mtConfig.loadConfig();

		//上手く行かないので保留
//		//後からクラスパスに追加しようと模索しているが失敗する
//		//理由不明
//		Utility.copyFolderFromJar(getPluginJarFile(), new File(instance.getDataFolder(), "lib"), "lib");
//		ArrayList<String> fileList = new ArrayList<String>();
//		fileList.add("twitter4j-core-4.0.5-SNAPSHOT.jar");
//		fileList.add("twitter4j-stream-4.0.5-SNAPSHOT.jar");
//		fileList.add("twitter4j-async-4.0.5-SNAPSHOT.jar");
//
//		LibraryLoader TWITTER4J = new LibraryLoader(this, fileList, true);
//		TWITTER4J.load();

	    twitterManager = new TwitterManager(this);
	    twitterManager.startSetup();

	    botManager = new BotManager(this);
	    botManager.botSetup();

	    registerCommands();
	    RegistrationListener.registrationListener(instance);

		serverStartTweet();
    }

	@Override
	public void onDisable()
	{
		if(forceDisableMode)
		{
			return;
		}

		serverStopTweet();
	}

	private void serverStartTweet()
	{
		if (mtConfig.serverStartTweet)
		{
			try
			{
				twitterManager.tweet(mtConfig.start_message_temp);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void serverStopTweet()
	{
		if (mtConfig.serverStopTweet)
		{
			try
			{
				twitterManager.tweet(mtConfig.stop_message_temp);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}

	private void registerCommands()
	{
		commands.add(new HelpCommand());
		commands.add(new TweetCommand());
		commands.add(new ConfigReloadCommand());
	}

	//使わなくない？
	public ClassLoader getPluginClassLoader()
	{
		return this.getClassLoader();
	}

	public File getPluginJarFile()
	{
		return this.getFile();
	}

	public List<BaseCommand> getCommands()
	{
		return commands;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String args[])
	{
		if (cmd.getName().equalsIgnoreCase("mt") || cmd.getName().equalsIgnoreCase("minetweet"))
		{
			if(args.length == 0)
			{
				// 引数ゼロはヘルプ表示
				args = new String[]{"help"};
			}

			outer:
			for (BaseCommand command : commands.toArray(new BaseCommand[0]))
			{
				String[] cmds = command.getName().split(" ");
				for (int i = 0; i < cmds.length; i++){
					if (i >= args.length || !cmds[i].equalsIgnoreCase(args[i])){
						continue outer;
					}
					// 実行
					return command.run(this, sender, args, commandLabel);
				}
			}
			// 有効コマンドなし ヘルプ表示
			new HelpCommand().run(this, sender, args, commandLabel);
			return true;
		}
//		詰んだ
//		else if(cmd.getName().equalsIgnoreCase("tw") || cmd.getName().equalsIgnoreCase("tweet"))
//		{
//			new TweetCommand().run(this, sender, args, commandLabel);
//			return true;
//		}
		return false;
	}
}