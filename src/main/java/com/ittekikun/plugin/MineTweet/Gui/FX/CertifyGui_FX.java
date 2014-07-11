package com.ittekikun.plugin.MineTweet.Gui.FX;

import com.ittekikun.plugin.MineTweet.MineTweet;
import com.ittekikun.plugin.MineTweet.Twitter.TwitterManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import twitter4j.Twitter;
import twitter4j.auth.RequestToken;

public class CertifyGui_FX extends Application
{
	public MineTweet plugin;
	public RequestToken requestToken;
	public Twitter twitter;
	public TwitterManager twitterManager;

	public static Label label1;
	public static Label label2;
	public static Label label3;
	public static Label label4;

	public static TextField field1;


	public CertifyGui_FX(MineTweet plugin)
	{
		this.plugin = plugin;
		this.twitter = plugin.twitterManager.twitter;
		this.twitterManager = plugin.twitterManager;
	}

	@Override
	public void start(Stage primaryStage) throws Exception
	{
		//????
//		FXMLLoader loader = new FXMLLoader();
//		GridPane root = (GridPane)loader.load(getClass().getResourceAsStream("Sample.fxml"));
//		loader.setController(this);
//		Controller controller = loader.getController();
		GridPane root = FXMLLoader.load(getClass().getResource("window.fxml"));
		primaryStage.setTitle("MineTweetアクセストークン取得ウィンドウ(FX)");
		Scene scene = new Scene(root);
		String style = CertifyGui_FX.class.getResource("style.css").toExternalForm();
		scene.getStylesheets().add(style);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>()
		{
			@Override
			public void handle(WindowEvent t)
			{
				//何もしない
			}
		});
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.show();
	}

	//詰んだ
	public void openGui()
	{
		String[] args = new String[0];
		launch(args);
	}


	@FXML
	public void button1(ActionEvent event)
	{
		label4.setText("○");
		if(field1.getText().length() == 7)
		{
			if(isInteger(field1.getText()))
			{
				label4.setText("○");
			}
		}
		label4.setText("×");
	}

	@FXML
	public void button2(ActionEvent event)
	{

	}

	@FXML
	public void button3(ActionEvent event)
	{
		Platform.exit();
	}

	@FXML
	public void field1(ActionEvent event)
	{

	}
	/**
	 * 入力された文字が数字(Integer)か調べる
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
}