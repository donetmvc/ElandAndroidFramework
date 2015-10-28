package com.eland.elandandroidframework.Service;

import android.content.Context;

import com.eland.elandandroidframework.JavaBean.MyUser;
import com.eland.elandandroidframework.Model.UserInforDto;
import com.eland.elandandroidframework.Util.LogUtil;

import cn.bmob.v3.listener.SaveListener;

/**
 * Created by elandmac on 15/10/15.
 */
public class UserProxy {

    public static final String TAG = "UserProxy";
    public Context context;
    private ISignUpListener signUpLister;
    private ISignInListener signInLister;

    public UserProxy(Context context) {
        this.context = context;
    }

    //用户注册
    public void signUp(UserInforDto dto) {
        MyUser user = new MyUser();

        user.setUsername(dto.userName);
        user.setPassword(dto.password);
        user.setSex(dto.sex);
        user.setAge(dto.age);

        user.signUp(context, new SaveListener() {
            @Override
            public void onSuccess() {
                if(signUpLister != null) {
                    signUpLister.onSignUpSuccess();
                }
                else {
                    LogUtil.i(TAG, "sign success error");
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if(signUpLister != null) {
                    signUpLister.onSignUpFailure(i,s);
                }
                else {
                    LogUtil.i(TAG, "sign failure error");
                }
            }
        });
    }

    //用户登录
    public void signIn(UserInforDto dto) {
        MyUser user = new MyUser();
        user.setUsername(dto.userName);
        user.setPassword(dto.password);

        user.login(context, new SaveListener() {
            @Override
            public void onSuccess() {
                if(signInLister != null) {
                    signInLister.onSignInSuccess();
                }
            }

            @Override
            public void onFailure(int i, String s) {
                if(signInLister != null) {
                    signInLister.onSignInFailure(i, s);
                }
            }
        });
    }

    public void setOnSignUpListener(ISignUpListener signUpLister) {
        this.signUpLister = signUpLister;
    }

    public void setOnSignInListener(ISignInListener signInLister) {
        this.signInLister = signInLister;
    }

    //注册回调
    public interface ISignUpListener {
        void onSignUpSuccess();

        void onSignUpFailure(int code,String msg);
    }

    //登录回调
    public interface ISignInListener {
        void onSignInSuccess();

        void onSignInFailure(int code,String msg);
    }

}
