package com.ittekikun.plugin.MineTweet.Event.UserStream;

import com.ittekikun.plugin.MineTweet.Event.MineTweetEvent;
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