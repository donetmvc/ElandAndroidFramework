package com.eland.elandandroidframework.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eland.elandandroidframework.Application.ElandApplication;
import com.eland.elandandroidframework.Model.Constant;
import com.eland.elandandroidframework.Model.UserInforDto;
import com.eland.elandandroidframework.R;
import com.eland.elandandroidframework.Service.UserProxy;
import com.eland.elandandroidframework.Util.LogUtil;
import com.eland.elandandroidframework.Util.SharedPreferencesHelper;
import com.eland.elandandroidframework.Views.CircleImageView;
import com.eland.elandandroidframework.Views.EditTextView;
import com.eland.elandandroidframework.Views.ToastView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by elandmac on 15/10/14.
 */
public class LoginActivity extends Activity implements UserProxy.ISignInListener{


    @Bind(R.id.tips)
    TextView tips;

    @Bind(R.id.user_name_input)
    EditTextView userName;
    @Bind(R.id.user_pwd_input)
    EditTextView userPwd;
    @Bind(R.id.loginButton)
    Button loginButton;
    @Bind(R.id.userIcon)
    CircleImageView circleImageView;


    private String TAG = "Eland";
    private Context context;
    private UserInforDto dto;
    private UserProxy userProxy;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);

        ElandApplication.getInstance().addActivity(this);
        context = this;
        userProxy = new UserProxy(context);

        ButterKnife.bind(this);
        initView();
    }

    @OnClick(R.id.loginButton) void signIn() {
        if(animation != null) {
            circleImageView.startAnimation(animation);
        }

        validateInput();

        userProxy.setOnSignInListener(this);
        userProxy.signIn(dto);
    }

    @OnClick(R.id.tips) void goRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);

        startActivityForResult(intent, Constant.REGISTER_REQUEST_CODE);
    }

    public void initView() {
        Typeface typeface = Typeface.createFromAsset(this.getAssets(),
                "font/Roboto-Light.ttf");
        tips.setTypeface(typeface);

        animation = AnimationUtils.loadAnimation(context, R.anim.loading_ico);
        LinearInterpolator lin = new LinearInterpolator(); // 匀速旋转
        animation.setInterpolator(lin);
    }

    public void validateInput() {
        String name = userName.getText().toString();
        String pwd = userPwd.getText().toString();

        if(name.isEmpty() && pwd.isEmpty()) {
            userName.setShakeAnimation();
            userPwd.setShakeAnimation();

            ToastView.showToast(context, "请输入账户信息", Toast.LENGTH_LONG);
            return;
        }

        if(name.isEmpty()) {
            userName.setShakeAnimation();
            ToastView.showToast(context, "请输入用户名", Toast.LENGTH_LONG);
            return;
        }

        if(pwd.isEmpty()) {
            userPwd.setShakeAnimation();
            ToastView.showToast(context, "请输入密码", Toast.LENGTH_LONG);
            return;
        }

        dto = new UserInforDto();

        dto.userName = name;
        dto.password = pwd;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        LogUtil.d(TAG, "-------resultCode=" + resultCode);
        switch (resultCode){
            case Constant.REGISTER_RESULT_CODE:
                String name = data.getStringExtra("username") ;
                String password = data.getStringExtra("password") ;
                userName.setText(name);
                userPwd.setText(password);
                loginButton.setFocusable(true);
                break ;
        }
    }

    @Override
    public void onSignInSuccess() {

        if(animation != null && animation.hasStarted()) {
            circleImageView.clearAnimation();
        }

        SharedPreferencesHelper.getInstance(context).setValue("IsLogin", "true");

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onSignInFailure(int code, String msg) {

        if(animation != null && animation.hasStarted()) {
            circleImageView.clearAnimation();
        }

        if(code == 101) {
            ToastView.showToast(context, "用户名或密码不正确.", Toast.LENGTH_LONG);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        ElandApplication.getInstance().existApp();
    }
}
