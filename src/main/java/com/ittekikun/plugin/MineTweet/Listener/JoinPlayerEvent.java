package com.ittekikun.plugin.MineTweet.Listener;

import com.ittekikun.plugin.MineTweet.Config.MineTweetConfig;
import com.ittekikun.plugin.MineTweet.Gui.Swing.CertifyGui_Swing;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import com.ittekikun.plugin.MineTweet.Utility;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.PackedColorModel;
import java.io.File;
import java.net.URL;
import java.util.Collection;

public class JoinPlayerEvent implements Listener
{
	MineTweet plugin;
	MineTweetConfig mtConfig;
	TwitterManager twitterManager;

	public JoinPlayerEvent(MineTweet plugin)
	{
		this.plugin = plugin;
		this.mtConfig = plugin.mtConfig;
		this.twitterManager = plugin.twitterManager;
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void onJoinPlayer(final PlayerJoinEvent event) throws TwitterException
	{
		if(!mtConfig.debugMode)
		{
			Player player = event.getPlayer();
			String name = player.getName();

			if(plugin.isV18)
			{
				Collection member = plugin.getServer().getOnlinePlayers();
				String number = Integer.toString((member.size()));
			}
			else
			{
				//1.7の時はちゃうんや
				Collection member = plugin.getServer().getOnlinePlayers();
				String number = Integer.toString((member.size()));
			}

			String Message = replaceKeywords(mtConfig.join_message_temp, name, number);

			twitterManager.tweet(Message);
		}
		else
		{
			Player player = event.getPlayer();
			final String name = player.getName();

			Collection member = plugin.getServer().getOnlinePlayers();
			final String number = Integer.toString((member.size()));

			plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						generationPlayerImage(name, "JOINED THE GAME!");

						String message = replaceKeywords(mtConfig.join_message_temp, name, number);
						twitterManager.tweet(message,(new File(plugin.getDataFolder(), "temp.png")));
					}
					catch(TwitterException e)
					{
						e.printStackTrace();
					}
				}
			});
		}
	}

	private String replaceKeywords(String source,String name, String number)
	{
		String result = source;
        if ( result.contains(MineTweet.KEYWORD_USER) )
        {
            result = result.replace(MineTweet.KEYWORD_USER, name);
        }
        if ( result.contains(MineTweet.KEYWORD_NUMBER) )
        {
            result = result.replace(MineTweet.KEYWORD_NUMBER, number);
        }
        return result;
    }

	public static void generationPlayerImage(String playerName,String message) throws TwitterException
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
			ImageIO.write(base, "png", new File(MineTweet.instance.getDataFolder(), "temp.png"));
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