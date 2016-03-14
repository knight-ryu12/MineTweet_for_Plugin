package com.ittekikun.plugin.MineTweet.Event.UserStream;

import com.ittekikun.plugin.MineTweet.Event.MineTweetEvent;
import twitter4j.StatusDeletionNotice;

public class onDeletionNoticeEvent extends MineTweetEvent
{
    private StatusDeletionNotice statusDeletionNotice;
    private long l;
    private long l1;

    public onDeletionNoticeEvent(StatusDeletionNotice statusDeletionNotice)
    {
        statusDeletionNotice = this.statusDeletionNotice;
    }

    public onDeletionNoticeEvent(long l, long l1)
    {
        l = this.l;
        l1 = this.l1;
    }

    public StatusDeletionNotice getStatusDeletionNotice()
    {
        return statusDeletionNotice;
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
