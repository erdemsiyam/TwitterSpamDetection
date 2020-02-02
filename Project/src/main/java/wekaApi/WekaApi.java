package wekaApi;

import model.MTestTweet;
import model.MTrainingTweet;
import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.Logistic;
import weka.classifiers.functions.SMO;
import weka.classifiers.lazy.IBk;
import weka.classifiers.trees.RandomForest;
import weka.classifiers.trees.RandomTree;
import weka.core.Capabilities;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.ConverterUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WekaApi {

    public static void Training(BufferedReader trainingDataset, BufferedReader testDataset , AlgorithmType algorithmType , Integer... crossNumFolds ) throws Exception {
        System.out.println("instance alındı");
        Instances datasetTrainig = new Instances(trainingDataset);
        datasetTrainig.setClassIndex(datasetTrainig.numAttributes()-1);

        Instances datasetTest = new Instances(testDataset);
        datasetTest.setClassIndex(datasetTest.numAttributes()-1);

        Classifier trainingClassifier = null;

        switch (algorithmType)
        {
            case NAIVE_BAYES:
                trainingClassifier = new NaiveBayes();
                break;
            case LOGISTIC_REGRESSION:
                trainingClassifier = new Logistic();
                break;
            case SUPPORT_VECTOR_MACHINE:
                trainingClassifier =  new SMO();
                break;
            case RANDOM_FOREST:
                trainingClassifier =  new RandomForest();
                break;
            case RANDOM_TREE:
                trainingClassifier =  new RandomTree();
                break;
            case K_NEAREST_NEIGHBOURS:
                trainingClassifier =  new IBk();
                break;
        }

        trainingClassifier.buildClassifier(datasetTrainig);

        Evaluation eval = new Evaluation(datasetTrainig);
        if (crossNumFolds.length>0)
            eval.crossValidateModel(trainingClassifier, datasetTrainig, crossNumFolds[0], new Random(1));
        else
            eval.evaluateModel(trainingClassifier,datasetTest);

        System.out.println(eval.toSummaryString());
        System.out.print(" the expression for the input data as per alogorithm is ");
        System.out.println(trainingClassifier);
        System.out.println(eval.toMatrixString());
        System.out.println(eval.toClassDetailsString());
    }



    public static BufferedReader trainingTweetsToDataSet(List<MTrainingTweet> tweets) throws IOException {
        String test = "@relation TweetSpamAnalyze\n\n";
        test += "@attribute acount_age numeric\n";
        test += "@attribute no_follower numeric\n";
        test += "@attribute no_following numeric\n";
        test += "@attribute no_userfavourites numeric\n";
        test += "@attribute no_lists numeric\n";
        test += "@attribute no_tweets numeric\n";
        test += "@attribute no_retweets numeric\n";
        test += "@attribute no_hashtag numeric\n";
        test += "@attribute no_usermention numeric\n";
        test += "@attribute no_urls numeric\n";
        test += "@attribute no_char numeric\n";
        test += "@attribute no_digits numeric\n";
        test += "@attribute clas {spammer,non-spammer}\n\n";
        test += "@data\n";
        for (MTrainingTweet tweet : tweets ) {
            test += tweet.getDataRaw()+"\n";
        }
        Reader inputString = new StringReader(test);
        BufferedReader reader = new BufferedReader(inputString);


        //////////////////////////////

        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();
        Instances ins = new Instances(data);
        ArffSaver saver = new ArffSaver();
        saver.setInstances(ins);
        saver.setFile(new File("./data/test.arff"));
        saver.writeBatch();

        return reader;
    }

    public  static  void trainingTweetsToArff(List<MTrainingTweet> tweets) throws IOException {
        String test = "@relation TweetSpamAnalyze\n\n";
        test += "@attribute acount_age numeric\n";
        test += "@attribute no_follower numeric\n";
        test += "@attribute no_following numeric\n";
        test += "@attribute no_userfavourites numeric\n";
        test += "@attribute no_lists numeric\n";
        test += "@attribute no_tweets numeric\n";
        test += "@attribute no_retweets numeric\n";
        test += "@attribute no_hashtag numeric\n";
        test += "@attribute no_usermention numeric\n";
        test += "@attribute no_urls numeric\n";
        test += "@attribute no_char numeric\n";
        test += "@attribute no_digits numeric\n";
        test += "@attribute clas {spammer,non-spammer}\n\n";
        test += "@data\n";
        int i=0;
        for (MTrainingTweet tweet : tweets ) {
            System.out.println(i);
            test += tweet.getDataRaw()+"\n";
            i++;
        }

        BufferedWriter writer  = new BufferedWriter( new FileWriter( "C:\\Users\\hamza\\Desktop\\myfile2.txt"));
        writer.write(test);
        writer.close();
        System.out.println("File written Successfully");

        /*
        Reader inputString = new StringReader(test);
        BufferedReader reader = new BufferedReader(inputString);

        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();
        Instances ins = new Instances(data);
        ArffSaver saver = new ArffSaver();
        saver.setInstances(ins);
        saver.setFile(new File("C:\\test.arff"));
        saver.writeBatch();*/
    }


    public  static  void testTweetsToArff(List<MTestTweet> tweets) throws IOException {
        String test = "@relation 'TweetSpamAnalyze'\n\n";
        test += "@attribute acount_age numeric\n";
        test += "@attribute no_follower numeric\n";
        test += "@attribute no_following numeric\n";
        test += "@attribute no_userfavourites numeric\n";
        test += "@attribute no_lists numeric\n";
        test += "@attribute no_tweets numeric\n";
        test += "@attribute no_retweets numeric\n";
        test += "@attribute no_hashtag numeric\n";
        test += "@attribute no_usermention numeric\n";
        test += "@attribute no_urls numeric\n";
        test += "@attribute no_char numeric\n";
        test += "@attribute no_digits numeric\n";
        test += "@attribute clas {spammer,non-spammer}\n\n";
        test += "@data\n";
        int i=0;
        for (MTestTweet tweet : tweets ) {
            System.out.println(i);
            test += tweet.getDataRaw()+"\n";
            i++;
        }
        test = test.substring(0,test.length()-1);
        BufferedWriter writer  = new BufferedWriter( new FileWriter( "C:\\Users\\hamza\\Desktop\\allSpam.arff"));
        writer.write(test);
        writer.close();
        System.out.println("File written Successfully");

        /*
        Reader inputString = new StringReader(test);
        BufferedReader reader = new BufferedReader(inputString);

        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();
        Instances ins = new Instances(data);
        ArffSaver saver = new ArffSaver();
        saver.setInstances(ins);
        saver.setFile(new File("C:\\test.arff"));
        saver.writeBatch();*/
    }

}
