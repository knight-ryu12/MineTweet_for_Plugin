package com.ittekikun.plugin.minetweet.event.UserStream;

import com.ittekikun.plugin.minetweet.event.MineTweetEvent;
import twitter4j.DirectMessage;

public class onDirectMessageEvent extends MineTweetEvent
{
    private DirectMessage directMessage;

    public onDirectMessageEvent(DirectMessage directMessage)
    {
        directMessage = this.directMessage;
    }

    public DirectMessage getDirectMessage()
    {
        return directMessage;
    }
}