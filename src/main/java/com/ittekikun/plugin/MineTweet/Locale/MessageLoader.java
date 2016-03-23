package com.ittekikun.plugin.MineTweet.Locale;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;

import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Utility;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class MessageLoader
{
    private final String fileName;
    private String fullFileName;
    private final String folderName;
    private final File target;
    private final JavaPlugin plugin;

    private File messageFile;
    private FileConfiguration fileConfiguration;

    private String language;

    public MessageLoader(JavaPlugin plugin, String folderName, String fileName, String language)
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
        this.language = language;
        this.folderName = folderName;

        File dataFolder = plugin.getDataFolder();

        if(dataFolder == null)
        {
            throw new IllegalStateException();
        }

        this.target = new File(plugin.getDataFolder(), folderName);

        fullFileName = this.fileName + "_" + this.language + ".yml";
        this.messageFile = new File(target, fullFileName);
    }

    //必要なことが分かった
    public void reloadConfig()
    {
        fileConfiguration = YamlConfiguration.loadConfiguration(messageFile);
    }

    public FileConfiguration getConfig()
    {
        if(fileConfiguration == null)
        {
            this.reloadConfig();
        }
        return fileConfiguration;
    }

    public void saveMessages()
    {
        if(!target.exists())
        {
            if(MineTweet.isV19)
            {
                Utility.copyRawFolderFromJar(MineTweet.instance.getPluginJarFile(), MineTweet.instance.getDataFolder(), folderName);
            }
            else
            {
                Utility.copyFolderFromJar(MineTweet.instance.getPluginJarFile(), MineTweet.instance.getDataFolder(), folderName);
            }
        }

        if(!messageFile.exists())
        {
            //選択した言語がなかったら強制的に英語にする
            fullFileName = this.fileName + "_" + "en" + ".yml";
            this.messageFile = new File(target, fullFileName);
        }
    }

    public String loadMessage(String source)
    {
        //System.out.println(messageFile.getName());
        String name = getConfig().getString(source, source);
        return name;
    }
}