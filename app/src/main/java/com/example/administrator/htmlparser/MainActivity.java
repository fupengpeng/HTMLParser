package com.example.administrator.htmlparser;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.administrator.htmlparser.Adapter.NewsAdapter;
import com.example.administrator.htmlparser.Adapter.ViewPagerAdapter;
import com.example.administrator.htmlparser.activity.BaseActivity;
import com.example.administrator.htmlparser.activity.impl.IMainView;
import com.example.administrator.htmlparser.common.Consts;
import com.example.administrator.htmlparser.entity.Data;
import com.example.administrator.htmlparser.event.ArticleSynEvent;
import com.example.administrator.htmlparser.presenter.factory.MainViewPresenterFactory;
import com.example.administrator.htmlparser.presenter.impl.IMainViewPresenter;
import com.example.administrator.htmlparser.utils.BitmapUtils;
import com.example.administrator.htmlparser.utils.LogUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements IMainView, View.OnClickListener {



    @BindView(R.id.viewpager)
    ViewPager viewpager;

    String[] messageUrl = new String[4];

    @BindView(R.id.id_tablayout)
    TabLayout idTablayout;

    private IMainViewPresenter presenter;

    ArrayList<View> mViews;//定义一个ArrayList来存放view

    ViewPagerAdapter mViewPagerAdapter;//定义ViewPager适配器

    ArrayList<Data> dataFocus;
    ArrayList<Data> dataCity;
    ArrayList<Data> dataService;
    ArrayList<Data> dataCharm;
    NewsAdapter charmAdapter;
    ProgressDialog dialog;

    private int focusPage = 1;
    private int cityPage = 1;
    private int servicePage = 1;
    private int charmPage = 1;

    private int zfocusPage = 1;
    private int zcityPage = 1;
    private int zservicePage = 1;
    private int zcharmPage = 1;
    boolean wl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
        EventBus.getDefault().register(this);//订阅
        BitmapUtils.init(this);
        mViews = new ArrayList<View>();

        messageUrl[0] = Consts.NetUrl.CIVILIZATIONFOCUSING_URL;
        messageUrl[1] = Consts.NetUrl.CIVILIZEDCITY_URL;
        messageUrl[2] = Consts.NetUrl.VOLUNTEERSERVICE_URL;
        messageUrl[3] = Consts.NetUrl.CHARMSHOOL_URL;

        presenter = MainViewPresenterFactory.newHome(this);
        wl = isNetworkConnected();
        if (wl) {
            LogUtils.e("MainActivity----", "---有网---");
            init();
        } else {
            final CharSequence strDialogTitle = getString(R.string.network_Lost);
            final CharSequence strDialogBody = getString(R.string.check_network);
            dialog = ProgressDialog.show(this, strDialogTitle, strDialogBody, true);
            new Thread() {
                @Override
                public void run() {
                    try {
                        //设置线程睡眠3秒
                        sleep(1500);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        //删除所创建的myDialog对象
                        dialog.dismiss();
                    }
                    super.run();
                }
            }.start();
            LogUtils.e("MainActivity----", "---没网---");
        }
    }


    @Override
    protected boolean isSupportSwipeBack() {
        return false;
    }

    /**
     * 初始化数据
     */
    private void init() {
        LogUtils.e("----init----初始化数据 -- ");
        for (int i = 0; i < messageUrl.length; i++) {
            LogUtils.e("----init----初始化数据 -- " + "messageUrl-- length= " + messageUrl.length);
            presenter.init(messageUrl[i]);
        }
    }

    /**
     * 接收数据
     *
     * @param event 订阅消息
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN, priority = 300)
    public void eventComing(DataSynEvent event) {
        if (event.getCount() == 1) {
            zfocusPage = Integer.valueOf(event.getMessage());
            dataFocus = event.getMessages();
            LogUtils.e("--2---", "--fff---" + event.getMessages());
        }
        if (event.getCount() == 15) {
            zcityPage = Integer.valueOf(event.getMessage());
            dataCity = event.getMessages();
            LogUtils.e("--16---", "-----" + event.getMessages());
        }
        if (event.getCount() == 17) {
            zservicePage = Integer.valueOf(event.getMessage());
            dataService = event.getMessages();
            LogUtils.e("--18---", "-----" + event.getMessages());
        }
        if (event.getCount() == 19) {
            zcharmPage = Integer.valueOf(event.getMessage());
            dataCharm = event.getMessages();
            LogUtils.e("--20---", "--fff---" + event.getMessages());
        }
        if (event.getCount() == 21) {
            ArrayList<ArrayList<Data>> data = new ArrayList<ArrayList<Data>>();
            LogUtils.e("---21--", "" + dataCharm);
            data.add(dataFocus);
            data.add(dataCity);
            data.add(dataService);
            data.add(dataCharm);
            int[] ys = new int[4];
            ys[0] = focusPage;
            ys[1] = cityPage;
            ys[2] = servicePage;
            ys[3] = charmPage;

            int[] zys = new int[4];
            zys[0] = zfocusPage;
            zys[1] = zcityPage;
            zys[2] = zservicePage;
            zys[3] = zcharmPage;

            for (int i = 0; i < data.size(); i++) {

                message(data.get(i), charmAdapter,  messageUrl[i]);
            }
            img();
            mViewPagerAdapter = new ViewPagerAdapter(mViews);
            viewpager.setAdapter(mViewPagerAdapter);
            idTablayout.setupWithViewPager(viewpager);
            viewpager.setCurrentItem(0);
        }
        if (event.getCount() == 4) {
            focusPage = event.getCurrentPage();
            LogUtils.e("--4---", "-----________________________" + event.getCurrentPage());
        }
        if (event.getCount() == 25) {
            cityPage = event.getCurrentPage();
            LogUtils.e("--25---", "--fff---" + event.getMessages());
        }
        if (event.getCount() == 26) {
            servicePage = event.getCurrentPage();
            LogUtils.e("--26---", "--fff---" + event.getMessages());
        }
        if (event.getCount() == 27) {
            charmPage = event.getCurrentPage();
            LogUtils.e("--27---", "--fff---" + event.getMessages());
        }

    }

    /**
     * 公益Fragment中的四个按钮布局
     */
    private void img() {
        View craftFragment = LayoutInflater.from(this).inflate(R.layout.fragment_craft, null);
        final LinearLayout panels = (LinearLayout) craftFragment.findViewById(R.id.panels);
        LinearLayout rail = (LinearLayout) craftFragment.findViewById(R.id.rail);
        LinearLayout plane = (LinearLayout) craftFragment.findViewById(R.id.plane);
        LinearLayout phone = (LinearLayout) craftFragment.findViewById(R.id.phone);
        panels.setOnClickListener(this);
        rail.setOnClickListener(this);
        plane.setOnClickListener(this);
        phone.setOnClickListener(this);
        mViews.add(craftFragment);
    }


    /**
     *
     * @param messages
     * @param adapter
     * @param url
     */
    private void message(ArrayList<Data> messages, NewsAdapter adapter,  String url) {

        View vpChild = LayoutInflater.from(this).inflate(R.layout.vp_child, null);
        final PullToRefreshListView pullToRefreshListView = (PullToRefreshListView) vpChild.findViewById(R.id.listView);
        final ImageView ivReturnTop = (ImageView) vpChild.findViewById(R.id.iv_return_top);

        ivReturnTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pullToRefreshListView.getRefreshableView().setSelection(0);
                ivReturnTop.setVisibility(View.GONE);
            }
        });
        adapter = new NewsAdapter(this);
        adapter.setList(messages);
        pullToRefreshListView.setAdapter(adapter);
        mViews.add(vpChild);

        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载中...");
        pullToRefreshListView.getLoadingLayoutProxy(false, true).setReleaseLabel("放开加载...");

        setListeners(pullToRefreshListView, url, adapter, ivReturnTop);


        if (url.equals(Consts.NetUrl.CIVILIZATIONFOCUSING_URL)) {
            img();
        }
    }

    /**
     * 上拉加载、下拉刷新数据监听
     * @param list
     * @param url
     * @param adapter
     * @param img
     */
    private void setListeners(PullToRefreshListView list,  String url, NewsAdapter adapter, ImageView img) {
        LogUtils.e("----  设置监听事件  ----");
        list.setOnItemClickListener(new InnerOnItemClickListener());
        list.setOnRefreshListener(new InnerOnRefreshListener2( url, list, adapter));
        list.setOnScrollListener(new InnerOnScrollListener(list, img));
    }

    private class InnerOnScrollListener implements android.widget.AbsListView.OnScrollListener {
        PullToRefreshListView list;
        ImageView img;

        public InnerOnScrollListener(PullToRefreshListView list, ImageView img) {
            this.list = list;
            this.img = img;
        }

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            switch (scrollState) {
                // 当不滚动时
                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时

                    if (list.getRefreshableView().getFirstVisiblePosition() == 0) {
                        img.setVisibility(View.GONE);
                    } else if (list.getRefreshableView().getFirstVisiblePosition() > 3) {
                        img.setVisibility(View.VISIBLE);
                    }
                    // 判断滚动到顶部

                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时

                    break;
                case AbsListView.OnScrollListener.SCROLL_STATE_FLING:// 是当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时

                    break;
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        }
    }

    /**
     * 公益Fragment中四个按钮的点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.panels:

                startActivity(CraftActivity.class);
                EventBus.getDefault().postSticky(new DataSynEvent(Consts.NetUrl.PANELS, 11));
                break;
            case R.id.rail:

                startActivity(CraftActivity.class);
                EventBus.getDefault().postSticky(new DataSynEvent(Consts.NetUrl.RAIL, 11));
                break;
            case R.id.plane:
                startActivity(CraftActivity.class);
                EventBus.getDefault().postSticky(new DataSynEvent(Consts.NetUrl.PLANE, 11));
                break;
            case R.id.phone:

                startActivity(CraftActivity.class);
                EventBus.getDefault().postSticky(new DataSynEvent(Consts.NetUrl.PHONE, 11));
                break;
        }
    }

    /**
     * 刷新监听器
     */
    private class InnerOnRefreshListener2 implements PullToRefreshBase.OnRefreshListener2 {
        int y;
        int zy;
        ArrayList<Data> data;
        String s;
        PullToRefreshListView list;
        NewsAdapter adapter;

        public InnerOnRefreshListener2( String s, PullToRefreshListView list, NewsAdapter adapter) {
            LogUtils.e("-0000000-----", "-0--" + s);
            this.s = s;
            this.list = list;
            this.adapter = adapter;
        }

        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            // 下拉刷新
            if (viewpager.getCurrentItem() == 0) {
                this.data = dataFocus;
                LogUtils.e("-mes-----", "-0--" + focusPage);
            }
            if (viewpager.getCurrentItem() == 2) {
                this.data = dataCity;
                LogUtils.e("------", "-1--" + s);
            }
            if (viewpager.getCurrentItem() == 3) {
                this.data = dataService;
                LogUtils.e("------", "-2--" + s);
            }
            if (viewpager.getCurrentItem() == 4) {
                this.data = dataCharm;
                LogUtils.e("------", "-3--" + s);
            }
            presenter.refresh(s, data);
            new FinishRefresh(list, adapter).execute();
            LogUtils.e("-----", "----xia--");
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            if (viewpager.getCurrentItem() == 0) {
                this.y = focusPage;
                this.zy = zfocusPage;
                this.data = dataFocus;
                LogUtils.e("-mes-----", "-0--" + focusPage);
            }
            if (viewpager.getCurrentItem() == 2) {
                this.y = cityPage;
                this.zy = zcityPage;
                this.data = dataCity;
                LogUtils.e("------", "-1--" + s);
            }
            if (viewpager.getCurrentItem() == 3) {
                this.y = servicePage;
                this.zy = zservicePage;
                this.data = dataService;
                LogUtils.e("------", "-2--" + s);
            }
            if (viewpager.getCurrentItem() == 4) {
                this.y = charmPage;
                this.zy = zcharmPage;
                this.data = dataCharm;
                LogUtils.e("------", "-3--" + s);
            }
            LogUtils.e("--y----", "--" + y);
            LogUtils.e("--zy----", "--" + zy);
            // 判断是否还有更多数据
            if (y >= zy) {
                Toast.makeText(MainActivity.this, "没有更多数据", Toast.LENGTH_SHORT).show();
                // 加载完成
                new FinishRefresh(list, adapter).execute();
                return;
            }
            presenter.loading(s, y, data);

            new FinishRefresh(list, adapter).execute();
        }
    }

    /**
     * 子项点击监听器
     */
    public class InnerOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 数据源当前索引
            startActivity(ArticleActivity.class);

            if (viewpager.getCurrentItem() == 0) {

                EventBus.getDefault().postSticky(new DataSynEvent(dataFocus.get(position - 1).getUrl(), 7));
                EventBus.getDefault().postSticky(new ArticleSynEvent(Consts.NetUrl.CIVILIZATIONFOCUSING_URL, 9));
            } else if (viewpager.getCurrentItem() == 2) {
                EventBus.getDefault().postSticky(new DataSynEvent(dataCity.get(position - 1).getUrl(), 7));
                EventBus.getDefault().postSticky(new ArticleSynEvent(Consts.NetUrl.CIVILIZEDCITY_URL, 9));
            } else if (viewpager.getCurrentItem() == 3) {
                EventBus.getDefault().postSticky(new DataSynEvent(dataService.get(position - 1).getUrl(), 7));
                EventBus.getDefault().postSticky(new ArticleSynEvent(Consts.NetUrl.VOLUNTEERSERVICE_URL, 9));
            } else if (viewpager.getCurrentItem() == 4) {
                EventBus.getDefault().postSticky(new DataSynEvent(dataCharm.get(position - 1).getUrl(), 7));
                EventBus.getDefault().postSticky(new ArticleSynEvent(Consts.NetUrl.CHARMSHOOL_URL, 9));
            }
            LogUtils.e("---pos---", "" + position + "------" + viewpager.getCurrentItem());
            //
        }
    }
    /**
     * 刷新数据计时关闭刷新提示
     */
    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        PullToRefreshListView list;
        NewsAdapter adapter;

        public FinishRefresh(PullToRefreshListView list, NewsAdapter adapter) {
            this.list = list;
            this.adapter = adapter;
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
                LogUtils.e("-------", "----1000----");
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            list.onRefreshComplete();
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }


}