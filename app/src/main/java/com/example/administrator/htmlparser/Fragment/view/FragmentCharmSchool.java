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
import com.example.administrator.htmlparser.Fragment.impl.IFragmentCharmSchool;
import com.example.administrator.htmlparser.Adapter.NewsAdapter;
import com.example.administrator.htmlparser.R;
import com.example.administrator.htmlparser.common.Consts;
import com.example.administrator.htmlparser.event.ArticleSynEvent;
import com.example.administrator.htmlparser.presenter.factory.CharmSchoolPresenterFactory;
import com.example.administrator.htmlparser.presenter.impl.ICharmSchoolPresenter;
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

public class FragmentCharmSchool extends BaseFragment implements IFragmentCharmSchool {
    @BindView(R.id.fragment_charm_school)
    PullToRefreshListView fragmentCharmSchool;
    Unbinder unbinder;
    /**
     * 当前页
     */
    private int currentPage = 1;
    String zysl = "1";
    ArrayList<Data> dataArrayList = new ArrayList<>();
    NewsAdapter adapter;
    private ICharmSchoolPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, R.layout.fragment_charm_school);

        EventBus.getDefault().register(this);//订阅
        setListeners();
        fragmentCharmSchool.setMode(PullToRefreshBase.Mode.BOTH);
        fragmentCharmSchool.getLoadingLayoutProxy(false, true).setPullLabel("上拉加载");
        fragmentCharmSchool.getLoadingLayoutProxy(false, true).setRefreshingLabel("正在加载中...");
        fragmentCharmSchool.getLoadingLayoutProxy(false, true).setReleaseLabel("放开加载...");
        presenter = CharmSchoolPresenterFactory.newHome(this);
        presenter.init(Consts.NetUrl.CHARMSHOOL_URL);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    private void setListeners() {
        fragmentCharmSchool.setOnRefreshListener(new InnerOnRefreshListener21());
        fragmentCharmSchool.setOnItemClickListener(new InnerOnItemClickListener11());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    /**
     * 子项点击监听器
     */
    public class InnerOnItemClickListener11 implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // 数据源当前索引
            startActivity(ArticleActivity.class);
            LogUtils.e("---pos---", "" + position + "------" + dataArrayList.get(position - 1).getUrl());
            LogUtils.e("-- onItemClick-","---"+dataArrayList.toString());
            EventBus.getDefault().postSticky(new DataSynEvent(dataArrayList.get(position - 1).getUrl(), 7));
            EventBus.getDefault().postSticky(new ArticleSynEvent(Consts.NetUrl.CHARMSHOOL_URL, 9));
            //
        }
    }

    /**
     * 刷新监听器
     */
    private class InnerOnRefreshListener21 implements PullToRefreshBase.OnRefreshListener2 {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
//            // 下拉刷新
//            refreshData(1);
            presenter.refresh(Consts.NetUrl.CHARMSHOOL_URL);
            new FinishRefresh().execute();
            LogUtils.e("-shool----", "----xia--");
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            // 判断是否还有更多数据
            if (currentPage >= Integer.valueOf(zysl)) {
                Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                // 加载完成
//                PullToRefreshUtils.refreshComplete(lvDataList);
                LogUtils.e("-school--","--上---");
                new FinishRefresh().execute();
                return;
            }
            presenter.loading(Consts.NetUrl.CHARMSHOOL_URL);

            LogUtils.e("-----", "----上--" + currentPage);
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
            zysl = event.getMessage();
            LogUtils.e("------", "------" + zysl);
        }
        if (event.getCount() == 2) {
            dataArrayList = event.getMessages();
            adapter = new NewsAdapter(getActivity());
            adapter.setList(dataArrayList);
            fragmentCharmSchool.setAdapter(adapter);
            LogUtils.e("---ff---", "------" + event.getMessages());
        }
        if (event.getCount() == 3) {
            dataArrayList = event.getMessages();
            LogUtils.e("---","-dataArrayList--"+dataArrayList);
            adapter = new NewsAdapter(getActivity());
            adapter.setList(dataArrayList);
            adapter.notifyDataSetChanged();
        }
        if (event.getCount() == 4) {
            currentPage = event.getCurrentPage();
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
            fragmentCharmSchool.onRefreshComplete();
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);//解除订阅
    }
}
