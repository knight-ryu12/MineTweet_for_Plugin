package com.ittekikun.plugin.MineTweet.Event.UserStream;

import com.ittekikun.plugin.MineTweet.Event.MineTweetEvent;

public class onScrubGeoEvent extends MineTweetEvent
{
    private long l;
    private long l1;

    public onScrubGeoEvent(long l, long l1)
    {
        l = this.l;
        l1 = this.l1;
    }

    public long getL()
    {
        return l;
    }

    public long getL1()
    {
        return l1;
    }
}
