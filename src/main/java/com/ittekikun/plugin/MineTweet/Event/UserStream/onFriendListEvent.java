package com.ittekikun.plugin.MineTweet.Event.UserStream;

import com.ittekikun.plugin.MineTweet.Event.MineTweetEvent;

public class onFriendListEvent extends MineTweetEvent
{
    private long[] longs;

    public onFriendListEvent(long[] longs)
    {
        longs = this.longs;
    }

    public long[] getLongs()
    {
        return longs;
    }
}
