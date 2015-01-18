package com.ittekikun.plugin.MineTweet;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import twitter4j.TwitterException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;

public class Utility
{
	private static final Logger log = MineTweet.log;

	/**
	 * ArrayUnion
	 *
	 * @author  ittekikun
	 * @param  par1  繋げたい配列（配列String型）
	 * @param  par2  どこの配列から繋げたいか（int型）
	 */
	public static String arrayUnion(String[] par1, int par2)
	{
		StringBuilder sb = new StringBuilder();

		for (int a = par2; a < par1.length; ++a)
		{
			if (a > par2)
			{
				sb.append(" ");
			}

			String s = par1[a];

			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * timeGetter
	 *
	 * @author  ittekikun
	 * @param  format 出力する時刻のフォーマット（String）
	 * @return 指定したフォーマットの形で現時刻
	 */
	public static String timeGetter(String format)
	{
		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String time = sdf.format(date);

		return time;
	}

	/**
	 * HTTPサーバー上のテキストの内容を読み込む
	 * @param par1 URL
	 * @return テキストをListで返す
	 */
	public static String[] getHttpServerText(String par1)
	{
		try
		{
			URL url = new URL(par1);
			InputStream i = url.openConnection().getInputStream();

			//いつかUTF8に対応したいなって（動作確認済み）
			//↓
			//1.4より移行
			BufferedReader buf = new BufferedReader(new InputStreamReader(i ,"UTF-8"));

			//BufferedReader buf = new BufferedReader(new InputStreamReader(i));

			String line = null;
			int l = 0;
			String[] strarray = new String[1000];
			while((line = buf.readLine()) != null)
			{
				strarray[l] = line;
				l++;
			}
			buf.close();
			return strarray;
		}
		catch (IOException e)
		{
			log.severe("何らかの理由でバージョンアップ確認サーバーにアクセスできませんでした。");
			log.severe("お手数ですが一度UpdateCheckをfalseにする事をおすすめします。");
			e.printStackTrace();
		}
		return null;
	}

	public static String simpleTimeGetter()
	{
		Calendar calendar = Calendar.getInstance();
		String Time = calendar.getTime().toString();

		return Time;
	}

	/**
	 * jarファイルの中に格納されているテキストファイルを、jarファイルの外にコピーするメソッド<br/>
	 * WindowsだとS-JISで、MacintoshやLinuxだとUTF-8で保存されます。
	 * @param jarFile jarファイル
	 * @param targetFile コピー先
	 * @param sourceFilePath コピー元
	 */
	public static void copyFileFromJar(File jarFile, File targetFile, String sourceFilePath)
	{
		JarFile jar = null;
		InputStream is = null;
		FileOutputStream fos = null;
		BufferedReader reader = null;
		BufferedWriter writer = null;

		File parent = targetFile.getParentFile();
		if ( !parent.exists() ) {
			parent.mkdirs();
		}

		try {
			jar = new JarFile(jarFile);
			ZipEntry zipEntry = jar.getEntry(sourceFilePath);
			is = jar.getInputStream(zipEntry);

			fos = new FileOutputStream(targetFile);

			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			writer = new BufferedWriter(new OutputStreamWriter(fos));

			String line;
			while ((line = reader.readLine()) != null) {
				writer.write(line);
				writer.newLine();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if ( jar != null ) {
				try {
					jar.close();
				} catch (IOException e) {
					// do nothing.
				}
			}
			if ( writer != null ) {
				try {
					writer.flush();
					writer.close();
				} catch (IOException e) {
					// do nothing.
				}
			}
			if ( reader != null ) {
				try {
					reader.close();
				} catch (IOException e) {
					// do nothing.
				}
			}
			if ( fos != null ) {
				try {
					fos.flush();
					fos.close();
				} catch (IOException e) {
					// do nothing.
				}
			}
			if ( is != null ) {
				try {
					is.close();
				} catch (IOException e) {
					// do nothing.
				}
			}
		}
	}

	/**
	 * 渡された文字列が数字(Integer)か調べる
	 * 調べ方は雑
	 * @param num String
	 * @return 見ての通り
	 */
	@SuppressWarnings("unused")
	public static boolean isInteger(String num)
	{
		try
		{
			int n = Integer.parseInt(num);
			return true;
		}
		catch (NumberFormatException e)
		{
			return false;
		}
	}

	/**
	 * メッセージをユニキャスト
	 * @param message メッセージ
	 */
	public static void message(CommandSender sender, String message)
	{
		if (sender != null && message != null)
		{
			sender.sendMessage(message.replaceAll("&([0-9a-fk-or])", "\u00A7$1"));
		}
	}

	/**
	 * メッセージをブロードキャスト
	 * @param message メッセージ
	 */
	public static void broadcastMessage(String message)
	{
		if (message != null)
		{
			message = message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
			Bukkit.broadcastMessage(message);
		}
	}
	/**
	 * メッセージをワールドキャスト
	 * @param world
	 * @param message
	 */
	public static void worldcastMessage(World world, String message)
	{
		if (world != null && message != null)
		{
			message = message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
			for(Player player: world.getPlayers())
			{
				player.sendMessage(message);
			}
			log.info("[Worldcast]["+world.getName()+"]: " + message);
		}
	}
	/**
	 * メッセージをパーミッションキャスト(指定した権限ユーザにのみ送信)
	 * @param permission 受信するための権限ノード
	 * @param message メッセージ
	 */
	public static void permcastMessage(String permission, String message)
	{
		// OK
		int i = 0;
		for (Player player : Bukkit.getServer().getOnlinePlayers())
		{
			if (player.hasPermission(permission))
			{
				Utility.message(player, message);
				i++;
			}
		}

		log.info("Received "+i+"players: "+message);
	}


}