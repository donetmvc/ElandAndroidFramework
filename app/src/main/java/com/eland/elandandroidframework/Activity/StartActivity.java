package com.eland.elandandroidframework.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.eland.elandandroidframework.Application.ElandApplication;
import com.eland.elandandroidframework.R;
import com.eland.elandandroidframework.Util.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by elandmac on 15/10/13.
 */
public class StartActivity extends Activity{

    @Bind(R.id.backgroundImage)
    ImageView backgroundImage;
    @Bind(R.id.imageTips)
    TextView imageTips;

    private String TAG = "Eland";
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        ElandApplication.getInstance().addActivity(this);
        ButterKnife.bind(this);
        initView();

        LogUtil.i(TAG, "I am startActivity.");
    }

    private void initView() {
        //设置字体
        imageTips.setTypeface(ElandApplication.systemTypeface);
        //动画设置
        animation = AnimationUtils.loadAnimation(this, R.anim.start_page);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                goSign();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //在背景图片上执行动画
        backgroundImage.setAnimation(animation);
    }

    private void goSign() {
        Intent intent;
        //User user = new User("", "");

        //if(user.getLoginID() == "123") {
            //intent = new Intent(this, MainActivity.class);
        //}
        //else {
            intent = new Intent(this, LoginActivity.class);
        //}

        startActivity(intent);
        this.finish();
    }
}
