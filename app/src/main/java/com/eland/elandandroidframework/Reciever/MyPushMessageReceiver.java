package com.eland.elandandroidframework.Reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.eland.elandandroidframework.Util.LogUtil;

/**
 * Created by elandmac on 15/10/15.
 */
public class MyPushMessageReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        if(intent.getAction().equals("msg")){
            LogUtil.d("bmob", "客户端收到推送内容：" + intent.getStringExtra("msg"));
        }
    }
}
