package com.ittekikun.plugin.MineTweet.Config;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;

import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Utility;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigAccessor
{
	private final String fileName;
	private final JavaPlugin plugin;

	private File configFile;
	private FileConfiguration fileConfiguration;

	public ConfigAccessor(JavaPlugin plugin, String fileName)
	{
		if(plugin == null)
		{
			throw new IllegalArgumentException("plugin cannot be null");
		}
		//代替メソッドわからない
		if(!plugin.isInitialized())
		{
			throw new IllegalArgumentException("plugin must be initialized");
		}
		this.plugin = plugin;
		this.fileName = fileName;
		File dataFolder = plugin.getDataFolder();
		if(dataFolder == null)
		{
			throw new IllegalStateException();
		}
		this.configFile = new File(plugin.getDataFolder(), fileName);
	}

	//必要なことが分かった
	public void reloadConfig()
	{
		fileConfiguration = YamlConfiguration.loadConfiguration(configFile);
	}

	public FileConfiguration getConfig()
	{
		if(fileConfiguration == null)
		{
			this.reloadConfig();
		}
		return fileConfiguration;
	}

	public void saveConfig()
	{
		if(fileConfiguration == null || configFile == null)
		{
			return;
		}
		else
		{
			try
			{
				getConfig().save(configFile);
			}
			catch(IOException ex)
			{
				MineTweet.log.severe("Could not save config to " + configFile);
				ex.printStackTrace();
			}
		}
	}

	public void saveDefaultConfig()
	{
		if(!configFile.exists())
		{
			if(MineTweet.isV19)
			{
				Utility.copyRawFileFromJar(MineTweet.instance.getPluginJarFile(), configFile, fileName);
			}
			else
			{
				Utility.copyFileFromJar(MineTweet.instance.getPluginJarFile(), configFile, fileName);
			}
		}
	}
}