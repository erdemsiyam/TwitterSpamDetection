package twitterApi;

import twitter4j.RateLimitStatus;

import java.util.Map;

public enum LimitTipi {
    TWEET_ARAMA ("/search/tweets"),
    USER_DETAY ("/users/lookup"),
    GENEL_LİMİT_SORGULAMA ("/application/rate_limit_status"),
    İKİ_USER_İLİŞKİSİ("/friendships/show"),
    KULLANICILARI_ARA("/users/search"),
    KULLANICININ_TAKİP_ETTİKLERİ("/friends/list"),
    KULLANICIYI_TAKİP_EDENLER("/followers/list"),
    TEK_BİR_KULLANICI_GETİR("/users/show/:id"),
    ANASAYFA_TWEETLERİ("/statuses/home_timeline"),
    KULLANİCİ_TWEETLERİ("/statuses/user_timeline"),
    TEK_BİR_TWEET_GETİRME("/statuses/show/:id"),
    TWEET_RETWEETLERİ("/statuses/retweets/:id"),
    KULLANICI_FAVORILERİ("/favorites/list")
    ;

    private String key;
    public int kalanHak;

    private LimitTipi(String key) {
        this.key = key;
    }

    public static void haklariGuncelle(Map<String, RateLimitStatus> tumHaklar){
        for(LimitTipi lt : LimitTipi.values())
        {
            lt.kalanHak = tumHaklar.get(lt.key).getRemaining();
            System.out.println(lt.key + " : " + tumHaklar.get(lt.key).getRemaining());
        }
    }
    @Override
    public String toString() {
        return key;
    }
}
