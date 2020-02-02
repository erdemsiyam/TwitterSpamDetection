package com.tpa.tpa;

import model.MTrainingTweet;
import model.MTweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import twitter4j.TwitterException;
import twitterApi.Api;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import wekaApi.AlgorithmType;
import wekaApi.WekaApi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TransferQueue;

@RequestMapping(value = "/*")
@Controller
public class appController {
    @Autowired
    MongoTemplate mongoTemplate;

    String sonuc;

    @RequestMapping("/test")
    public String test() throws Exception {
        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("Beans.xml");
        MongoTemplate mongoTemplate=(MongoTemplate) context.getBean("mongoTemplate");
       // Api.baglan();
        //User user = Api.kullaniciGetir("oguzhanmelez");

       /* List<MTrainingTweet> list = MTrainingTweet.iccToTrainerTweets("C:\\csv_result-95k-random.csv");
        //icc db save
        for(MTrainingTweet tweet: list)
        {
            mongoTemplate.save(tweet);
        }*/
        /*
        List<MTrainingTweet> listsuperBowl = MTrainingTweet.superBowlToTrainerTweets("E:\\Bitirme Projesi\\Docs\\Datasets\\superbowllive.txt");
        for(MTrainingTweet tweet: listsuperBowl)
        {
            mongoTemplate.save(tweet);
        }
        // mongoTemplate.save(MUser.newUser(user));
        */
        /*
        Query query = new Query();
        query.limit(20);


        Query query3 = new Query();
        query3.addCriteria(Criteria.where("clas").is("non-spammer"));
        query3.limit(1);*/

       /* Query query2 = new Query();
        query2.addCriteria(Criteria.where("clas").is("non-spammer"));
        query2.limit(9500);

        Query query4 = new Query();
        query4.addCriteria(Criteria.where("clas").is("spammer"));
        query4.limit(863);

        List<MTrainingTweet> listtweets2 = mongoTemplate.find(query2,MTrainingTweet.class);
        List<MTrainingTweet> listtweets3 = mongoTemplate.find(query4,MTrainingTweet.class);
        listtweets2.addAll(listtweets3);

        WekaApi.trainingTweetsToArff(listtweets2);*/

/*
        BufferedReader trainingDataset =
                new BufferedReader(new FileReader("C:\\Users\\hamza\\Desktop\\twitter-spam.arff"));
        BufferedReader testDataset  =
                new BufferedReader(new FileReader("C:\\Users\\hamza\\Desktop\\twitter-spam-test.arff"));*/
         // WekaApi.Training(trainingDataset,testDataset , AlgorithmType.NAIVE_BAYES);
        // WekaApi.Training(trainingDataset,testDataset , AlgorithmType.SUPPORT_VECTOR_MACHINE);
          //WekaApi.Training(trainingDataset,testDataset , AlgorithmType.LOGISTIC_REGRESSION);
         //WekaApi.Training(trainingDataset,testDataset , AlgorithmType.RANDOM_FOREST,20);
        //WekaApi.Training(trainingDataset,testDataset , AlgorithmType.RANDOM_TREE,20);
       // WekaApi.Training(trainingDataset,testDataset , AlgorithmType.K_NEAREST_NEIGHBOURS,20);
        //WekaApi.KnnMultiTest(reader,reader1);

       // WekaApi.crossValidation(reader,20,AlgorithmType.NAIVE_BAYES);

      // List<MTrainingTweet> listtweets = mongoTemplate.findAll(MTrainingTweet.class);//hepsini çekiyoz
        int x=31;
        return "test";
    }

    @RequestMapping("/")
    public String getir(){

        return "anasayfa";
    }

    @RequestMapping(value = "/aramasonuc",method =RequestMethod.POST)
    public ModelAndView aramasonuc(@RequestParam("kacyuz")Integer kacyuz,
                                       @RequestParam("arama")String arama,
                                       Model model) throws TwitterException, IOException, InterruptedException {
        Api.baglan();
        int[] sonuclar = Api.SpamTweetArama(arama,kacyuz);
        model.addAttribute("urlspam",sonuclar[0]);
        model.addAttribute("friendspam",sonuclar[1]);
        model.addAttribute("similarityspam",sonuclar[2]);
        return new ModelAndView("sonuc");
    }

    /*
    @RequestMapping(value = "/anasayfa",method =RequestMethod.POST)
    public ModelAndView setlistContact(@RequestParam("kacyuz")Integer kacyuz,
                                       @RequestParam("arama")String arama,
                                       @RequestParam("aramatipi") String aramatipi, Model model) throws TwitterException {
        Api.baglan();
        Api ana = new Api();
        sonuc=ana.AnaArama(aramatipi,arama,kacyuz);
        List<String> sonuclar=new ArrayList<>();
        sonuclar.add(sonuc);
        model.addAttribute("key",sonuclar);
        return new ModelAndView("sonuc");
    }*/

    @RequestMapping("/ekle")
    public String getir1(){

        return "ekle";
    }

    @RequestMapping(value = "/testsonuc",method =RequestMethod.POST)
    public ModelAndView testsonuc(@RequestParam("flag")Integer flag, Model model)
     {

        return new ModelAndView("testsonuc");
    }
    @RequestMapping(value = "/ekle",method =RequestMethod.POST)
    public ModelAndView setlistContact(@RequestParam("say1")Integer sayi1,
                                       @RequestParam("say2")Integer sayi2,
                                       @RequestParam("arama")String arama,
                                       @RequestParam("y")String y){

        return new ModelAndView("contact");
    }

}
