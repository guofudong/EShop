package com.gfd.eshop.base;


import android.content.Intent;

import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.gfd.eshop.R;

public abstract class TransitionActivity extends AppCompatActivity {

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override public void startActivity(Intent intent) {
        super.startActivity(intent);
        setTransitionAnimation(true);
    }

    @Override public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        setTransitionAnimation(true);
    }

    @Override public void finish() {
        super.finish();
        setTransitionAnimation(false);
    }

    public void finishWithDefaultTransition() {
        super.finish();
    }

    private void setTransitionAnimation(boolean newActivityIn) {
        if (newActivityIn) {
            // 新页面从右边进入
            overridePendingTransition(R.anim.push_right_in,
                    R.anim.push_right_out);
        } else {
            // 上一个页面从左边进入
            overridePendingTransition(R.anim.push_left_in,
                    R.anim.push_left_out);
        }
    }

}
