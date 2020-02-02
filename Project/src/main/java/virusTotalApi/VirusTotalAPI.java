package virusTotalApi;

import org.json.JSONException;
import org.json.JSONObject;
import twitterApi.Api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;

public class VirusTotalAPI {
    private String API_KEY ;
    private int times = 0;

    private Date firstRequestTime;
    public static ArrayList<VirusTotalAPI> accounts = new ArrayList<VirusTotalAPI>();
    public static int index = 0;

    public VirusTotalAPI(String apiKey){API_KEY=apiKey;accounts.add(this);}


    public static boolean isUrlSuspicious(String scanUrl) throws IOException, JSONException, InterruptedException {


        // kalan hak kontrol : dakikada 4 tane sorgulama hakkımız var

        remainingControl();
        VirusTotalAPI vir = accounts.get(index);
        // istek gönderme
        String url = "https://www.virustotal.com/vtapi/v2/url/report?apikey="+vir.API_KEY+"&resource="+scanUrl;
        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {response.append(inputLine);}
        in.close();
        // istek sonucu

        JSONObject geriDonen ;
        try {
            geriDonen = new JSONObject(response.toString());
        }catch (Exception ex)
        {
            return  false;
        }
        if (geriDonen.getInt("response_code")==0)
            return false;
        return (geriDonen.getInt("positives") > 0)? true:false;

    }
    private static void remainingControl() throws InterruptedException {
        VirusTotalAPI vir = accounts.get(index);
        if(vir.firstRequestTime == null)
        {
            vir.firstRequestTime = new Date();
            vir.times = 1;
            System.out.println("Virus Total Api : "+ (index+1) +". hesap ile "+vir.times+". İstek Gönderiliyor...");
        }
        else
        {
            if(vir.times >= 4)
            {
                long diff = new Date().getTime() - vir.firstRequestTime.getTime();
                long diffSeconds = (diff / 1000);
                if (diffSeconds <= 60)
                {
                    if(index +1 < accounts.size())
                    {
                        index++;
                    }else
                    {
                        index=0;
                    }
                    System.out.println("Virus Total Api : "+ (index+1) +". hesaba geçiliyor.");
                    Thread.sleep(250);
                    remainingControl();

                }else
                {
                    vir.firstRequestTime = new Date();
                    vir.times = 1;
                    System.out.println("Virus Total Api : "+ (index+1) +". hesap ile "+vir.times+". İstek Gönderiliyor...");
                }
            }
            else
            {
                vir.times++;
                System.out.println("Virus Total Api : "+ (index+1 )+". hesap ile "+vir.times+". İstek Gönderiliyor...");
            }
        }
    }

    static{

        new VirusTotalAPI("XXX");
        new VirusTotalAPI("XXX");
        new VirusTotalAPI("XXX");
		/* istediğin kadar buraya ekleyebilirsin. */
    }
}
