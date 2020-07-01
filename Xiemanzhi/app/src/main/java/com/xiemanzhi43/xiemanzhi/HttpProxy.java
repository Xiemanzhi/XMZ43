package com.xiemanzhi43.xiemanzhi;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpProxy {
    private static final String TAG = "HttpProxy";
    private static final HttpProxy instance = new HttpProxy();


    public  static HttpProxy getInstance(){
        return instance;
    }

    public void load(final String urlStr,final NetInputCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String movieUrl = "http://ramedia.sinaapp.com/res/DouBanMovie2.json";
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.setConnectTimeout(5000);
                    urlConnection.setReadTimeout(10000);
                    urlConnection.connect();
                    int responseCode = urlConnection.getResponseCode();
                    Log.i(TAG, "----responseCode: " + responseCode);
                    if (200 == responseCode) {
                        InputStream inputStream = urlConnection.getInputStream();
                        callback.onSuccess(inputStream);
                        inputStream.close();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public static interface NetInputCallback{

        void onSuccess(InputStream inputStream);
    }


}

