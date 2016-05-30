package com.ittekikun.plugin.minetweet;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class Utility
{
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