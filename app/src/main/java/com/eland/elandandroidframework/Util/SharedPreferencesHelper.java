package com.eland.elandandroidframework.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.eland.elandandroidframework.Model.Constant;

/**
 * Created by elandmac on 15/9/28.？
 * Function: 临时数据缓存 -- sharedPreferences类
 */
public class SharedPreferencesHelper {

    private static SharedPreferencesHelper sharedPreferencesHelper = null;
    private SharedPreferences sharedPreferences;

    //实例化
    public SharedPreferencesHelper(Context context) {
        sharedPreferences = context.getSharedPreferences(Constant.GLOBLE_SETTING, context.MODE_PRIVATE);
    }

    public static SharedPreferencesHelper getInstance(Context context) {
        if (sharedPreferencesHelper == null) {
            sharedPreferencesHelper = new SharedPreferencesHelper(context);
        }
        return sharedPreferencesHelper;
    }

    //设置缓存值
    public void setValue(String key,String value) {
        sharedPreferences.edit().putString(key, value).commit();
    }

    //获取缓存值
    public String getValue(String key) {
        return sharedPreferences.getString(key, "");
    }

    //清理缓存的数据
    public void clear() {
        sharedPreferences.edit().clear().commit();
    }
}
