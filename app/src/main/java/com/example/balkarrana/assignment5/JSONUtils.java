package com.example.balkarrana.assignment5;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public final class JSONUtils {
    public static ArrayList<String> getEarthquakeJSONData(String urlString){
        URL url;

        try{
            url = new URL(urlString);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Failed to create url");
            return null;
        }

        try{
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // connection properties
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setDoOutput(true);

            // connect
            connection.connect();

            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            StringBuilder response = new StringBuilder();

            String line;

            while((line = br.readLine()) != null){
                response.append(line);
            }

            connection.disconnect();
            return getJSONDataFromResponse(response.toString());
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error grabbing JSON data");
            return null;
        }
    }

    public static ArrayList<String> getJSONDataFromResponse(String response){
        if(response.isEmpty()){
            System.out.println("Response is empty");
            return null;
        }

        ArrayList<String> eData = new ArrayList<>();

        try{
            JSONArray eArray = new JSONObject(response).getJSONArray("features");

            // go through all presumably 20 earthquakes
            for(int i = 0; i < eArray.length(); i++){
                JSONObject properties = eArray.getJSONObject(i).getJSONObject("properties");
                eData.add(properties.getString("title") + "&" + properties.getString("time") + "&" + properties.getString("url"));
                // System.out.println("Im i: " + i);
            }

            // System.out.println("Edata length: " + eData.size());

            return eData;
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("Error parsing response");
            return null;
        }
    }

}
