package com.gfd.eshop.feature.help;


import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gfd.eshop.R;
import com.gfd.eshop.base.BaseActivity;
import com.gfd.eshop.base.utils.LogUtils;
import com.gfd.eshop.base.wrapper.PhotoWrapper;
import com.gfd.eshop.base.wrapper.ToolbarWrapper;
import com.gfd.eshop.network.core.ApiPath;
import com.gfd.eshop.network.core.ResponseEntity;

import java.net.MalformedURLException;
import java.net.URL;

import butterknife.BindView;

/**
 * 帮助页面
 */
public class HelpActivity extends BaseActivity {

    @BindView(R.id.web_view) WebView webView;

    @Override protected int getContentViewLayout() {
        return R.layout.activity_help;
    }

    @Override protected void initView() {
        //设置标题
        new ToolbarWrapper(this).setCustomTitle(R.string.mine_help);
        setWebView();
        webView.loadUrl(ApiPath.HELP);
    }

    @Override
    protected void onBusinessResponse(String apiPath, boolean success, ResponseEntity rsp) {
    }

    @Override public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
            return;
        }
        super.onBackPressed();
    }

    /** 设置WebView*/
    private void setWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                webView.loadUrl(url);
                return true;
            }
        });
    }

}
