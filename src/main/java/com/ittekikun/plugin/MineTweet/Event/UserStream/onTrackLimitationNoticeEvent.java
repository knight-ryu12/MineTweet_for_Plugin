package com.ittekikun.plugin.minetweet.event.UserStream;

import com.ittekikun.plugin.minetweet.event.MineTweetEvent;

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
