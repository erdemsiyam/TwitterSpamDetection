package model;


import org.springframework.data.mongodb.core.mapping.Document;
import twitter4j.Status;
import twitter4j.User;
import twitterApi.Api;

import java.util.ArrayList;
import java.util.Date;

@Document(collection = "MUser")
public class MUser {
    private String id;

    String screenName;
    Date createdTime;
    String name;
    String location;
    String description;
    int followerCount;
    int friendsCounnt;
    int favouritesCount;
    String language;
    int statusCount;
    boolean isVerified;
    String profilImageUrl;
    String web;
    ArrayList<String> likedTweets; // burası için beğeni tweetleri çelen fonksiyon düzenlenecek. bunun icinde apide dinamik bir beğeni listesi çeken yapı kururlacak.
    String aramaId;

    public static MUser newUser(User user , MArama aramaObj)
    {


        String web=user.getURL();
        if(web == null)
        {
            web ="";
        }
        return new MUser(
                String.valueOf(user.getId()),
                user.getScreenName(),
                user.getCreatedAt(),
                user.getName(),
                user.getLocation(),
                user.getDescription(),
                user.getFollowersCount(),
                user.getFriendsCount(),
                user.getFavouritesCount(),
                user.getLang(),
                user.getStatusesCount(),
                user.isVerified(),
                user.getProfileImageURL(),
                web,
                null,
                aramaObj.getId()

        );
    }

    @Override
    public String toString() {
        return "MUser{" +
                "id='" + id + '\'' +
                ", screenName='" + screenName + '\'' +
                ", createdTime=" + createdTime +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                ", followerCount=" + followerCount +
                ", friendsCounnt=" + friendsCounnt +
                ", favouritesCount=" + favouritesCount +
                ", language='" + language + '\'' +
                ", statusCount=" + statusCount +
                ", isVerified=" + isVerified +
                ", profilImageUrl='" + profilImageUrl + '\'' +
                ", web='" + web + '\'' +
                ", likedTweets=" + likedTweets +
                ", aramaId='" + aramaId + '\'' +
                '}';
    }

    public MUser(String id, String screenName, Date createdTime, String name, String location, String description, int followerCount, int friendsCounnt, int favouritesCount, String language, int statusCount, boolean isVerified, String profilImageUrl, String web, ArrayList<String> likedTweets, String aramaId) {
        this.id = id;
        this.screenName = screenName;
        this.createdTime = createdTime;
        this.name = name;
        this.location = location;
        this.description = description;
        this.followerCount = followerCount;
        this.friendsCounnt = friendsCounnt;
        this.favouritesCount = favouritesCount;
        this.language = language;
        this.statusCount = statusCount;
        this.isVerified = isVerified;
        this.profilImageUrl = profilImageUrl;
        this.web = web;
        this.likedTweets = likedTweets;
        this.aramaId = aramaId;
    }

    public String getAramaId() {
        return aramaId;
    }

    public void setAramaId(String aramaId) {
        this.aramaId = aramaId;
    }

    public int getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(int followerCount) {
        this.followerCount = followerCount;
    }

    public ArrayList<String> getLikedTweets() {
        return likedTweets;
    }

    public void setLikedTweets(ArrayList<String> likedTweets) {
        this.likedTweets = likedTweets;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getFriendsCounnt() {
        return friendsCounnt;
    }

    public void setFriendsCounnt(int friendsCounnt) {
        this.friendsCounnt = friendsCounnt;
    }

    public int getFavouritesCount() {
        return favouritesCount;
    }

    public void setFavouritesCount(int favouritesCount) {
        this.favouritesCount = favouritesCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getStatusCount() {
        return statusCount;
    }

    public void setStatusCount(int statusCount) {
        this.statusCount = statusCount;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public String getProfilImageUrl() {
        return profilImageUrl;
    }

    public void setProfilImageUrl(String profilImageUrl) {
        this.profilImageUrl = profilImageUrl;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
}
