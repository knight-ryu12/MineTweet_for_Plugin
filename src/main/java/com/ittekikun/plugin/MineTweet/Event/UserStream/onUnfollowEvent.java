package com.ittekikun.plugin.MineTweet.Event.UserStream;

import com.ittekikun.plugin.MineTweet.Event.MineTweetEvent;
import twitter4j.User;

public class onUnfollowEvent extends MineTweetEvent
{
    private User user;
    private User user1;

    public onUnfollowEvent(User user, User user1)
    {
        user = this.user;
        user1 = this.user1;
    }

    public User getUser()
    {
        return user;
    }

    public User getUser1()
    {
        return user1;
    }
}