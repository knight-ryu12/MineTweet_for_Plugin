package com.ittekikun.plugin.MineTweet.Event.UserStream;

import com.ittekikun.plugin.MineTweet.Event.MineTweetEvent;
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