package com.ittekikun.plugin.MineTweet.Event.UserStream;

import com.ittekikun.plugin.MineTweet.Event.MineTweetEvent;

public class onTrackLimitationNoticeEvent extends MineTweetEvent
{
    private int i;

    public onTrackLimitationNoticeEvent(int i)
    {
        i = this.i;
    }

    public int getI()
    {
        return i;
    }
}
