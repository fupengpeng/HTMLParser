package com.example.administrator.htmlparser.Adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/4.
 */

public class ViewPagerAdapter extends PagerAdapter {
    //界面列表
    private ArrayList<View> views;
    private String[] mTabTitles = new String[]
            {"聚焦","公益", "创建", "志愿", "礼仪"};
    public ViewPagerAdapter(ArrayList<View> views){
        this.views = views;
    }



    /**
     * 获得当前界面数
     */
    @Override
    public int getCount() {
        if (views != null) {
            return mTabTitles.length;
        }
        return 0;
    }

    /**
     * 初始化position位置的界面
     */
    @Override
    public Object instantiateItem(View view, int position) {

        ((ViewPager) view).addView(views.get(position), 0);

        return views.get(position);
    }

    /**
     * 判断是否由对象生成界面
     */
    @Override
    public boolean isViewFromObject(View view, Object arg1) {
        return (view == arg1);
    }

    /**
     * 销毁position位置的界面
     */
    @Override
    public void destroyItem(View view, int position, Object arg2) {
        ((ViewPager) view).removeView(views.get(position));
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTabTitles[position];
    }
}
