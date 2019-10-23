package com.gfd.eshop.feature.mine;

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
import com.gfd.eshop.network.api.ApiSignUp;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ResponseEntity;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * 注册页面
 */
public class SignUpActivity extends BaseActivity {

    @BindView(R.id.edit_name) EditText etName;
    @BindView(R.id.edit_email) EditText etEmail;
    @BindView(R.id.edit_password) EditText etPassword;
    @BindView(R.id.button_signup) Button btnSignUp;

    private ProgressWrapper mProgressWrapper;

    private String mUsername;
    private String mPassword;
    private String mEmail;

    @Override protected int getContentViewLayout() {
        return R.layout.activity_sign_up;
    }

    @Override protected void initView() {
        new ToolbarWrapper(this).setCustomTitle(R.string.mine_title_sign_up);
        mProgressWrapper = new ProgressWrapper();
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
        if (!ApiPath.USER_SIGNUP.equals(apiPath)) {
            throw new UnsupportedOperationException(apiPath);
        }
        mProgressWrapper.dismissProgress();
        if (success) {
            ToastWrapper.show(R.string.mine_msg_sign_up_success);
            ApiSignUp.Rsp signUpRsp = (ApiSignUp.Rsp) rsp;
            UserManager.getInstance().setUser(signUpRsp.getData().getUser(), signUpRsp.getData().getSession());
            finish();
        }
    }

    @OnTextChanged({R.id.edit_password, R.id.edit_name, R.id.edit_email}) void onTextChanged() {
        mUsername = etName.getText().toString();
        mEmail = etEmail.getText().toString();
        mPassword = etPassword.getText().toString();
        // 简单的注册条件判断: 用户名, 邮箱和密码不能为空.
        if (TextUtils.isEmpty(mUsername)
                || TextUtils.isEmpty(mEmail)
                || TextUtils.isEmpty(mPassword)) {
            btnSignUp.setEnabled(false);
        } else {
            btnSignUp.setEnabled(true);
        }
    }

    @OnClick(R.id.button_signup) void signUp() {
        // 用户注册.
        mProgressWrapper.showProgress(this);
        ApiSignUp apiSignUp = new ApiSignUp(mUsername, mEmail, Sha256Utils.bin2hex(mPassword));
        enqueue(apiSignUp);
    }

}
