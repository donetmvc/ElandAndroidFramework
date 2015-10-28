package com.eland.elandandroidframework.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.eland.elandandroidframework.Application.ElandApplication;
import com.eland.elandandroidframework.Model.Constant;
import com.eland.elandandroidframework.Model.UserInforDto;
import com.eland.elandandroidframework.R;
import com.eland.elandandroidframework.Service.UserProxy;
import com.eland.elandandroidframework.Util.LogUtil;
import com.eland.elandandroidframework.Util.TextEmailUtil;
import com.eland.elandandroidframework.Views.EditTextView;
import com.eland.elandandroidframework.Views.ToastView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by elandmac on 15/10/15.
 */
public class RegisterActivity extends Activity implements UserProxy.ISignUpListener{

    @Bind(R.id.user_name)
    EditTextView user_name;
    @Bind(R.id.user_pwd)
    EditTextView user_pwd;
    @Bind(R.id.re_user_pwd)
    EditTextView re_user_pwd;
    @Bind(R.id.re_user_sex)
    EditTextView re_user_sex;
    @Bind(R.id.re_user_age)
    EditTextView re_user_age;
    @Bind(R.id.login_link)
    TextView login_link;
    @Bind(R.id.registerButton)
    Button registerButton;

    private Context context;
    private UserInforDto userInforDto;
    private UserProxy userProxy;
    private String TAG = "Eland";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        ElandApplication.getInstance().addActivity(this);
        context = this;
        ButterKnife.bind(this);

        userProxy = new UserProxy(context);
        userProxy.setOnSignUpListener(this);
        initView();
    }

    protected void initView() {
        //设置字体
        user_name.setTypeface(ElandApplication.systemTypeface);
        user_pwd.setTypeface(ElandApplication.systemTypeface);
        re_user_pwd.setTypeface(ElandApplication.systemTypeface);
        re_user_sex.setTypeface(ElandApplication.systemTypeface);
        re_user_age.setTypeface(ElandApplication.systemTypeface);

    }

    @OnClick(R.id.registerButton) void register() {
        userInforDto = new UserInforDto();
        validateInput();

        userProxy.signUp(userInforDto);
        //login_link.setClickable(false);
        //registerButton.setClickable(false);
    }

    @OnClick(R.id.login_link) void goLogin() {
        Intent intent = new Intent() ;
        intent.setClass(context,LoginActivity.class) ;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    protected void validateInput() {

        String name = user_name.getText().toString();
        String pwd = user_pwd.getText().toString();
        String re_pwd = re_user_pwd.getText().toString();
        String sex = re_user_sex.getText().toString();
        String age = re_user_age.getText().toString();

        if(name.isEmpty() && pwd.isEmpty() && re_pwd.isEmpty() &&
                sex.isEmpty() && age.isEmpty()){
            user_name.setShakeAnimation();
            user_pwd.setShakeAnimation();
            re_user_pwd.setShakeAnimation();
            re_user_sex.setShakeAnimation();
            re_user_age.setShakeAnimation();
            ToastView.showToast(context, "请输入注册信息", Toast.LENGTH_SHORT);
            return ;
        }
        if (name.isEmpty()) {
            user_name.setShakeAnimation();
            ToastView.showToast(context, "邮箱不能为空", Toast.LENGTH_SHORT);
            return;
        }
        else if(!TextEmailUtil.isEmail(name)) {
            user_name.setShakeAnimation();
            ToastView.showToast(context, "邮箱格式错误", Toast.LENGTH_SHORT);
            return;
        }
        if (pwd.isEmpty()) {
            user_pwd.setShakeAnimation();
            ToastView.showToast(context, "密码不能为空", Toast.LENGTH_SHORT);
            return;
        }
        if (re_pwd.isEmpty()) {
            re_user_pwd.setShakeAnimation();
            ToastView.showToast(context, "确认密码不能为空", Toast.LENGTH_SHORT);
            return;
        }
        if (sex.isEmpty()) {
            re_user_sex.setShakeAnimation();
            ToastView.showToast(context, "性别不能为空", Toast.LENGTH_SHORT);
            return;
        }
        if (age.isEmpty()) {
            re_user_age.setShakeAnimation();
            ToastView.showToast(context, "年龄不能为空", Toast.LENGTH_SHORT);
            return;
        }
        if(!re_pwd.equals(pwd)){
            re_user_pwd.setShakeAnimation();
            ToastView.showToast(context, "密码和确认密码不一致", Toast.LENGTH_SHORT);
            return;
        }

        userInforDto.userName = name;
        userInforDto.password = pwd;
        userInforDto.sex = sex;
        userInforDto.age = Integer.parseInt(age);
    }

    @Override
    public void onSignUpSuccess() {
        LogUtil.i(TAG, "Register Success.");
        login_link.setClickable(true);
        registerButton.setClickable(true);
        Intent intent = new Intent() ;
        intent.setClass(context,LoginActivity.class) ;
        intent.putExtra("username",userInforDto.userName) ;
        intent.putExtra("password",userInforDto.password) ;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        setResult(Constant.REGISTER_RESULT_CODE,intent);
        finish();
    }

    @Override
    public void onSignUpFailure(int code, String msg) {
        login_link.setClickable(true);
        registerButton.setClickable(true);
        if(code == 9016){
            //The network is not available,please check your network!
            ToastView.showToast(context,"请检查网络连接",Toast.LENGTH_SHORT);
        }if(code == 202){
            //  用户名已经存在
            ToastView.showToast(context,"用户名已经存在",Toast.LENGTH_SHORT);
        }if(code == 203){
            //  邮箱已经存在
            ToastView.showToast(context,"邮箱已经存在",Toast.LENGTH_SHORT);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
