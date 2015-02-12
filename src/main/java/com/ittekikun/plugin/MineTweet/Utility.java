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
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;

public class Utility
{

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
			MineTweet.log.severe("何らかの理由でバージョンアップ確認サーバーにアクセスできませんでした。");
			MineTweet.log.severe("お手数ですが一度UpdateCheckをfalseにする事をおすすめします。");
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
			MineTweet.log.info("[Worldcast]["+world.getName()+"]: " + message);
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

		MineTweet.log.info("Received "+i+"players: "+message);
	}

	/**
	 * @author https://github.com/ucchyocean/ColorTeaming/blob/master/src/main/java/com/github/ucchyocean/ct/Utility.java
	 * 現在接続中のプレイヤーを全て取得する
	 * @return 接続中の全てのプレイヤー
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Player> getOnlinePlayers()
	{
		// CB179以前と、CB1710以降で戻り値が異なるため、
		// リフレクションを使って互換性を（無理やり）保つ。
		try
		{
			if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class)
			{
				Collection<?> temp = ((Collection<?>)Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				return new ArrayList<Player>((Collection<? extends Player>)temp);
			}
			else
			{
				Player[] temp = ((Player[])Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				ArrayList<Player> players = new ArrayList<Player>();
				for ( Player t : temp )
				{
					players.add(t);
				}
				return players;
			}
		}
		catch (NoSuchMethodException ex){} // never happen
		catch (InvocationTargetException ex){} // never happen
		catch (IllegalAccessException ex){} // never happen
		return new ArrayList<Player>();
	}

	public static void generationPlayerImage(String playerName,String message,File tweetImage) throws TwitterException
	{
		BufferedImage base = null;
		BufferedImage head = null;
		BufferedImage name = null;
		BufferedImage mes = null;
		try
		{
			base = new BufferedImage(600, 200, BufferedImage.TYPE_INT_BGR);

			head = ImageIO.read(new URL("https://minotar.net/avatar/" + playerName + "/200.png"));

			name = new BufferedImage(400, 100, BufferedImage.TYPE_INT_BGR);

			mes = new BufferedImage(400, 100, BufferedImage.TYPE_INT_BGR);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		//白く塗りつぶし
		Graphics2D baseGraphics = base.createGraphics();
		baseGraphics.setColor(Color.WHITE);
		baseGraphics.fillRect(0,0,600,200);

		//白く塗りつぶし
		Graphics2D nameGraphics = name.createGraphics();
		nameGraphics.setColor(Color.WHITE);
		nameGraphics.fillRect(0,0,400,100);

		//色々して文字列書き込み
		nameGraphics.setColor(Color.BLACK);
		Font nameFont = new Font("Monospaced",Font.PLAIN,50);
		nameGraphics.setFont(nameFont);
		nameGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		drawStringCenter(nameGraphics,400,100,playerName);

		//白く塗りつぶし
		Graphics2D mesGraphics = mes.createGraphics();
		mesGraphics.setColor(Color.WHITE);
		mesGraphics.fillRect(0,0,400,100);

		//色々して文字列書き込み
		mesGraphics.setColor(new Color(0,167,212));
		Font f = new Font("Monospaced",Font.BOLD,45);
		mesGraphics.setFont(f);
		mesGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		drawStringCenter(mesGraphics,400,100,message);//16文字まで

		//ベースに統合
		baseGraphics.drawImage(head, 0, 0, null);
		baseGraphics.drawImage(name, 200, 0, null);
		baseGraphics.drawImage(mes, 200, 100, null);

		//	ファイル保存
		try
		{
			ImageIO.write(base, "png", tweetImage);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void drawStringCenter(Graphics g, int x,int y, String text)
	{
		Rectangle size = new Rectangle(x, y);
		FontMetrics fm = g.getFontMetrics();
		Rectangle rectText = fm.getStringBounds(text, g).getBounds();
		int nx = (size.width - rectText.width) / 2;
		int ny = (size.height - rectText.height) / 2 + fm.getMaxAscent();
		// Draw text
		g.drawString(text, nx, ny);
	}
}