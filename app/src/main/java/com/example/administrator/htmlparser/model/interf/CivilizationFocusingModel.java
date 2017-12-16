package com.example.administrator.htmlparser.model.interf;

import com.example.administrator.htmlparser.entity.Data;
import com.example.administrator.htmlparser.DataSynEvent;
import com.example.administrator.htmlparser.common.Consts;
import com.example.administrator.htmlparser.model.impl.ICivilizationFocusingModel;
import com.example.administrator.htmlparser.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * 唯一model
 */

public class CivilizationFocusingModel implements ICivilizationFocusingModel {

    String[] message = new String[4];
    /**
     * 当前页
     */
    private int currentPage = 1;
    private int number;

    @Override
    public void init(String url) {


        LogUtils.e("----model----init----  " );
        ArrayList<Data> dataArrayList;
        dataArrayList = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements els = doc.select("div.left").select(".more_l li a");
        LogUtils.e("----model----init---- 一、HTML內容", els.toString());
        String zys = doc.select("SCRIPT").get(5).toString();
        LogUtils.e("----model----init---- ", "-zys---" + zys);
        String ff = zys.substring(zys.indexOf("(") + 1, zys.indexOf(","));
        LogUtils.e("-----model----init----", "-zys---" + ff);

        for (int j = 0; j < els.size(); j++) {
            Element el = els.get(j);

            String title = el.text();
            String href = el.attr("href");

            Data data = new Data(title, href);
            dataArrayList.add(data);
        }
        if (url == Consts.NetUrl.CIVILIZATIONFOCUSING_URL) {
            LogUtils.e("----model----init--------", "wmjj");
//            EventBus.getDefault().postSticky(new DataSynEvent(ff, 1));
//            EventBus.getDefault().postSticky(new DataSynEvent(dataArrayList, 2));

            EventBus.getDefault().postSticky(new DataSynEvent(1,ff,dataArrayList));
            number++;
        } else if (url == Consts.NetUrl.CIVILIZEDCITY_URL) {
            LogUtils.e("----model----init--------", "wmcscj");
//            EventBus.getDefault().postSticky(new DataSynEvent(ff, 15));
//            EventBus.getDefault().postSticky(new DataSynEvent(dataArrayList, 16));

            EventBus.getDefault().postSticky(new DataSynEvent(15,ff,dataArrayList));
            number++;
        } else if (url == Consts.NetUrl.VOLUNTEERSERVICE_URL) {
            LogUtils.e("----model----init--------", "zyfw");
//            EventBus.getDefault().postSticky(new DataSynEvent(ff, 17));
//            EventBus.getDefault().postSticky(new DataSynEvent(dataArrayList, 18));

            EventBus.getDefault().postSticky(new DataSynEvent(17,ff,dataArrayList));
            number++;
        } else if (url == Consts.NetUrl.CHARMSHOOL_URL) {
            LogUtils.e("----model----init--------", "wmlyxx");
//            EventBus.getDefault().postSticky(new DataSynEvent(ff, 19));
//            EventBus.getDefault().postSticky(new DataSynEvent(dataArrayList, 20));

            EventBus.getDefault().postSticky(new DataSynEvent(19,ff,dataArrayList));
            number++;
        }
        if (number == 4) {
            LogUtils.e("----model----init--------", "============================");
            EventBus.getDefault().postSticky(new DataSynEvent("", 21));
            number = 0;
        }
    }

    @Override
    public void loading(String url, int y, ArrayList<Data> datas) {
        LogUtils.e("---loading--", "--y = " + y + "url = " + url );
        Document doc = null;
        try {
            LogUtils.e("---loading--", "--             ---- ");
            doc = Jsoup.connect(url + "index_" + y + ".html").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 上拉加载
        y++;

        refreshData(y, url);

        Elements els = doc.select("div.left").select(".more_l li a");


        LogUtils.e("一、HTML內容", els.toString());

        for (int j = 0; j < els.size(); j++) {
            Element el = els.get(j);
            LogUtils.e("1.标题", el.text());
            String title = el.text();
            String href = el.attr("href");
            LogUtils.e("2.链接", href);
            Data data = new Data(title, href);
            datas.add(data);
        }

    }

    @Override
    public void refresh(String url, ArrayList<Data> datas) {
        LogUtils.e("----refresh----", "===" + datas.toString());
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements els = doc.select("div.left").select(".more_l li a");
        String zys = doc.select("SCRIPT").get(5).toString();
        LogUtils.e("----refresh----", "-zys---" + zys);
        String ff = zys.substring(zys.indexOf("(") + 1, zys.indexOf(","));
        LogUtils.e("-refresh---", "-zys---" + datas);
        LogUtils.e("---refresh---一、HTML內容", els.toString());
        if (datas != null) {
            datas.clear();
        }
        for (int j = 0; j < els.size(); j++) {
            Element el = els.get(j);
            LogUtils.e("----refresh----1.标题", el.text());
            String title = el.text();
            String href = el.attr("href");
            LogUtils.e("----refresh----2.链接", href);
            Data data = new Data(title, href);
            LogUtils.e("----refresh----", "---" + data.toString());
            datas.add(data);
        }


        // 下拉刷新
        refreshData(1, url);
    }

    @Override
    public void Img(String url) {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements els = doc.select("dd a");
        LogUtils.e("-ss----", "---" + url);
        for (int j = 0; j < els.size(); j++) {
            Element el = els.get(j);
            String href = el.attr("href");
            LogUtils.e("2.链接", href);
            message[j] = href;
        }
        LogUtils.e("--message---", "  " + message.length);
        EventBus.getDefault().postSticky(new DataSynEvent(message, 10));
    }

    /**
     * 刷新数据
     */
    private void refreshData(int page, String url) {
        LogUtils.e("--refreshData---  刷新数据  ", " page= " + page + "url = " + url);
        currentPage = page;
        if (url.equals(Consts.NetUrl.CIVILIZATIONFOCUSING_URL)) {
            LogUtils.e("--refreshData---  刷新数据  ","wmjj --" + " page= " + page + "url = " + url);
            EventBus.getDefault().post(new DataSynEvent(currentPage, 4));
        } else if (url.equals(Consts.NetUrl.CIVILIZEDCITY_URL)) {
            LogUtils.e("--refreshData---  刷新数据  ", "wmcscj -- page= " + page + "url = " + url);
            EventBus.getDefault().post(new DataSynEvent(currentPage, 25));
        } else if (url.equals(Consts.NetUrl.VOLUNTEERSERVICE_URL)) {
            LogUtils.e("--refreshData---  刷新数据  ", " wmlyxx -- page= " + page + "url = " + url);
            EventBus.getDefault().post(new DataSynEvent(currentPage, 26));
        } else if (url.equals(Consts.NetUrl.CHARMSHOOL_URL)) {
            LogUtils.e("--refreshData---  刷新数据  ", "zyfw -- page= " + page + "url = " + url);
            EventBus.getDefault().post(new DataSynEvent(currentPage, 27));
        }
        // 加载数据
    }
}
