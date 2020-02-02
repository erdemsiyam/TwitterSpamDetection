package twitterApi;

import model.*;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import twitter4j.*;
import twitter4j.api.PlacesGeoResources;
import twitter4j.api.TrendsResources;
import twitter4j.conf.ConfigurationBuilder;
import virusTotalApi.StringSimilarity;
import virusTotalApi.VirusTotalAPI;

import java.io.IOException;
import java.util.*;
import java.util.TimeZone;

public class Api {

    private static Map<String,RateLimitStatus> tumLimitler;
    private static Date tumLimitSorgulamaYenilenmeZamani = null;
    //spring boot context
    static ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("Beans.xml");
    static MongoTemplate  mongoTemplate=(MongoTemplate) context.getBean("mongoTemplate");
    // twitter bağlantısı
    public static Twitter twitter;

    // twitter bağlantı fonksiyonu
    public static void baglan(){
        ConfigurationBuilder cf = new ConfigurationBuilder();
        cf.setDebugEnabled(true)
                .setOAuthConsumerKey("XXX")
                .setOAuthConsumerSecret("XXX")
                .setOAuthAccessToken("XXX")
                .setOAuthAccessTokenSecret("XXX");
        TwitterFactory tf = new TwitterFactory(cf.build());
        twitter = tf.getInstance();
    }


    // ------------------------------------- LİMİT ---------------------------------------------


    public static LimitTipi seciliLimitSorgula(LimitTipi limitTipi){
        try{
            if(tumLimitSorgulamaYenilenmeZamani == null) //ilk limit sorgusu mu : ozaman twitter dan hakları çek.
            {
                tumLimitler = twitter.getRateLimitStatus(); // twitter dan hakları çek
                tumLimitSorgulamaYenilenmeZamani = new Date(Long.parseLong(tumLimitler.get(LimitTipi.GENEL_LİMİT_SORGULAMA.toString()).getResetTimeInSeconds() + "000")); // yeni TÜM hakları sorgulama zamanını güncelle
                LimitTipi.haklariGuncelle(tumLimitler); // bizdeki tüm limit tiplerin hakları kendilerinde tutulur onları günceledik.
                return limitTipi;
            }
            else {// ilk limit sorgusu değil, ama hak zamanımız geldi : twitter'den hakları çek
                long suankiZaman = new Date().getTime(); // fazlasını almıyoruz fazlası sanisel. o yüzden hep şuanki zmaan fazla gözüküp hep çekmeye çalışıyor.
                if (tumLimitSorgulamaYenilenmeZamani.getTime() <= suankiZaman) // zaman doldu mu evetse
                {
                    tumLimitler = twitter.getRateLimitStatus(); // twitter dan hakları çek
                    tumLimitSorgulamaYenilenmeZamani = new Date(Long.parseLong(tumLimitler.get(LimitTipi.GENEL_LİMİT_SORGULAMA.toString()).getResetTimeInSeconds() + "000")); // yeni TÜM hakları sorgulama zamanını güncelle
                    LimitTipi.haklariGuncelle(tumLimitler); // bizdeki tüm limit tiplerin hakları kendilerinde tutulur onları günceledik.
                    return limitTipi;
                }
            }
        }
        catch (TwitterException e)
        {
            System.out.println("HATA - Limit Aşımı : Genel Limit Sorgulama Hakkı Kalmadı " + e.getRateLimitStatus().getSecondsUntilReset() + " Kadar Saniye Bekle"+"\n...");
            tumLimitSorgulamaYenilenmeZamani = null;
            try {
                Thread.sleep((e.getRateLimitStatus().getSecondsUntilReset() * 1000) + 500);
            } catch (InterruptedException e2) {
                e2.printStackTrace();
            }
            seciliLimitSorgula(limitTipi);
        }
        return limitTipi;
    }

