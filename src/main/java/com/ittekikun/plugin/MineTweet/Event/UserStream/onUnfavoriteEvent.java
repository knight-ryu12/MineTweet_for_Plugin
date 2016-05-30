package com.ittekikun.plugin.minetweet.event.UserStream;

import com.ittekikun.plugin.minetweet.event.MineTweetEvent;
import twitter4j.Status;
import twitter4j.User;

public class onUnfavoriteEvent extends MineTweetEvent
{
    private User user;
    private User user1;
    private Status status;

    public onUnfavoriteEvent(User user, User user1, Status status)
    {
        user = this.user;
        user1 = this.user1;
        status = this.status;
    }

    public User getUser()
    {
        return user;
    }

    public User getUser1()
    {
        return user1;
    }

    public Status getStatus()
    {
        return status;
    }
}
