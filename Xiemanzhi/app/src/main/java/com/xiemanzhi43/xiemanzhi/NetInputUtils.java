package com.xiemanzhi43.xiemanzhi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetInputUtils {
    private static final String TAG = "NetInputUtils";
    public static String readString(InputStream inputStream)throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        StringBuilder sb = new StringBuilder();
        do {
            line = bf.readLine();
            Log.i(TAG, "----服务器响应的数据:" + line);
            if(null!=line){
                sb.append(line);
            }
        } while (line != null);
        return sb.toString();
    }

    public static Bitmap readImg(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }
}

