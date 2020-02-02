package model;

import org.json.JSONObject;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Document(collection = "MTrainingTweet")
public class MTrainingTweet {
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

    public MTrainingTweet(long id, int acount_age, int no_follower, int no_following, int no_userfavourites, int no_lists, int no_tweets, int no_retweets, int no_hashtag, int no_usermention, int no_urls, int no_char, int no_digits, String clas, String dbFlag) {
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


    public String getDataRaw()
    {
        return acount_age+","+no_follower+","+no_following+","+no_userfavourites+","+no_lists+","+no_tweets+","+no_retweets+","+no_hashtag+","+no_usermention+","+no_urls+","+no_char+","+no_digits+","+clas;
    }
/*
    public static ArrayList<MTrainingTweet> superBowlToTrainerTweets(String txtPath) throws FileNotFoundException {
        ArrayList<MTrainingTweet> records = new ArrayList<MTrainingTweet>();
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(txtPath));
            String line;
            while ((line = reader.readLine()) != null)
            {
                String[] array = new String[14];
                JSONObject jsonObj = new JSONObject(line);

                array[0] = jsonObj.getString("id_str");
                array[1] = String.valueOf(getDayDiff(jsonObj.getString("created_at"))); // acoount age
                array[2] = String.valueOf(jsonObj.getJSONObject("user").getInt("followers_count"));
                array[3] = String.valueOf(jsonObj.getJSONObject("user").getInt("friends_count"));
                array[4] = String.valueOf(jsonObj.getJSONObject("user").getInt("favourites_count"));
                array[5] = String.valueOf(jsonObj.getJSONObject("user").getInt("listed_count"));
                array[6] = String.valueOf(jsonObj.getJSONObject("user").getInt("statuses_count"));
                array[7] = String.valueOf(jsonObj.getInt("retweet_count"));
                array[8] = String.valueOf(jsonObj.getJSONObject("entities").getJSONArray("hashtags").length());
                array[9] = String.valueOf(jsonObj.getJSONObject("entities").getJSONArray("user_mentions").length());
                array[10] = String.valueOf(jsonObj.getJSONObject("entities").getJSONArray("urls").length());
                array[11] = String.valueOf(jsonObj.getString("text").length());
                array[12] = String.valueOf(getDigitCount(jsonObj.getString("text"))); // digit
                array[13] = "spammer";

                MTrainingTweet tweet = new MTrainingTweet(array,"superbowllive");
                records.add(tweet);
            }
            reader.close();
        }
        catch (Exception e)
        {
            System.err.format("Exception occurred trying to read '%s'.", txtPath);
            e.printStackTrace();
        }
        finally {
            return records;
        }
    }*/
    private static int getDayDiff(String createDate){
        LocalDate date = LocalDate.parse(createDate, DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH));
        return Integer.parseInt(String.valueOf(ChronoUnit.DAYS.between(date, LocalDate.now())));
    }

    private static int getDigitCount(String text){
        int count = 0;
        for (int i = 0, len = text.length(); i < len; i++) {
            if (Character.isDigit(text.charAt(i))) {
                count++;
            }
        }
        return count;
    }

/*
    // icc db için yazılmış.
    public static List<MTrainingTweet> iccToTrainerTweets(String csvPath) {
        List<MTrainingTweet> tweets = new ArrayList<MTrainingTweet>();
        try
        {
            FileReader csvFile = new FileReader(csvPath);
            tweets = new ArrayList<MTrainingTweet>();
            Pattern pattern = Pattern.compile(",");

            BufferedReader in = new BufferedReader(csvFile);

            tweets = in.lines().skip(1).map(line -> {
                String[] x = pattern.split(line);
                return new MTrainingTweet(x,"icc");
            }).collect(Collectors.toList());

            return tweets;
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return tweets;
    }*/

    @Override
    public String toString() {
        return "MTrainingTweet{" +
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
