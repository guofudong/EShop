package com.gfd.eshop.feature;

import android.animation.Animator;
import android.content.Intent;
import android.widget.ImageView;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.network.core.ResponseEntity;

import butterknife.BindView;

/**
 * <p> App入口 - 启动页.
 * <p> 开始2秒的渐变动画, 然后跳转到主页面.
 */
public class SplashActivity extends BaseActivity implements Animator.AnimatorListener {

    @BindView(R.id.image_splash) ImageView ivSplash;

    @Override protected int getContentViewLayout() {
        return R.layout.activity_splash;
    }

    @Override protected void initView() {
        // 渐变动画
        ivSplash.setAlpha(0.3f);
        ivSplash.animate()
                .alpha(1.0f)
                .setDuration(2000)
                .setListener(this)
                .start();
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
    }

    @Override public void onAnimationEnd(Animator animation) {
        Intent intent = new Intent(this, EShopMainActivity.class);
        startActivity(intent);
        finishWithDefaultTransition();
    }

    @Override public void onAnimationStart(Animator animation) {
    }

    @Override public void onAnimationCancel(Animator animation) {
    }

    @Override public void onAnimationRepeat(Animator animation) {
    }
}
