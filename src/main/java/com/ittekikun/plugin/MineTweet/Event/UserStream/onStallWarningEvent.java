package com.ittekikun.plugin.minetweet.event.UserStream;

import com.ittekikun.plugin.minetweet.event.MineTweetEvent;
import twitter4j.StallWarning;

public class onStallWarningEvent extends MineTweetEvent
{
    private StallWarning stallWarning;

    public onStallWarningEvent(StallWarning stallWarning)
    {
        stallWarning = this.stallWarning;
    }

    public StallWarning getStallWarning()
    {
        return stallWarning;
    }
}
