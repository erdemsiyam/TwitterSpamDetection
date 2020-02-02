package com.tpa.tpa;

import model.MTestTweet;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;
import twitterApi.Api;
import twitterApi.LimitTipi;
import virusTotalApi.VirusTotalAPI;
import wekaApi.AlgorithmType;
import wekaApi.WekaApi;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        /*
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("Beans.xml");
        MongoTemplate mongoTemplate=(MongoTemplate) context.getBean("mongoTemplate");
        Query query = new Query();
        query.addCriteria(Criteria.where("dbFlag").is("similaritySpammer"));
        List<MTestTweet> tweets = mongoTemplate.find(query,MTestTweet.class);
        List<MTestTweet> tweets = mongoTemplate.findAll(MTestTweet.class);
        WekaApi.testTweetsToArff(tweets);
        System.out.println("başlıyor");
        BufferedReader trainingDataset =
                new BufferedReader(new FileReader("C:\\Users\\hamza\\Desktop\\twitter-spam.arff"));
        System.out.println("training okundu");
        BufferedReader testDataset =
                new BufferedReader(new FileReader("C:\\Users\\hamza\\Desktop\\allSpam.arff"));
        WekaApi.Training(trainingDataset,testDataset, AlgorithmType.RANDOM_FOREST);
        */

        /* ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("Beans.xml");
        MongoTemplate mongoTemplate=(MongoTemplate) context.getBean("mongoTemplate");

        Api.baglan();
        Api.SpamTweetArama("ow.ly",175);
        //  String[] trends = {"#EasterSunday", "#HappyEaster", "#200TLdenTTVerilir","#pazar","#YeniBirBaşlangıç"};
        //


        Query query = new Query();
        query.addCriteria(Criteria.where("dbFlag").is("friendSpammer"));
        List<MTestTweet> tweets = mongoTemplate.find(query,MTestTweet.class);
         WekaApi.testTweetsToArff(tweets);

      */
      /*   System.out.println("başlıyor");
        BufferedReader trainingDataset =
                new BufferedReader(new FileReader("C:\\Users\\hamza\\Desktop\\twitter-spam.arff"));
        System.out.println("training okundu");
        BufferedReader testDataset =
                new BufferedReader(new FileReader("C:\\Users\\hamza\\Desktop\\twitter-spam-test.arff"));
        WekaApi.Training(trainingDataset,testDataset, AlgorithmType.SUPPORT_VECTOR_MACHINE);
        */

        Api.baglan();
        //Api.tweetAra("#ÇocukSusarSenSUSMA",200);

        //List<Status> status = Api.tweetAra("Serkan Çınar",200);
        //List<User> users = Api.kullaniciyiTakipEdenler("vekilince",4000);

        Map<String, RateLimitStatus> tumLimitler = Api.twitter.getRateLimitStatus();
        int x= 1;
    }
}
