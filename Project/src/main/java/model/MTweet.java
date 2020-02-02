package model;

import org.springframework.data.mongodb.core.mapping.Document;
import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitterApi.Api;
import twitterApi.StatusTipi;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "MTweet")
public class MTweet {
    private String id;
    String text;
    String userScreenName;
    Date createdDate;
    String device;
    StatusTipi statusType;
    String relatedStatusId;
    int favoriteCount;
    int retweetCount;
    String location;
    String language;
    List<String> hashtags;
    String aramaId;


    public static MTweet newTweet(Status status,MArama aramaObj)
    {
        // Tweet tipi
        String tipTweetId;
        StatusTipi tipim = Api.tweetTipSorgula(status);
        if (tipim == StatusTipi.Alıntı )
        {
            tipTweetId = String.valueOf(status.getQuotedStatusId());
        }
        else if (tipim == StatusTipi.Retweet)
        {
            tipTweetId = String.valueOf(status.getRetweetedStatus().getId());
        }
        else if(tipim == StatusTipi.Reply)
        {
            tipTweetId = String.valueOf(status.getInReplyToStatusId());
        }
        else
        {
            tipTweetId="";
        }

        // Hashtag entity
        List<String> hasts = new ArrayList<String>();
        for (HashtagEntity hash : status.getHashtagEntities() )
        {
            hasts.add(hash.getText());
        }

        // Location
        String loca ="";
        if (status.getPlace() != null)
        {
            loca=status.getPlace().getFullName();
        }

        return new MTweet(
                String.valueOf(status.getId()),
                status.getText(),
                status.getUser().getScreenName(),
                status.getCreatedAt(),
                Api.tweetAtilanPlatform(status),
                tipim,
                tipTweetId,
                status.getFavoriteCount(),
                status.getRetweetCount(),
                loca,
                status.getLang(),
                hasts,
                aramaObj.getId()

        ) ;
    }



    @Override
    public String toString() {
        return "MTweet{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", userScreenName='" + userScreenName + '\'' +
                ", createdDate=" + createdDate +
                ", device='" + device + '\'' +
                ", statusType=" + statusType +
                ", relatedStatusId='" + relatedStatusId + '\'' +
                ", favoriteCount=" + favoriteCount +
                ", retweetCount=" + retweetCount +
                ", location='" + location + '\'' +
                ", language='" + language + '\'' +
                ", hashtags=" + hashtags +
                ", aramaId='" + aramaId + '\'' +
                '}';
    }

    public MTweet(String id, String text, String userScreenName, Date createdDate, String device, StatusTipi statusType, String relatedStatusId, int favoriteCount, int retweetCount, String location, String language, List<String> hashtags, String aramaId) {
        this.id = id;
        this.text = text;
        this.userScreenName = userScreenName;
        this.createdDate = createdDate;
        this.device = device;
        this.statusType = statusType;
        this.relatedStatusId = relatedStatusId;
        this.favoriteCount = favoriteCount;
        this.retweetCount = retweetCount;
        this.location = location;
        this.language = language;
        this.hashtags = hashtags;
        this.aramaId = aramaId;
    }

    public String getAramaId() {
        return aramaId;
    }

    public void setAramaId(String aramaId) {
        this.aramaId = aramaId;
    }


    public StatusTipi getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusTipi statusType) {
        this.statusType = statusType;
    }

    public String getRelatedStatusId() {
        return relatedStatusId;
    }

    public void setRelatedStatusId(String relatedStatusId) {
        this.relatedStatusId = relatedStatusId;
    }

    public List<String> getHashtags() {
        return hashtags;
    }

    public void setHashtags(List<String> hashtags) {
        this.hashtags = hashtags;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserScreenName() {
        return userScreenName;
    }

    public void setUserScreenName(String userScreenName) {
        this.userScreenName = userScreenName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public int getFavoriteCount() {
        return favoriteCount;
    }

    public void setFavoriteCount(int favoriteCount) {
        this.favoriteCount = favoriteCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

}
