package com.example.administrator.htmlparser;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;

import com.example.administrator.htmlparser.activity.BaseActivity;
import com.example.administrator.htmlparser.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 公益子界面
 */

public class CraftActivity extends BaseActivity implements View.OnKeyListener {
    @BindView(R.id.web)
    WebView web;
    @BindView(R.id.web_back)
    Button webBack;
    @BindView(R.id.web_forward)
    Button webForward;
    @BindView(R.id.title_back)
    ImageView titleBack;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_cartf);
        EventBus.getDefault().register(this);//订阅
        titleBack.setImageResource(R.drawable.ic_arrow_back_black_24dp);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onArticleSynEvent(final DataSynEvent event) {
        if (event.getCount() == 11) {
            LogUtils.e("----", "----" + event.getMessage());
            web.getSettings().setDatabaseEnabled(true);
            web.loadUrl(event.getMessage());
            web.getSettings().setJavaScriptEnabled(true);
            web.getSettings().setAllowContentAccess(true);
            web.getSettings().setBuiltInZoomControls(true);
            web.getSettings().setUseWideViewPort(true);
            web.getSettings().setLoadWithOverviewMode(true);
            web.setOnKeyListener(this);
            web.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    view.loadUrl(event.getMessage());
                    return true;
                }
            });
        }
    }

    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onDestroy() {
        if (web != null) {
            web.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            web.clearHistory();

            ((ViewGroup) web.getParent()).removeView(web);
            web.destroy();
            web = null;
        }
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅

    }

    @OnClick({R.id.web_back, R.id.web_forward, R.id.title_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.web_back:

                webForward.setEnabled(true);
                if (web.canGoBack()) {
                    web.goBack();
                    webForward.setEnabled(true);
                } else {
                    finish();
                }
                LogUtils.e("----", "----" + web.canGoBack());
                break;
            case R.id.web_forward:
                web.goForward();
                if (!web.canGoForward()) {

                    webForward.setEnabled(false);
                }
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            // 返回键退回
            finish();
            return true;
        } else
            return false;
    }

}
