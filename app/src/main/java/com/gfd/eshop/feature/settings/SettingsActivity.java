package com.gfd.eshop.feature.settings;


import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentTransaction;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.wrapper.ToolbarWrapper;
import com.gfd.eshop.network.UserManager;
import com.gfd.eshop.network.core.ResponseEntity;
import com.gfd.eshop.network.event.UserEvent;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置页面
 */
public class SettingsActivity extends BaseActivity {

    @BindView(R.id.button_sign_out) Button btnSignOut;

    @Override protected int getContentViewLayout() {
        return R.layout.activity_settings;
    }

    @Override protected void initView() {
        new ToolbarWrapper(this).setCustomTitle(R.string.settings_title);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.container, new SettingsFragment());
        transaction.commit();
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
    }

    @Override public void onEvent(UserEvent event) {
        super.onEvent(event);
        if (UserManager.getInstance().hasUser()) {
            btnSignOut.setVisibility(View.VISIBLE);
        } else {
            btnSignOut.setVisibility(View.INVISIBLE);
        }
    }

    @OnClick(R.id.button_sign_out) void signOut() {
        UserManager.getInstance().clear();
        finish();
    }

}
