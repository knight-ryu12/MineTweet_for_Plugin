package com.ittekikun.plugin.minetweet.event.UserStream;

import com.ittekikun.plugin.minetweet.event.MineTweetEvent;
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