import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.google.gson.JsonObject;
import com.google.gson.JsonArray;

public class GetMovieTitles {
    public static void main(String args[]){
        String[] movieTitles = getMovieTitles("spiderman or batman");
        if(movieTitles.length > 0) {
            for (String title : movieTitles) {
                System.out.println(title);
            }
        } else {
            System.out.println("Not Found");
        }
    }

    static String[] getMovieTitles(String substr) {
        substr = substr.replace(" ", "%20");
        String resp;
        int pageNumber = 1;
        int totalPage = 1;
        List<String> titles = new ArrayList<String>();
        while(pageNumber <= totalPage) {
            try{
                URL url = new URL("https://jsonmock.hackerrank.com/api/movies/search/?Title=" + substr+"&page="+pageNumber);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));

                while ((resp = in.readLine()) != null) {

                    Gson gson = new Gson();
                    JsonObject obj = gson.fromJson(resp, JsonObject.class);
                    totalPage = obj.get("total_pages").getAsInt();
                    JsonArray data = obj.getAsJsonArray("data");
                    for(int i=0;i<data.size();i++){
                        //System.out.println(data.get(i));
                        String title = data.get(i).getAsJsonObject().get("Title").getAsString();
                        titles.add(title);
                        //System.out.println(title);
                    }
                }
                in.close();
                pageNumber++;
                //System.out.println(titles);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Collections.sort(titles);
        return titles.toArray(new String[0]);
    }
}
