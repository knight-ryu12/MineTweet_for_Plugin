package com.ittekikun.plugin.MineTweet.Twitter.Earthquake;

import twitter4j.Status;
import twitter4j.UserStreamAdapter;

public class EarthquakeStream extends UserStreamAdapter
{
    @Override
    public void onStatus(Status status)
    {
        System.out.println("onStatus @" + status.getUser().getScreenName() + " - " + status.getText());

        String csv = status.getText();
        String[] info = csv.split(",", -1);
    }
}