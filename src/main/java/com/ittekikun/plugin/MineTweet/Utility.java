package com.ittekikun.plugin.MineTweet;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import twitter4j.TwitterException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;

public class Utility
{

	/**
	 * ArrayUnion
	 *
	 * @param par1 繋げたい配列（配列String型）
	 * @param par2 どこの配列から繋げたいか（int型）
	 */
	public static String JoinArray(String[] par1, int par2)
	{
		StringBuilder stringBuilder = new StringBuilder();

		for (int a = par2; a < par1.length; ++a)
		{
			if (a > par2)
			{
				stringBuilder.append(" ");
			}

			String s = par1[a];

			stringBuilder.append(s);
		}
		return stringBuilder.toString();
	}

	/**
	 * timeGetter
	 *
	 * @param format 出力する時刻のフォーマット（String）
	 * @return 指定したフォーマットの形で現時刻
	 * @author ittekikun
	 */
	public static String timeGetter(String format)
	{
		Date date = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String time = sdf.format(date);

		return time;
	}

	public static String simpleTimeGetter()
	{
		Calendar calendar = Calendar.getInstance();
		String time = calendar.getTime().toString();

		return time;
	}


	/**
	 * HTTPサーバー上のテキストの内容を読み込む
	 *
	 * @param par1 URL
	 * @return テキストをListで返す
	 */
	public static String[] getHttpServerText(String par1)
	{
		try
		{
			URL url = new URL(par1);
			InputStream i = url.openConnection().getInputStream();

			BufferedReader buf = new BufferedReader(new InputStreamReader(i, "UTF-8"));

			String line = null;
			int l = 0;
			//これスマートじゃないので修正予定
			String[] strarray = new String[1000];
			while ((line = buf.readLine()) != null)
			{
				strarray[l] = line;
				l++;
			}
			buf.close();
			return strarray;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * jarファイルの中に格納されているテキストファイルを、jarファイルの外にコピーするメソッド<br/>
	 * ファイルをそのままコピーします。
	 *
	 * @param jarFile        jarファイル
	 * @param targetFile     コピー先
	 * @param sourceFilePath コピー元
	 */
	public static void copyRawFileFromJar(File jarFile, File targetFile, String sourceFilePath)
	{
		JarFile jar = null;
		InputStream is = null;

		File parent = targetFile.getParentFile();
		if (!parent.exists())
		{
			parent.mkdirs();
		}

		try
		{
			jar = new JarFile(jarFile);
			ZipEntry zipEntry = jar.getEntry(sourceFilePath);
			is = jar.getInputStream(zipEntry);

			Files.copy(is, targetFile.toPath());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (jar != null)
			{
				try
				{
					jar.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
		}
	}

	/**
	 * jarファイルの中に格納されているフォルダを、中のファイルごとまとめてjarファイルの外にコピーするメソッド<br/>
	 * テキストファイルは、そのままコピーされます。
	 *
	 * @author https://github.com/ucchyocean/
	 *
	 * @param jarFile        jarファイル
	 * @param targetFilePath コピー先のフォルダ
	 * @param sourceFilePath コピー元のフォルダ
	 */
	public static void copyRawFolderFromJar(File jarFile, File targetFilePath, String sourceFilePath)
	{

		JarFile jar = null;

		if (!targetFilePath.exists())
		{
			targetFilePath.mkdirs();
		}

		try
		{
			jar = new JarFile(jarFile);
			Enumeration<JarEntry> entries = jar.entries();

			while (entries.hasMoreElements())
			{

				JarEntry entry = entries.nextElement();
				if (!entry.isDirectory() && entry.getName().startsWith(sourceFilePath))
				{

					File targetFile = new File(targetFilePath, sourceFilePath);
					if (!targetFile.getParentFile().exists())
					{
						targetFile.getParentFile().mkdirs();
					}

					if(!targetFile.exists())
					{
						targetFile.mkdir();
					}

					File target = new File(targetFile, entry.getName().substring(sourceFilePath.length() + 1));

					InputStream is = null;

					try
					{
						is = jar.getInputStream(entry);

						Files.copy(is, target.toPath());
					}
					catch (FileNotFoundException e)
					{
						e.printStackTrace();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					finally
					{
						if (is != null)
						{
							try
							{
								is.close();
							}
							catch (IOException e)
							{
								// do nothing.
							}
						}
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (jar != null)
			{
				try
				{
					jar.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
		}

	}

	/**
	 * jarファイルの中に格納されているテキストファイルを、jarファイルの外にコピーするメソッド<br/>
	 * WindowsだとS-JISで、MacintoshやLinuxだとUTF-8で保存されます。
	 *
	 * @author https://github.com/ucchyocean/
	 *
	 * @param jarFile        jarファイル
	 * @param targetFile     コピー先
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
		if (!parent.exists())
		{
			parent.mkdirs();
		}

		try
		{
			jar = new JarFile(jarFile);
			ZipEntry zipEntry = jar.getEntry(sourceFilePath);
			is = jar.getInputStream(zipEntry);

			fos = new FileOutputStream(targetFile);

			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			writer = new BufferedWriter(new OutputStreamWriter(fos));

			String line;
			while ((line = reader.readLine()) != null)
			{
				writer.write(line);
				writer.newLine();
			}

		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (jar != null)
			{
				try
				{
					jar.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
			if (writer != null)
			{
				try
				{
					writer.flush();
					writer.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
			if (reader != null)
			{
				try
				{
					reader.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
			if (fos != null)
			{
				try
				{
					fos.flush();
					fos.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
		}
	}

	/**
	 * jarファイルの中に格納されているフォルダを、中のファイルごとまとめてjarファイルの外にコピーするメソッド<br/>
	 * テキストファイルは、WindowsだとS-JISで、MacintoshやLinuxだとUTF-8で保存されます。
	 *
	 * @author https://github.com/ucchyocean/
	 *
	 * @param jarFile        jarファイル
	 * @param targetFilePath コピー先のフォルダ
	 * @param sourceFilePath コピー元のフォルダ
	 */
	public static void copyFolderFromJar(File jarFile, File targetFilePath, String sourceFilePath)
	{

		JarFile jar = null;

		if (!targetFilePath.exists())
		{
			targetFilePath.mkdirs();
		}

		try
		{
			jar = new JarFile(jarFile);
			Enumeration<JarEntry> entries = jar.entries();

			while (entries.hasMoreElements())
			{

				JarEntry entry = entries.nextElement();
				if (!entry.isDirectory() && entry.getName().startsWith(sourceFilePath))
				{

					File targetFile = new File(targetFilePath, sourceFilePath);
					if (!targetFile.getParentFile().exists())
					{
						targetFile.getParentFile().mkdirs();
					}

					if(!targetFile.exists())
					{
						targetFile.mkdir();
					}

					File target = new File(targetFile, entry.getName().substring(sourceFilePath.length() + 1));

					InputStream is = null;
					FileOutputStream fos = null;
					BufferedReader reader = null;
					BufferedWriter writer = null;

					try
					{
						is = jar.getInputStream(entry);
						reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
						fos = new FileOutputStream(target);
						writer = new BufferedWriter(new OutputStreamWriter(fos));

						String line;
						while ((line = reader.readLine()) != null)
						{
							writer.write(line);
							writer.newLine();
						}

					}
					catch (FileNotFoundException e)
					{
						e.printStackTrace();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
					finally
					{
						if (writer != null)
						{
							try
							{
								writer.flush();
								writer.close();
							}
							catch (IOException e)
							{
								// do nothing.
							}
						}
						if (reader != null)
						{
							try
							{
								reader.close();
							}
							catch (IOException e)
							{
								// do nothing.
							}
						}
						if (fos != null)
						{
							try
							{
								fos.flush();
								fos.close();
							}
							catch (IOException e)
							{
								// do nothing.
							}
						}
						if (is != null)
						{
							try
							{
								is.close();
							}
							catch (IOException e)
							{
								// do nothing.
							}
						}
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (jar != null)
			{
				try
				{
					jar.close();
				}
				catch (IOException e)
				{
					// do nothing.
				}
			}
		}
	}

	/**
	 * 文字列が整数値に変換可能かどうかを判定する
	 * @param source 変換対象の文字列
	 * @return 整数に変換可能かどうか
	 *
	 * @author https://github.com/ucchyocean/
	 */
	public static boolean checkIntParse(String source)
	{

		return source.matches("^-?[0-9]{1,9}$");
	}


	/**
	 * メッセージをユニキャスト
	 *
	 * @param message メッセージ
	 */
	public static void message(CommandSender sender, String message)
	{
		if (sender != null && message != null)
		{
			sender.sendMessage(MineTweet.prefix + message.replaceAll("&([0-9a-fk-or])", "\u00A7$1"));
		}
	}

	/**
	 * メッセージをブロードキャスト
	 *
	 * @param message メッセージ
	 */
	public static void broadcastMessage(String message)
	{
		if (message != null)
		{
			message = message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
			Bukkit.broadcastMessage(MineTweet.prefix + message);
		}
	}

	/**
	 * メッセージをワールドキャスト
	 *
	 * @param world
	 * @param message
	 */
	public static void worldcastMessage(World world, String message)
	{
		if (world != null && message != null)
		{
			message = message.replaceAll("&([0-9a-fk-or])", "\u00A7$1");
			for (Player player : world.getPlayers())
			{
				player.sendMessage(MineTweet.prefix + message);
			}
			MineTweet.log.info(MineTweet.prefix + "[Worldcast][" + world.getName() + "]: " + message);
		}
	}

	/**
	 * メッセージをパーミッションキャスト(指定した権限ユーザにのみ送信)
	 *
	 * @param permission 受信するための権限ノード
	 * @param message    メッセージ
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

		MineTweet.log.info(MineTweet.prefix + "Received " + i + "players: " + message);
	}

	public static Player getPlayer(String name)
	{
		for (Player player : getOnlinePlayers())
		{
			if(player.getName().equals(name))
			{
				return player;
			}
		}
		return null;
	}

	/**
	 * @return 接続中の全てのプレイヤー
	 * @author https://github.com/ucchyocean/
	 * 現在接続中のプレイヤーを全て取得する
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
				Collection<?> temp = ((Collection<?>) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				return new ArrayList<Player>((Collection<? extends Player>) temp);
			}
			else
			{
				Player[] temp = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).invoke(null, new Object[0]));
				ArrayList<Player> players = new ArrayList<Player>();
				for (Player t : temp)
				{
					players.add(t);
				}
				return players;
			}
		}
		catch (NoSuchMethodException ex)
		{
			// never happen
		}
		catch (InvocationTargetException ex)
		{
			// never happen
		}
		catch (IllegalAccessException ex)
		{
			// never happen
		}
		return new ArrayList<Player>();
	}

	/**
	 * 指定したプレイヤーが手に持っているアイテムを返します。
	 * CB1.9以降と、CB1.8.8以前で、互換性を保つために使用します。
	 * @param player プレイヤー
	 * @return 手に持っているアイテム
	 */
	@SuppressWarnings("deprecation")
	public static ItemStack getItemInHand(Player player)
	{
		if (MineTweet.isV19)
		{
			return player.getInventory().getItemInMainHand();
		}
		else
		{
			return player.getItemInHand();
		}
	}

	public static void generationPlayerImage(String playerName, String message, File tweetImage)
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
		catch (Exception e)
		{
			e.printStackTrace();
		}

		//白く塗りつぶし
		Graphics2D baseGraphics = base.createGraphics();
		baseGraphics.setColor(Color.WHITE);
		baseGraphics.fillRect(0, 0, 600, 200);

		//白く塗りつぶし
		Graphics2D nameGraphics = name.createGraphics();
		nameGraphics.setColor(Color.WHITE);
		nameGraphics.fillRect(0, 0, 400, 100);

		//色々して文字列書き込み
		nameGraphics.setColor(Color.BLACK);
		Font nameFont = new Font("Monospaced", Font.PLAIN, 50);
		nameGraphics.setFont(nameFont);
		nameGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		drawStringCenter(nameGraphics, 400, 100, playerName);

		//白く塗りつぶし
		Graphics2D mesGraphics = mes.createGraphics();
		mesGraphics.setColor(Color.WHITE);
		mesGraphics.fillRect(0, 0, 400, 100);

		//色々して文字列書き込み
		mesGraphics.setColor(new Color(0, 167, 212));
		Font f = new Font("Monospaced", Font.BOLD, 45);
		mesGraphics.setFont(f);
		mesGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		drawStringCenter(mesGraphics, 400, 100, message);//16文字まで

		//ベースに統合
		baseGraphics.drawImage(head, 0, 0, null);
		baseGraphics.drawImage(name, 200, 0, null);
		baseGraphics.drawImage(mes, 200, 100, null);

		//	ファイル保存
		try
		{
			ImageIO.write(base, "png", tweetImage);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void drawStringCenter(Graphics g, int x, int y, String text)
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