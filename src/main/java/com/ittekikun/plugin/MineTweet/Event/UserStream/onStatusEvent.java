package com.ittekikun.plugin.minetweet.event.UserStream;

import com.ittekikun.plugin.minetweet.event.MineTweetEvent;
import twitter4j.Status;

public class onStatusEvent extends MineTweetEvent
{
    private Status status;

    public onStatusEvent(Status status)
    {
        status = this.status;
    }

    public Status getStatus()
    {
        return status;
    }
}