    public static void hakKontrol(LimitTipi limitTipi) throws TwitterException {

        if(limitTipi.kalanHak <=0)
        {
            long suankiZaman = new Date().getTime();
            long yenilenmeZamani = Long.parseLong(tumLimitler.get(limitTipi.toString()).getResetTimeInSeconds()+"000");
            long kalanSanise = yenilenmeZamani - suankiZaman;
            if(kalanSanise >= 0) // yenilenme için kalan zaman varsa okadar bekle yoksa devam et
            {
                System.out.println("Hak kalmadı " + (int)(kalanSanise/1000) + " Saniye Kadar Bekle"+"\n...");
                try {
                    Thread.sleep(kalanSanise + 10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Hak Kontrol Tekrar Yapılıyor...");
                hakKontrol(seciliLimitSorgula(limitTipi));
            }else
            {
                if(LimitTipi.GENEL_LİMİT_SORGULAMA.kalanHak > 0){
                }
                else{
                    long suankiZaman2 = new Date().getTime();
                    long yenilenmeZaman2 = Long.parseLong(tumLimitler.get(LimitTipi.GENEL_LİMİT_SORGULAMA.toString()).getResetTimeInSeconds()+"000");
                    long kalanSanise2 = yenilenmeZaman2 - suankiZaman2;
                    if(kalanSanise2 >= 0){
                        System.out.println("Genel Hak kalmadı " + (int)(kalanSanise2/1000) + " Saniye Kadar Bekle"+"\n...");
                        try {
                            Thread.sleep(kalanSanise + 500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                seciliLimitSorgula(LimitTipi.GENEL_LİMİT_SORGULAMA);
                limitTipi.kalanHak--;
                System.out.println("Genel Hak yenilendi sorgulama Başlıyor...");
            }
        }
        else
        {
            limitTipi.kalanHak--;
            System.out.println("Sorgulama Başlıyor...");
        }
    }
    // ------------------------------------- TWEET ---------------------------------------------

    // Kendi TimeLine'ımıza ait tweetler // LİMİT TİPİ : ANASAYFA_TWEETLERİ
    public static List<Status>  anasayfaTweetleriGetir() throws TwitterException {
        hakKontrol(seciliLimitSorgula(LimitTipi.ANASAYFA_TWEETLERİ));
        return twitter.getHomeTimeline();
    }


    public static List<Status> kullaniciTweetleriniGetir(String screenName, int statusCount) throws TwitterException {
        int kacYuzTweetiVar = statusCount/200;
        int pageNo = 1;
        List<Status> tweetList = new ArrayList<>();
        for(; pageNo <= kacYuzTweetiVar ; pageNo++)
        {
            hakKontrol(seciliLimitSorgula(LimitTipi.KULLANİCİ_TWEETLERİ));
            tweetList.addAll(twitter.getUserTimeline(screenName, new Paging(pageNo, 100)));
        }
        int yuzunKatlarindenKalanKusuratTweetSayisi = statusCount - (kacYuzTweetiVar * 100);
        if( yuzunKatlarindenKalanKusuratTweetSayisi > 0){
            hakKontrol(seciliLimitSorgula(LimitTipi.KULLANİCİ_TWEETLERİ));
            tweetList.addAll(twitter.getUserTimeline(screenName, new Paging(pageNo, yuzunKatlarindenKalanKusuratTweetSayisi)));
        }
        return  tweetList;
    }


    // Tek bir tweet getirme // LİMİT TİPİ : TEK_BİR_TWEET_GETİRME
    public static Status tweetGetir(String tweetID) throws TwitterException {
        hakKontrol(seciliLimitSorgula(LimitTipi.TEK_BİR_TWEET_GETİRME));
        return twitter.showStatus(Long.parseLong(tweetID));
    }

    // Tweet Tipi sorgulama - Tweet,Reply,Alıntı,Retweet,Unknown
    public static StatusTipi tweetTipSorgula(Status tweet){
        if(tweet.getInReplyToStatusId() == -1 && tweet.getQuotedStatusId() == -1 && tweet.getRetweetedStatus() != null )
        {
            return StatusTipi.Retweet;
        }
        else if(tweet.getInReplyToStatusId() == -1 && tweet.getQuotedStatusId() == -1)
        {
            return StatusTipi.Tweet;
        }
        else if (tweet.getInReplyToStatusId() != -1 )
        {
            return StatusTipi.Reply;
        }
        else if (tweet.getQuotedStatusId() != -1)
        {
            return StatusTipi.Alıntı;
        }
        else
            return  StatusTipi.Unknown;
    }

    // Bir tweet'e ait Retweetler // LİMİT TİPİ : TWEET_RETWEETLERİ
    public static ResponseList<Status>  tweetRetweetleriniGetir(String tweetID) throws TwitterException {
        hakKontrol(seciliLimitSorgula(LimitTipi.TWEET_RETWEETLERİ));
        return twitter.getRetweets(Long.parseLong(tweetID));
    }

    //YENİ
    public static String tweetAtilanPlatform(Status tweet){
        String source = tweet.getSource();
        if(source.contains("Web"))
        {
            return "Web";
        }
        else if (source.contains("iPhone"))
        {
            return "iPhone";
        }
        else if (source.contains("Android"))
        {
            return "Android";
        }
        return "";
    }
    //YENİ
    public static String tweetKonumu(Status tweet){
        Place place = tweet.getPlace();
        if(place != null)
        {
            return place.getFullName();
        }
        return "";
    }



    // ------------------------------------- ARAMA ---------------------------------------------

    // Bir key word'e göre kullanıcı arama // LİMİT TİPİ : KULLANICILARI_ARA
    public static ResponseList<User> kullaniciAra(String arama) throws TwitterException {
        hakKontrol(seciliLimitSorgula(LimitTipi.KULLANICILARI_ARA));
        /*
        RateLimitStatus limitKontrol = seciliLimitSorgula(LimitTipi.USER_ARAMA);
        if( limitKontrol.getRemaining()<1)
        {
            System.out.println("Yeteri Kadar User Arama Hakkı Yok : "+ limitKontrol.getRemaining()+"/"+limitKontrol.getLimit()+"\tYenilenme Süresi : "+limitKontrol.getSecondsUntilReset()+" saniye");
            return null;
        }
        */
        ResponseList<User> kullanicilar = twitter.searchUsers(arama,1); //2. parametre : kaç sayfa sonuç gelsin , 1 sayfa = 20 sonuç
        return kullanicilar;
    }

    // screenName veya id alabilir. // LİMİT TİPİ : USER_DETAY
    public static ResponseList<User> kullaniciiDetayliAra(String screenName) throws TwitterException {
        hakKontrol(seciliLimitSorgula(LimitTipi.USER_DETAY));
        ResponseList<User> kullanicilar = twitter.lookupUsers(screenName);
        return kullanicilar;
    }

    // verilen arama kelimesini içeren tüm tweetler gelir. // LİMİT TİPİ : TWEET_ARAMA
    public static List<Status> tweetAra(String arama, int kacYuz) throws TwitterException {

        // her yüz için bir 1 adet /search/tweets hakkı kulanır.
        hakKontrol(seciliLimitSorgula(LimitTipi.TWEET_ARAMA));

        Query query = new Query();
        query.setQuery(arama);
        query.setResultType(Query.ResultType.mixed);
        query.setCount(100);
        QueryResult result = twitter.search(query);
        List<Status> tweets = result.getTweets();
        if(tweets.size() <= 0)
        {return null;}
        long son_id = tweets.get(tweets.size()-1).getId();
        for (  int i=1; i<kacYuz ; i++)
        {
            hakKontrol(seciliLimitSorgula(LimitTipi.TWEET_ARAMA));
            System.out.println("Tw arama kalan hak : " + LimitTipi.TWEET_ARAMA.kalanHak);
            Query query2 = new Query();
            query2.setQuery(arama);
            query2.setResultType(Query.ResultType.mixed);
            query2.setCount(100);
            query2.setMaxId(son_id);
            QueryResult result2 = twitter.search(query2);
            System.out.println("Çekilen Toplam Tweet : "+((i+1)*100));
            tweets.addAll(result2.getTweets());
            son_id = tweets.get(tweets.size()-1).getId();
        }
        /*          istediğin sorguyu ekle
            query.setSince("2010-03-04"); // nezamandan
            query.setUntil("2010-03-06"); // nezamana
            query.maxId(Long.valueOf("longsayi")); // çekilecek max id sonrasını alma
            query.setCount(1); // çekiecek max veri
            query.resultType(Query.ResultType.popular); // popüler girileri ver.
            query.setLocale("istanbul"); // mekana göre veri çek

         */
        /*          KONTROL ET - Hangisi Cazip verilerr veriyor
        Status status1 = twitter.showStatus(id);
        Status status2 = twitter.retweetStatus(id);
        */
        return tweets;
    }

    // Hocanın bizden istediği "_u dersek user a göre arama yapsın dediği fonksiyon"
    public String AnaArama(String aramaTipi,String arama,int kacYuz) throws  TwitterException{

        MArama aramaObj = new MArama(
                UUID.randomUUID().toString(),
                arama,
                new Date(),
                null,
                "Virüs Total Spam"
        );

        if(aramaTipi.equalsIgnoreCase("u"))
        {
            for (User eleman:kullaniciAra(arama)) {
                System.out.println( eleman.getId()+" "+eleman.getScreenName() + " "+ eleman.getName());
            }
            aramaObj.setEndTime(new Date());
            mongoTemplate.save(aramaObj);
            return kullaniciAra(arama).size() + " kadar sonuç bulundu";
        }
        else if (aramaTipi.equalsIgnoreCase("t"))
        {
            List<Status> tweets = Api.tweetAra(arama,kacYuz);

            for(Status tweet: tweets)
            {
                mongoTemplate.save(MTweet.newTweet(tweet,aramaObj));
                mongoTemplate.save(MUser.newUser(tweet.getUser(),aramaObj));
            }
            System.out.println(tweets.size() + " tweet kullanıcısıyla birlikte veri tabanına kayıt edildi.");
            aramaObj.setEndTime(new Date());
            mongoTemplate.save(aramaObj);
            return tweets.size() + " tweet kullanıcısıyla birlikte veri tabanına kayıt edildi.";
        }
        aramaObj.setEndTime(new Date());
        mongoTemplate.save(aramaObj);
        return "hata";
    }

    public static int[] SpamTweetArama(String arama,int kacYuz) throws TwitterException, IOException, InterruptedException {

        int urlspam = 0;
        int friendspam=0;
        int similarityspam=0;
        MArama aramaObj = new MArama(
                UUID.randomUUID().toString(),
                arama,
                new Date(),
                null,
                "Virüs Total Spam"
        );

        List<Status> tweets = Api.tweetAra(arama,kacYuz);
        //Status tweet = Api.tweetGetir("1107090119150972928");
        int i=1;
        for(Status tweet: tweets)
        {
                if (tweet.getURLEntities() !=null)
                {
                    for (URLEntity url : tweet.getURLEntities())
                    {
                       if (VirusTotalAPI.isUrlSuspicious(url.getExpandedURL()))
                       {
                           System.out.println("eklendi");
                           mongoTemplate.save(MTweet.newTweet(tweet,aramaObj));
                           mongoTemplate.save(MUser.newUser(tweet.getUser(),aramaObj));
                           mongoTemplate.save(MTestTweet.tweetToTestTweets(tweet,TestStatusType.URL_SPAMMER));
                           urlspam++;
                           User spammer = tweet.getUser();
                           int userFollowersCount = spammer.getFollowersCount(); // 15den fazla çekmesin
                           if(userFollowersCount > 15) userFollowersCount = 15;
                           List<User> friends = Api.kullaniciyiTakipEdenler(spammer.getScreenName(),userFollowersCount);
                           for (User user: friends )
                           {
                               if(user.isProtected()) continue;
                               mongoTemplate.save(MUser.newUser(user,aramaObj));
                               int cekilecekTweetSayisi = user.getStatusesCount(); // 50 den fazla tweet almasın
                               if(cekilecekTweetSayisi > 50 ) cekilecekTweetSayisi=50;
                               List<Status> userTweets = Api.kullaniciTweetleriniGetir(user.getScreenName(),cekilecekTweetSayisi);
                               for (Status status : userTweets)
                               {
                                   mongoTemplate.save(MTweet.newTweet(status,aramaObj));
                                   mongoTemplate.save(MTestTweet.tweetToTestTweets(status,TestStatusType.FRIEND_SPAMMER));
                                   friendspam++;
                               }
                           }
                           break;
                       }
                    }
                    mongoTemplate.save((aramaObj));
                }
            System.out.println("tweet no :" + i);
                i++;
        }

        /*
        similarityspam = mongoTemplate.findAll(MTweet.class).size();
        for (Status status1 : tweets)
        {
            if (Api.tweetTipSorgula(status1) != StatusTipi.Reply && Api.tweetTipSorgula(status1) != StatusTipi.Tweet)
                continue;
            String status1Hashtagsiz = status1.getText();
            for(HashtagEntity he : status1.getHashtagEntities()){
                status1Hashtagsiz = status1Hashtagsiz.replace("#"+he.getText(),"");
            }
            for (Status status2 : tweets)
            {
                if (Api.tweetTipSorgula(status2) != StatusTipi.Reply && Api.tweetTipSorgula(status2) != StatusTipi.Tweet)
                    continue;
                String status2Hashtagsiz = status2.getText();
                for(HashtagEntity he : status2.getHashtagEntities()){
                    status2Hashtagsiz = status2Hashtagsiz.replace("#"+he.getText(),"");
                }
                if (StringSimilarity.similarity(status1Hashtagsiz,status2Hashtagsiz))
                {
                    if(status1.getId() == status2.getId()) // eğer kendisiyse es geç
                        continue;
                    mongoTemplate.save(MTweet.newTweet(status1,aramaObj));
                    mongoTemplate.save(MUser.newUser(status1.getUser(),aramaObj));
                    mongoTemplate.save(MTestTweet.tweetToTestTweets(status1,TestStatusType.SIMILARITY_SPAMMER));
                    similarityspam++;
                    mongoTemplate.save(MTweet.newTweet(status2,aramaObj));
                    mongoTemplate.save(MUser.newUser(status2.getUser(),aramaObj));
                    mongoTemplate.save(MTestTweet.tweetToTestTweets(status2,TestStatusType.SIMILARITY_SPAMMER));
                }
            }
        }

        similarityspam = mongoTemplate.findAll(MTweet.class).size()-similarityspam;*/
        return new int[] {urlspam,friendspam,similarityspam};
    }


    // ------------------------------------ KULLANICI --------------------------------------------

    // ID ye göre kullanıcı getirme // LİMİT TİPİ : TEK_BİR_KULLANICI_GETİR
    public static User kullaniciGetir(long id) throws TwitterException {
        hakKontrol(seciliLimitSorgula(LimitTipi.TEK_BİR_KULLANICI_GETİR));
        return twitter.showUser(id);
    }

    // Kullanıcı Adına Göre Kullanıcı Getirme // LİMİT TİPİ : TEK_BİR_KULLANICI_GETİR
    public static User kullaniciGetir(String screenName) throws TwitterException {
        hakKontrol(seciliLimitSorgula(LimitTipi.TEK_BİR_KULLANICI_GETİR));
        return twitter.showUser(screenName);
    }

    // Bir kullanıcının takip ettiği kişileri getirme // LİMİT TİPİ : KULLANICININ_TAKİP_ETTİKLERİ
    public static PagableResponseList<User>  kullanicininTakipEttikleri (String kullaniciAdi, int getirilecekUserSayisi ) throws TwitterException {
        hakKontrol(seciliLimitSorgula(LimitTipi.KULLANICININ_TAKİP_ETTİKLERİ));
        if(! (getirilecekUserSayisi > 0))
        {
            getirilecekUserSayisi=20;
        }
        return twitter.getFriendsList(kullaniciAdi,-1,getirilecekUserSayisi);//takip ettikleri
    }


    // Bir kullanıcıyı takip edenleri getirme // LİMİT TİPİ : KULLANICIYI_TAKİP_EDENLER
    public static List<User> kullaniciyiTakipEdenler (String kullaniciAdi, int getirilecekUserSayisi) throws TwitterException {

        //her çağırıda 200 arkadaş getirme hakkımız var
        int kacTane200Takipci = (getirilecekUserSayisi / 200);
        int paging = -1;
        List<User> followers = new ArrayList<User>();

        for(;paging >= -kacTane200Takipci ; paging--){

            /*
            tumLimitler = twitter.getRateLimitStatus(); // tekrar cariyoz
            long zaman1 = Long.parseLong(tumLimitler.get(LimitTipi.GENEL_LİMİT_SORGULAMA.toString()).getResetTimeInSeconds()+"000");
            long zaman2 = Long.parseLong(tumLimitler.get(LimitTipi.KULLANICIYI_TAKİP_EDENLER.toString()).getResetTimeInSeconds()+"000");
            tumLimitSorgulamaYenilenmeZamani = new Date(zaman1);
            RateLimitStatus genel  = tumLimitler.get(LimitTipi.GENEL_LİMİT_SORGULAMA.toString());
            RateLimitStatus takip  = tumLimitler.get(LimitTipi.KULLANICIYI_TAKİP_EDENLER.toString());
            System.out.println("genel limit : " + genel.getLimit() + " / "+ genel.getRemaining() + " yenilenme :" + new Date(zaman1) + " : " + zaman1);
            System.out.println("takip limit : " + takip.getLimit() + " / "+ takip.getRemaining() + " yenilenme :" + new Date(zaman2) + " : " + zaman2);
            */

            System.out.println(paging*-1 + ". Sorgu :");
            hakKontrol(seciliLimitSorgula(LimitTipi.KULLANICIYI_TAKİP_EDENLER));
            followers.addAll(twitter.getFollowersList(kullaniciAdi,paging,200));
        }
        int kalanGetirilecekTakipciSayisi = getirilecekUserSayisi - (kacTane200Takipci*200);//200 ün katlarını çektik şimdi kalan küsüratı
        if(kalanGetirilecekTakipciSayisi > 0 )
        {
            /*
            tumLimitler = twitter.getRateLimitStatus(); // tekrar cariyoz
            long zaman1 = Long.parseLong(tumLimitler.get(LimitTipi.GENEL_LİMİT_SORGULAMA.toString()).getResetTimeInSeconds()+"000");
            long zaman2 = Long.parseLong(tumLimitler.get(LimitTipi.KULLANICIYI_TAKİP_EDENLER.toString()).getResetTimeInSeconds()+"000");
            tumLimitSorgulamaYenilenmeZamani = new Date(zaman1);
            RateLimitStatus genel  = tumLimitler.get(LimitTipi.GENEL_LİMİT_SORGULAMA.toString());
            RateLimitStatus takip  = tumLimitler.get(LimitTipi.KULLANICIYI_TAKİP_EDENLER.toString());
            System.out.println("genel limit : " + genel.getLimit() + " / "+ genel.getRemaining() + " yenilenme :" + new Date(zaman1) + " : " + zaman1);
            System.out.println("takip limit : " + takip.getLimit() + " / "+ takip.getRemaining() + " yenilenme :" + new Date(zaman2) + " : " + zaman2);
            */

            System.out.println(paging*-1 + ". Sorgu :");
            hakKontrol(seciliLimitSorgula(LimitTipi.KULLANICIYI_TAKİP_EDENLER));
            followers.addAll(twitter.getFollowersList(kullaniciAdi,paging,kalanGetirilecekTakipciSayisi));
        }
        return followers;
    }


    // İki user arasındaki İlişki // LİMİT TİPİ : İKİ_USER_İLİŞKİSİ
    public static Relationship ikiUserIliskisi(String sourceUser, String targetUser) throws TwitterException {
        hakKontrol(seciliLimitSorgula(LimitTipi.İKİ_USER_İLİŞKİSİ));
        return twitter.showFriendship(sourceUser,targetUser);
    }

    public static boolean kullaniciOnayliMi(User user)
    {
        return user.isVerified();
    }

    // Kullanici favori tweetleri : Limit Tipi : KULLANICI_FAVORILERİ
    public static List<Status> kullaniciFavorileri(String screenName , int kacinciYirmilik) throws TwitterException {
        hakKontrol(seciliLimitSorgula(LimitTipi.KULLANICI_FAVORILERİ));

        return twitter.getFavorites(screenName,new Paging(kacinciYirmilik));
    }


    //----Deneme

    public static void deneme() throws TwitterException
    {
        List<Status> list = twitter.getFavorites("oguzhanmelez",new Paging(2));

         //twitter.getUserListStatuses();
        int x= 1;
    }

    //Sonra düşün dene
    // userlerin listeleri : getUserList
    // userlerin listelerindeki tweetler : Statuses
}
