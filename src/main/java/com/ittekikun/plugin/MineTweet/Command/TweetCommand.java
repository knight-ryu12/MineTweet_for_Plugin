package com.ittekikun.plugin.MineTweet.Command;

import com.ittekikun.plugin.MineTweet.Command.BaseCommand;
import com.ittekikun.plugin.MineTweet.Data.Permission;
import com.ittekikun.plugin.MineTweet.Exception.CommandException;
import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import com.ittekikun.plugin.MineTweet.Utility;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class TweetCommand extends BaseCommand
{
	public TweetCommand()
	{
		bePlayer = false;
		name = "tw";
		argLength = 1;
		usage = "(text)";
	}

	@Override
	public void execute() throws CommandException
	{
		test();
//		if(TwitterManager.status)
//		{
//			String[] array = (String[])args.toArray(new String[0]);
//			String mes = Utility.arrayUnion(array, 0);
//
//			if(sender instanceof Player)
//			{
//				String name = player.getName();
//
//				String Message = replaceKeywords(mtConfig.cmd_message_temp, name,mes);
//
//				try
//				{
//					twitterManager.tweet(Message);
//				}
//				catch(TwitterException e)
//				{
//					e.printStackTrace();
//				}
//				Utility.message(sender, "&bツイートに成功しました。");
//				player.playSound(player.getLocation(), Sound.LEVEL_UP, 10, 1);
//			}
//			else
//			{
//				String Message = replaceKeywords(mtConfig.cmd_message_temp, "コンソール",mes);
//
//				try
//				{
//					twitterManager.tweet(Message);
//				}
//				catch(TwitterException e)
//				{
//					e.printStackTrace();
//				}
//
//				Utility.message(sender, "&bツイートに成功しました。");
//			}
//		}
//		else
//		{
//			Utility.message(sender, "&cまだ認証を行っていないのでツイート出来ません！");
//		}
	}

	private String replaceKeywords(String source,String name, String message)
	{
		String result = source;
		if (result.contains(MineTweet.KEYWORD_USER))
		{
			result = result.replace(MineTweet.KEYWORD_USER, name);
		}
		if ( result.contains(MineTweet.KEYWORD_MESSAGE ) )
		{
			result = result.replace(MineTweet.KEYWORD_MESSAGE , message);
		}
		return result;
	}

	public void test()
	{
		BufferedImage base = null;
		BufferedImage head = null;
		BufferedImage name = null;
		BufferedImage mes = null;
		try
		{
			base = new BufferedImage(600, 200, BufferedImage.TYPE_INT_BGR);

			head = ImageIO.read(new URL("https://minotar.net/avatar/ittekikun/200.png"));

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
		drawStringCenter(nameGraphics,400,100,"ittekikun");

		//白く塗りつぶし
		Graphics2D mesGraphics = mes.createGraphics();
		mesGraphics.setColor(Color.WHITE);
		mesGraphics.fillRect(0,0,400,100);

		//色々して文字列書き込み
		mesGraphics.setColor(new Color(0,167,212));
		Font f = new Font("Monospaced",Font.BOLD,45);
		mesGraphics.setFont(f);
		mesGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		drawStringCenter(mesGraphics,400,100,"JOINED NOW!");

		//ベースに統合
		baseGraphics.drawImage(head, 0, 0, null);
		baseGraphics.drawImage(name, 200, 0, null);
		baseGraphics.drawImage(mes, 200, 100, null);

//		//	ファイル保存
//		try{
//			ImageIO.write(base, "png", new File(plugin.getDataFolder(), "temp.png"));
//		}catch(Exception e){
//			e.printStackTrace();
//		}

		System.out.println("終わりました");

		final StatusUpdate status = new StatusUpdate("ittekikunさんがサーバーにログインしました。(自動投稿)");
		status.media(new File(plugin.getDataFolder(), "temp.png"));

		try{
			twitterManager.twitter.updateStatus(status);
		}catch(TwitterException e){
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

//	public void test()
//	{
//		BufferedImage image = null;
//
//		//	ファイル読み込み
//		try
//		{
//			image = new BufferedImage(500,500,BufferedImage.TYPE_INT_BGR);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		Graphics graphics = image.createGraphics();
//
//		//	いたずら書き
//		graphics.setColor(Color.RED);
//		Font f = new Font("Dialog",Font.BOLD,24);
//		graphics.setFont(f);
//		graphics.drawString(Utility.simpleTimeGetter(),50,50);
//
//		//	ファイル保存
//		try
//		{
//			ImageIO.write(image, "png", new File(plugin.getDataFolder(), "temp.png"));
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//		}
//
//		System.out.println("終わりました");
//
//		final StatusUpdate status = new StatusUpdate("画像生成してみてる");
//		status.media(new File(plugin.getDataFolder(),"temp.png"));
//
//		try{
//			twitterManager.twitter.updateStatus(status);
//		}catch(TwitterException e){
//			e.printStackTrace();
//		}
//	}

	@Override
	public boolean permission()
	{
		return Permission.TWEET.has(sender);
	}
}