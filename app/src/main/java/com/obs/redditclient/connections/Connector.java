package com.obs.redditclient.connections;

/**
 * @author Pedro Cánovas
 * @mail wcanovas@gmail.com
 */

import com.obs.redditclient.utils.Key;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class Connector {

    public static String call_get(String accion) {
        String response = "";

        try {
            URL url;
            url = new URL(Key.url+accion);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(30000);
            conn.setConnectTimeout(30000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            conn.connect();

            int responseCode=conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
        } catch (Exception e) {
            response="ERROR_GET";
        }
        return response;
    }

}