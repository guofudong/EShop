package com.gfd.eshop.feature.mine;

import android.content.Intent;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.utils.Sha256Utils;
import com.gfd.eshop.base.wrapper.ProgressWrapper;
import com.gfd.eshop.base.wrapper.ToastWrapper;
import com.gfd.eshop.base.wrapper.ToolbarWrapper;
import com.gfd.eshop.network.UserManager;
import com.gfd.eshop.network.api.ApiSignIn;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ResponseEntity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 登录页面
 */
public class SignInActivity extends BaseActivity {

    @BindView(R.id.edit_name) EditText etName;
    @BindView(R.id.edit_password) EditText etPassword;
    @BindView(R.id.button_signin) Button btnSignIn;

    private ProgressWrapper mProgressWrapper;

    private String mUsername;
    private String mPassword;

    @Override protected int getContentViewLayout() {
        return R.layout.activity_sign_in;
    }

    @Override protected void initView() {
        new ToolbarWrapper(this).setCustomTitle(R.string.mine_title_sign_in);
        mProgressWrapper = new ProgressWrapper();
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
        if (!ApiPath.USER_SIGNIN.equals(apiPath)) {
            throw new UnsupportedOperationException(apiPath);
        }
        mProgressWrapper.dismissProgress();
        if (success) {
            ToastWrapper.show(R.string.mine_msg_sign_in_success);
            ApiSignIn.Rsp signInRsp = (ApiSignIn.Rsp) rsp;
            UserManager.getInstance().setUser(signInRsp.getData().getUser(), signInRsp.getData().getSession());
            finish();
        }
    }

    @OnTextChanged({R.id.edit_password, R.id.edit_name}) void onTextChanged() {
        mUsername = etName.getText().toString();
        mPassword = etPassword.getText().toString();
        // 简单的条件判断: 用户名和密码不能为空.
        if (TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)) {
            btnSignIn.setEnabled(false);
        } else {
            btnSignIn.setEnabled(true);
        }
    }

    @OnClick(R.id.button_signin) void signIn() {
        mProgressWrapper.showProgress(this);
        ApiSignIn apiSignIn = new ApiSignIn(mUsername, Sha256Utils.bin2hex(mPassword));
        enqueue(apiSignIn);
    }

    @OnClick(R.id.text_signup) void navigateToSignUp() {
        // 跳转到注册页面.
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finishWithDefaultTransition();
    }

}
