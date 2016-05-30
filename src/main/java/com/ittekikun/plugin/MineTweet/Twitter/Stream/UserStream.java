package com.ittekikun.plugin.minetweet.twitter.stream;

import com.ittekikun.plugin.minetweet.MineTweetConfig;
import com.ittekikun.plugin.minetweet.event.UserStream.*;
import org.bukkit.Bukkit;
import twitter4j.*;

public class UserStream implements UserStreamListener
{
    public MineTweetConfig mtConfig;

    public UserStream(MineTweetConfig mtConfig)
    {
        this.mtConfig = mtConfig;
    }

    @Override
    public void onStatus(Status status)
    {
        onStatusEvent event = new onStatusEvent(status);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice)
    {
        onDeletionNoticeEvent event = new onDeletionNoticeEvent(statusDeletionNotice);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onTrackLimitationNotice(int i)
    {
        onTrackLimitationNoticeEvent event = new onTrackLimitationNoticeEvent(i);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onScrubGeo(long l, long l1)
    {
        onScrubGeoEvent event = new onScrubGeoEvent(l, l1);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onStallWarning(StallWarning stallWarning)
    {
        onStallWarningEvent event = new onStallWarningEvent(stallWarning);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onDeletionNotice(long l, long l1)
    {
        onDeletionNoticeEvent event = new onDeletionNoticeEvent(l, l1);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onFriendList(long[] longs)
    {
        onFriendListEvent event = new onFriendListEvent(longs);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onFavorite(User user, User user1, Status status)
    {
        onFavoriteEvent event = new onFavoriteEvent(user, user1, status);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onUnfavorite(User user, User user1, Status status)
    {
        onUnfavoriteEvent event = new onUnfavoriteEvent(user, user1, status);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onFollow(User user, User user1)
    {
        onFollowEvent event = new onFollowEvent(user, user1);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onUnfollow(User user, User user1)
    {
        onUnfollowEvent event = new onUnfollowEvent(user, user1);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onDirectMessage(DirectMessage directMessage)
    {
        onDirectMessageEvent event = new onDirectMessageEvent(directMessage);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    @Override
    public void onUserListMemberAddition(User user, User user1, UserList userList) {

    }

    @Override
    public void onUserListMemberDeletion(User user, User user1, UserList userList) {

    }

    @Override
    public void onUserListSubscription(User user, User user1, UserList userList) {

    }

    @Override
    public void onUserListUnsubscription(User user, User user1, UserList userList) {

    }

    @Override
    public void onUserListCreation(User user, UserList userList) {

    }

    @Override
    public void onUserListUpdate(User user, UserList userList) {

    }

    @Override
    public void onUserListDeletion(User user, UserList userList) {

    }

    @Override
    public void onUserProfileUpdate(User user) {

    }

    @Override
    public void onUserSuspension(long l) {

    }

    @Override
    public void onUserDeletion(long l) {

    }

    @Override
    public void onBlock(User user, User user1) {

    }

    @Override
    public void onUnblock(User user, User user1) {

    }

    @Override
    public void onException(Exception e) {

    }
}