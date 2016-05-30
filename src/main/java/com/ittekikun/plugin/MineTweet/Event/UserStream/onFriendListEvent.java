package com.ittekikun.plugin.minetweet.event.UserStream;

import com.ittekikun.plugin.minetweet.event.MineTweetEvent;

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
