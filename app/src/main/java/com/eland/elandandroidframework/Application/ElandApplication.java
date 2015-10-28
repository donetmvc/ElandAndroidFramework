package com.eland.elandandroidframework.Application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Typeface;

import com.eland.elandandroidframework.Util.ActivityManager;
import com.eland.elandandroidframework.Util.LogUtil;

import java.util.Locale;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;

/**
 * Created by elandmac on 15/9/28.
 */
public class ElandApplication extends Application {

    public final String TAG = "Eland";

    public static ElandApplication mInstance;

    public static String apiUrl = "http://121.190.88.31:8020";
    public static String apiKey = "7c982471b4794422dbb6cd9f4b546d15";

    public static synchronized ElandApplication getInstance() {
        if (mInstance == null) {
            mInstance = new ElandApplication();
        }
        return mInstance;
    }

    public static Typeface systemTypeface;
    public static String system;

    @Override
    public void onCreate() {
        super.onCreate();

        initTypeface(getApplicationContext());
        Bmob.initialize(this, apiKey);

        // 使用推送服务时的初始化操作
        BmobInstallation.getCurrentInstallation(this).save();
        // 启动推送服务
        BmobPush.startWork(this, apiKey);

        LogUtil.i(TAG, "I am application.");
    }

    public void initTypeface(Context context) {
        //获取系统语言
        system = getLocalLanguage();

        if(system == "en") {
            systemTypeface = Typeface.createFromAsset(context.getAssets(),
                    "font/Roboto-Light.ttf");
        }
        else if(system == "zh") {
            systemTypeface = Typeface.createFromAsset(context.getAssets(),
                    "font/xiyuan.ttf");
        }
    }

    public String getLocalLanguage() {
        Locale locale = getResources().getConfiguration().locale;
        String language = locale.getLanguage();

        //LogUtil.i(TAG, ":----------" + language);

        if (language.endsWith("zh"))
            return "zh";
        else if (language.endsWith("en"))
            return "en";
        else
            return "ko";
    }

    public void addActivity(Activity ac){
        ActivityManager.getInstance().addActivity(ac);
    }

    public void existApp() {
        ActivityManager.getInstance().removeAllActivity();
        System.exit(0);
    }
}
