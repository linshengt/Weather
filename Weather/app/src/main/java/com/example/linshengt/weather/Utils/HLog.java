package com.example.linshengt.weather.Utils;

import android.util.Log;

/**
 * Created by linshengt on 2016/9/6.
 */
public class HLog {
    private static int LOG_LEVEL = 6;
    private static int ERROR = 1;
    private static int WARN = 2;
    private static int INFO = 3;
    private static int DEBUG = 4;
    private static int VERBOS = 5;

    public static void e(String tag, String msg){
        if(LOG_LEVEL > ERROR){
            Log.e(tag, msg);
        }
    }

    public static void w(String tag, String msg){
        if(LOG_LEVEL > WARN){
            Log.w(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if(LOG_LEVEL > INFO){
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg){
        if(LOG_LEVEL > VERBOS){
            Log.d(tag, msg);
        }
    }

    public static void v(String tag, String msg){
        if(LOG_LEVEL > ERROR){
            Log.v(tag, msg);
        }
    }

}
