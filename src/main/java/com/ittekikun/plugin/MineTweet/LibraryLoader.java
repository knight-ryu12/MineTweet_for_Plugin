package com.ittekikun.plugin.minetweet;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

public class LibraryLoader
{
    public MineTweet plugin;
    public ArrayList<String> fileList;
    public boolean required;

    public LibraryLoader(MineTweet plugin, ArrayList fileList)
    {
        this(plugin, fileList, false);
    }

    public LibraryLoader(MineTweet plugin, ArrayList fileList, boolean required)
    {
        this.plugin = plugin;
        this.fileList = fileList;
        this.required = required;
    }

    public void load()
    {
        File libraryDirectory = new File(plugin.getDataFolder() ,"lib");
        File libraryFile = null;

        for (String fileName : fileList)
        {
            libraryFile = new File(libraryDirectory, fileName);
            try
            {
                //後からクラスパスに追加しようと模索しているが失敗する
                //理由不明
                addClassPath(getJarUrl(libraryFile));
                MineTweet.log.severe(libraryFile.getCanonicalPath());
            }
            catch (Exception e)
            {
                MineTweet.log.severe("ライブラリー:" + fileName + " のロードに失敗しました。");

                if(required)
                {
                    MineTweet.log.severe("プラグインを無効化します。");

                    MineTweet.forceDisableMode = true;
                    MineTweet.pluginManager.disablePlugin(plugin);
                }
                e.printStackTrace();
                return;
            }
        }
    }

    public static URL getJarUrl(final File file) throws IOException
    {
        return new URL("jar:" + file.toURI().toURL().toExternalForm() + "!/");
    }

    private void addClassPath(final URL url) throws IOException
    {
        final URLClassLoader sysloader = (URLClassLoader) plugin.getPluginClassLoader();
        final Class<URLClassLoader> sysclass = URLClassLoader.class;
        try
        {
            final Method method = sysclass.getDeclaredMethod("addURL",new Class[] { URL.class });
            method.setAccessible(true);
            method.invoke(sysloader, new Object[] { url });
        }
        catch (final Throwable t)
        {
            t.printStackTrace();
            throw new IOException("Error adding " + url+ " to system classloader");
        }
    }

}