import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

public class ApiTest {

    private static  Logger log = Logger.getLogger(ApiTest.class.getName());

    @Test
    public void testApi() {

        try {

            URL url = new URL("http://api.zippopotam.us/us/90001");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept","application/json");
            if(conn.getResponseCode() !=200){
              throw new RuntimeException("HTTP error code : " + conn.getResponseCode());

            }

           Scanner scan =  new Scanner(url.openStream());

            String response = new String();

            while(scan.hasNext()){
                response = scan.nextLine();
            }
            log.info(response);

            scan.close();

           JSONObject obj =  new JSONObject(response);

           String postCode = obj.get("post code").toString();
           log.info(postCode);
            Assert.assertEquals("90001",postCode);

            String country = obj.get("country").toString();
            log.info(country);
            Assert.assertEquals("United States",country);

            String counteryabr = obj.get("country abbreviation").toString();
            log.info(counteryabr);
            Assert.assertEquals("US",counteryabr);

            //array

            JSONArray arr =  obj.getJSONArray("places");
            int size = arr.length();
          log.info("Array length is "+size);


            String placeName = arr.getJSONObject(0).getString("place name");
            log.info(placeName);
           Assert.assertEquals("Los Angeles",placeName);

           String longLatt = arr.getJSONObject(0).getString("longitude");
           log.info(longLatt);
           Assert.assertEquals("-118.2479",longLatt);

           String  state = arr.getJSONObject(0).getString("state");
           log.info(state);
           Assert.assertEquals("California",state);

           String stateAbbreviation = arr.getJSONObject(0).getString("state abbreviation");
           log.info(stateAbbreviation);
           Assert.assertEquals("CA",stateAbbreviation);

           String latitude = arr.getJSONObject(0).getString("latitude");
           log.info(latitude);
           Assert.assertEquals("33.9731",latitude);





        }catch(MalformedURLException e){
            e.printStackTrace();

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}