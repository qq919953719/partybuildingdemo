package com.longhoo.net.widget.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2017/7/5.
 */

public abstract class BaseFragment extends Fragment {
    protected OnFragmentInteractionListener interactionListener;
    protected Unbinder unbinder;
    protected boolean isPrepared;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getContentId(), container, false);
        unbinder = ButterKnife.bind(this,view);
        isPrepared = true;
        initView(view);
        initToolbar();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            interactionListener = (OnFragmentInteractionListener) context;
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        isPrepared = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    protected abstract int getContentId();
    protected abstract void initView(View view);
    protected abstract void initToolbar();

}
