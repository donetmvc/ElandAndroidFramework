package com.eland.elandandroidframework.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.bmob.pay.tool.BmobPay;
import com.bmob.pay.tool.OrderQueryListener;
import com.bmob.pay.tool.PayListener;
import com.eland.elandandroidframework.Application.ElandApplication;
import com.eland.elandandroidframework.R;
import com.eland.elandandroidframework.Util.HttpRequest;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {

    //butterknife 注解插件 简化初始化工作
    @Bind(R.id.txt_message)
    TextView txt_message;
    @BindString(R.string.string_test)
    String test_message;

    private String orderInfor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //将当前的activity加入到list管理器中，方便管理生命周期
        ElandApplication.getInstance().addActivity(this);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_test) void test() {
        //txt_message.setText(test_message);

        RequestParams params = new RequestParams();
        params.put("loginId", "ju_minho");

        HttpRequest.get(ElandApplication.apiUrl + "/api/Plant", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                if (response != null && response.length() > 0) {
                    //txt_message.setText(response.getJSONObject(0).getString("plantName"));
                    txt_message.setText("전지점");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    @OnClick(R.id.btn_alibaba) void payForalibaba() {
        new BmobPay(MainActivity.this).pay(0.02, "淘宝买东西测试", new PayListener() {
            @Override
            public void orderId(String s) {
                //无论支付成功与否，发起一次请求都会生成一个单号
                orderInfor = s;
            }

            @Override
            public void succeed() {
                //支持成功
                new BmobPay(MainActivity.this).query(orderInfor, new OrderQueryListener() {
                    @Override
                    public void succeed(String s) {
                        txt_message.setText(s);
                    }

                    @Override
                    public void fail(int i, String s) {
                        txt_message.setText(s);
                    }
                });
            }

            @Override
            public void fail(int i, String s) {
                //失败
            }

            @Override
            public void unknow() {

            }
        });
    }

    @OnClick(R.id.btn_webview) void payForweixin() {
        new BmobPay(MainActivity.this).payByWX(0.02, "微信发红包咯", new PayListener() {
            @Override
            public void orderId(String s) {

            }

            @Override
            public void succeed() {

            }

            @Override
            public void fail(int i, String s) {

            }

            @Override
            public void unknow() {

            }
        });
    }
}
