package com.example.administrator.htmlparser.Fragment.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.htmlparser.Adapter.CraftAdapter;
import com.example.administrator.htmlparser.Fragment.BaseFragment;
import com.example.administrator.htmlparser.DataSynEvent;
import com.example.administrator.htmlparser.Fragment.impl.IFragmentCraft;
import com.example.administrator.htmlparser.R;
import com.example.administrator.htmlparser.common.Consts;
import com.example.administrator.htmlparser.presenter.factory.CraftPresenterFactory;
import com.example.administrator.htmlparser.presenter.impl.ICraftPresenter;
import com.example.administrator.htmlparser.utils.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 公益Fragment
 */

public class FragmentCraft extends BaseFragment implements IFragmentCraft {

    Unbinder unbinder;
    @BindView(R.id.panels)
    LinearLayout panels;
    @BindView(R.id.rail)
    LinearLayout rail;
    @BindView(R.id.plane)
    LinearLayout plane;
    @BindView(R.id.phone)
    LinearLayout phone;
    private ICraftPresenter presenter;
    String[] message;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, R.layout.fragment_craft);
        EventBus.getDefault().register(this);//订阅
        presenter = CraftPresenterFactory.newHome(this);
        presenter.init(Consts.NetUrl.CRAFT);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }



    /**
     * 处理订阅消息
     *
     * @param event 订阅消息
     */
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventComing(DataSynEvent event) {
        if (event.getCount() == 10) {
            LogUtils.e("FragmentCraft",  "---" + event.getMessageCraft());

            message = event.getMessageCraft();
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

    @OnClick({R.id.panels, R.id.rail, R.id.plane, R.id.phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.panels:
                startWeb(message[0]);
                break;
            case R.id.rail:
                startWeb(message[1]);
                break;
            case R.id.plane:
                startWeb(message[2]);
                break;
            case R.id.phone:
                startWeb(message[3]);
                break;
        }
    }
}
