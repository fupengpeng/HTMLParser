package com.example.administrator.htmlparser;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.htmlparser.activity.BaseActivity;
import com.example.administrator.htmlparser.event.ArticleSynEvent;
import com.example.administrator.htmlparser.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/24.
 */

public class ArticleActivity extends BaseActivity {
    @BindView(R.id.article_title)
    TextView articleTitle;
    @BindView(R.id.article_time)
    TextView articleTime;
    @BindView(R.id.article_article)
    WebView articleArticle;
    boolean fist;
    String urls;
    @BindView(R.id.title_back)
    ImageView titleBack;
    @BindView(R.id.title)
    TextView title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_article);
        EventBus.getDefault().register(this);//订阅
titleBack.setImageResource(R.drawable.ic_arrow_back_black_24dp);
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(DataSynEvent event) {
        LogUtils.e("------3", "3333==" + event.getMessage());
        if (event.getCount() == 7) {
            String url = event.getMessage();
            url = url.substring(1);
            LogUtils.e("-urlffff-", url);
            init(url);
        }
        if (event.getCount() == 5) {
            articleTitle.setText(event.getMessage());
        }
        if (event.getCount() == 6) {
            articleTime.setText(event.getMessage());
        }


    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onArticleSynEvent(ArticleSynEvent event) {
        if (event.getCount() == 8) {
            String ss=event.getUrl();
            articleArticle.loadDataWithBaseURL(null, ss, "text/html", "utf-8", null);
        }
        if (event.getCount() == 9) {
            urls = event.getUrl();
            LogUtils.e("-999999999999-", urls);
        }
    }

    private void init(final String url) {
        LogUtils.e("----", "---"+url);
        new Thread() {
            @Override
            public void run() {
                super.run();
                Document doc = null;
                try {
                    doc = Jsoup.connect(urls + url).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String fa=url.substring(1,7);
                LogUtils.e("----fa--","--fa--"+fa);
                Elements pngs = doc.select("div.TRS_Editor img");
                for (Element element : pngs) {
                    String imgUrl = element.attr("src");
                    imgUrl=imgUrl.substring(1);
                        LogUtils.e("--st--","--"+imgUrl);
                    if (imgUrl.trim().startsWith("/")) {
                        imgUrl =urls+fa + imgUrl;          element.attr("src", imgUrl);
                    }      }

                Document docs= doc;

                Element el = docs.select("div.left").select(".more_l p.heiti").get(0);
                Element elTime = docs.select("div.left").select(".more_l p.f12px").get(0);
                LogUtils.e("--el---", el.text());
                LogUtils.e("--eltime---", elTime.text());
                Elements els = docs.select("div.TRS_Editor p");
                LogUtils.e("-1---", "---"+els.toString());
                Elements elsf = docs.select("div.TRS_Editor img");
                LogUtils.e("-2---", "---"+elsf.toString());
                for (int i = 0; i <elsf.size() ; i++) {
                Element elf = elsf.get(i);
                    String href = elf.attr("src");
                    LogUtils.e("2.链接", href);

                }

                fist = true;
                if (els.toString() == "") {
                    LogUtils.e("ArticleActivity", "---null----");
                    els = docs.select("div.TRS_Editor font");
                }
                String ss=els.toString();


                Element articleAll = null;
                for (int i = 0; i < els.size(); i++) {
                    Element elArticle = els.get(i);

                }
                EventBus.getDefault().post(new DataSynEvent(el.text(), 5));
                EventBus.getDefault().post(new DataSynEvent(elTime.text(), 6));
                EventBus.getDefault().post(new ArticleSynEvent(String.valueOf(els), 8));
            }
        }.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅

    }

    @OnClick(R.id.title_back)
    public void onViewClicked(View v) {
        switch (v.getId()){
            case R.id.title_back:
                finish();
                break;
        }
    }
}
