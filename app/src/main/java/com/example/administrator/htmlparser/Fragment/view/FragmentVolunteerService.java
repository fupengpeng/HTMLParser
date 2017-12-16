package com.example.administrator.htmlparser.Fragment.view;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.example.administrator.htmlparser.ArticleActivity;
import com.example.administrator.htmlparser.Fragment.BaseFragment;
import com.example.administrator.htmlparser.entity.Data;
import com.example.administrator.htmlparser.DataSynEvent;
import com.example.administrator.htmlparser.Fragment.impl.IFragmentVolunteerService;
import com.example.administrator.htmlparser.Adapter.NewsAdapter;
import com.example.administrator.htmlparser.R;
import com.example.administrator.htmlparser.common.Consts;
import com.example.administrator.htmlparser.event.ArticleSynEvent;
import com.example.administrator.htmlparser.presenter.factory.VolunteerServicePresenterFactory;
import com.example.administrator.htmlparser.presenter.impl.IVolunteerServicePresenter;
import com.example.administrator.htmlparser.utils.LogUtils;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/7/26.
 */

public class FragmentVolunteerService extends BaseFragment implements IFragmentVolunteerService {

    @BindView(R.id.fragment_volunteer_service)
    PullToRefreshListView fragmentVolunteerService;
    Unbinder unbinder;
    private IVolunteerServicePresenter presenter;
    /**
     * 当前页
     */
    private int currentPage = 1;
    String zysl = "1";
    ArrayList<Data> dataArrayList = new ArrayList<>();
    NewsAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, R.layout.fragment_volunteer_service);
        EventBus.getDefault().register(this);//订阅
        presenter = VolunteerServicePresenterFactory.newHome(this);
        setListeners();
        fragmentVolunteerService.setMode(PullToRefreshBase.Mode.BOTH);
        fragmentVolunteerService.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        fragmentVolunteerService.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载中...");
        fragmentVolunteerService.getLoadingLayoutProxy(false, true).setReleaseLabel("放开加载...");
        presenter.init(Consts.NetUrl.VOLUNTEERSERVICE_URL);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void setListeners() {
        fragmentVolunteerService.setOnRefreshListener(new InnerOnRefreshListener2());
        fragmentVolunteerService.setOnItemClickListener(new InnerOnItemClickListener());
    }
    /**
     * 子项点击监听器
     */
    public class InnerOnItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 数据源当前索引
            startActivity(ArticleActivity.class);
            LogUtils.e("---pos---", "" + position + "------" + dataArrayList.get(position - 1).getUrl());
            EventBus.getDefault().postSticky(new DataSynEvent(dataArrayList.get(position - 1).getUrl(), 7));
            EventBus.getDefault().postSticky(new ArticleSynEvent(Consts.NetUrl.VOLUNTEERSERVICE_URL,9));
            //
        }
    }
    /**
     * 刷新监听器
     */
    private class InnerOnRefreshListener2 implements PullToRefreshBase.OnRefreshListener2 {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
//            // 下拉刷新
//            refreshData(1);
            presenter.refresh(Consts.NetUrl.VOLUNTEERSERVICE_URL);
            new FinishRefresh().execute();
            LogUtils.e("-----", "----xia--");
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            // 判断是否还有更多数据
            if (currentPage >= Integer.valueOf(zysl)) {
                Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                // 加载完成
//                PullToRefreshUtils.refreshComplete(lvDataList);
                new FinishRefresh().execute();
                return;
            }
            presenter.loading(Consts.NetUrl.VOLUNTEERSERVICE_URL);

            LogUtils.e("-----", "----上--"+currentPage);
            new FinishRefresh().execute();
        }
    }
    /**
     * 处理订阅消息
     *
     * @param event 订阅消息
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventComing(DataSynEvent event) {
        if (event.getCount() == 1) {
            zysl= event.getMessage();
            LogUtils.e("----aaaaaaaaaaaaa--","---aaaaaaaaaaaaaaaaaaaaaaaaaaa---"+zysl);
        }
        if (event.getCount() == 2) {
            dataArrayList=event.getMessages();
            adapter = new NewsAdapter(getActivity());
            adapter.setList(dataArrayList);
            fragmentVolunteerService.setAdapter(adapter);
            LogUtils.e("---city-fffffffff--","---fffffffffff---"+event.getMessages());
        }
        if (event.getCount() == 3) {
            dataArrayList=event.getMessages();
            adapter = new NewsAdapter(getActivity());
            adapter.setList(dataArrayList);
            adapter.notifyDataSetChanged();
        }
        if (event.getCount() == 4) {
            currentPage=event.getCurrentPage();
        }
    }
    private class FinishRefresh extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            fragmentVolunteerService.onRefreshComplete();
            adapter.notifyDataSetChanged();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
