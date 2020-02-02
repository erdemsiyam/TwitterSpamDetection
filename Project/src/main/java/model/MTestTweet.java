package model;

import org.springframework.data.mongodb.core.mapping.Document;
import twitter4j.Status;
import twitterApi.TestStatusType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

@Document(collection = "MTestTweet")
public class MTestTweet {
    private long id;
    int acount_age;
    int no_follower;
    int no_following;
    int no_userfavourites;
    int no_lists;
    int no_tweets;
    int no_retweets;
    int no_hashtag;
    int no_usermention;
    int no_urls;
    int no_char;
    int no_digits;
    String clas;
    String dbFlag;


    public static MTestTweet tweetToTestTweets(Status tweet, TestStatusType dbFlag){
        MTestTweet tweetim = new MTestTweet(tweet.getId(),
                getDayDiff(tweet.getUser().getCreatedAt().toString()),
                tweet.getUser().getFollowersCount(),
                tweet.getUser().getFriendsCount(),
                tweet.getUser().getFavouritesCount(),
                tweet.getUser().getListedCount(),
                tweet.getUser().getStatusesCount(),
                tweet.getRetweetCount(),
                tweet.getHashtagEntities().length,
                tweet.getUserMentionEntities().length,
                tweet.getURLEntities().length,
                tweet.getText().length(),
                getDigitCount(tweet.getText()),
               "spammer",
               dbFlag.toString());
        return tweetim;
    }

    private static int getDayDiff(String createDate){
        LocalDate date = LocalDate.parse(createDate, DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH));
        return Integer.parseInt(String.valueOf(ChronoUnit.DAYS.between(date, LocalDate.now())));
    }

    public String getDataRaw()
    {
        return acount_age+","+no_follower+","+no_following+","+no_userfavourites+","+no_lists+","+no_tweets+","+no_retweets+","+no_hashtag+","+no_usermention+","+no_urls+","+no_char+","+no_digits+","+clas;
    }

    private static int getDigitCount(String text){
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (Character.isDigit(text.charAt(i))) {
                count++;
            }
        }
        return count;
    }

    public MTestTweet(long id, int acount_age, int no_follower, int no_following, int no_userfavourites, int no_lists, int no_tweets, int no_retweets, int no_hashtag, int no_usermention, int no_urls, int no_char, int no_digits, String clas, String dbFlag) {
        this.id = id;
        this.acount_age = acount_age;
        this.no_follower = no_follower;
        this.no_following = no_following;
        this.no_userfavourites = no_userfavourites;
        this.no_lists = no_lists;
        this.no_tweets = no_tweets;
        this.no_retweets = no_retweets;
        this.no_hashtag = no_hashtag;
        this.no_usermention = no_usermention;
        this.no_urls = no_urls;
        this.no_char = no_char;
        this.no_digits = no_digits;
        this.clas = clas;
        this.dbFlag = dbFlag;
    }

    @Override
    public String toString() {
        return "MTestTweet{" +
                "id=" + id +
                ", acount_age=" + acount_age +
                ", no_follower=" + no_follower +
                ", no_following=" + no_following +
                ", no_userfavourites=" + no_userfavourites +
                ", no_lists=" + no_lists +
                ", no_tweets=" + no_tweets +
                ", no_retweets=" + no_retweets +
                ", no_hashtag=" + no_hashtag +
                ", no_usermention=" + no_usermention +
                ", no_urls=" + no_urls +
                ", no_char=" + no_char +
                ", no_digits=" + no_digits +
                ", clas='" + clas + '\'' +
                ", dbFlag='" + dbFlag + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAcount_age() {
        return acount_age;
    }

    public void setAcount_age(int acount_age) {
        this.acount_age = acount_age;
    }

    public int getNo_follower() {
        return no_follower;
    }

    public void setNo_follower(int no_follower) {
        this.no_follower = no_follower;
    }

    public int getNo_following() {
        return no_following;
    }

    public void setNo_following(int no_following) {
        this.no_following = no_following;
    }

    public int getNo_userfavourites() {
        return no_userfavourites;
    }

    public void setNo_userfavourites(int no_userfavourites) {
        this.no_userfavourites = no_userfavourites;
    }

    public int getNo_lists() {
        return no_lists;
    }

    public void setNo_lists(int no_lists) {
        this.no_lists = no_lists;
    }

    public int getNo_tweets() {
        return no_tweets;
    }

    public void setNo_tweets(int no_tweets) {
        this.no_tweets = no_tweets;
    }

    public int getNo_retweets() {
        return no_retweets;
    }

    public void setNo_retweets(int no_retweets) {
        this.no_retweets = no_retweets;
    }

    public int getNo_hashtag() {
        return no_hashtag;
    }

    public void setNo_hashtag(int no_hashtag) {
        this.no_hashtag = no_hashtag;
    }

    public int getNo_usermention() {
        return no_usermention;
    }

    public void setNo_usermention(int no_usermention) {
        this.no_usermention = no_usermention;
    }

    public int getNo_urls() {
        return no_urls;
    }

    public void setNo_urls(int no_urls) {
        this.no_urls = no_urls;
    }

    public int getNo_char() {
        return no_char;
    }

    public void setNo_char(int no_char) {
        this.no_char = no_char;
    }

    public int getNo_digits() {
        return no_digits;
    }

    public void setNo_digits(int no_digits) {
        this.no_digits = no_digits;
    }

    public String getClas() {
        return clas;
    }

    public void setClas(String clas) {
        this.clas = clas;
    }

    public String getDbFlag() {
        return dbFlag;
    }

    public void setDbFlag(String dbFlag) {
        this.dbFlag = dbFlag;
    }
}
