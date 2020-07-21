package com.longhoo.net.widget.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.longhoo.net.utils.SPTool;
import com.longhoo.net.utils.ULog;
import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.Unbinder;


/**
 * Created by Administrator on 2016/10/20.
 */

public abstract class BaseLazyFragment extends Fragment {
    protected String LAST_LOAD_TIME;
    protected boolean isPrepared;
    protected boolean isVisible;
    protected boolean isHasLoaded;
    protected OnFragmentInteractionListener interactionListener;
    protected Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getContentId(),container,false);
        unbinder = ButterKnife.bind(this,view);
        initViews(view);
        initToolbar();
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        isPrepared = true;
        LAST_LOAD_TIME =getTag()+":last_load_time";
        onLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isPrepared = false;
        unbinder.unbind();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        onLoad();
    }

    protected  void onLoad(){
        if(isPrepared&&getUserVisibleHint()){
            //设置每隔多长时间执行一次
            if(setLoadInterval()!=0&&System.currentTimeMillis()- SPTool.getLong(getActivity(),LAST_LOAD_TIME,0)>setLoadInterval()){
                isHasLoaded = false;
                ULog.e("c_k",LAST_LOAD_TIME+" "+(System.currentTimeMillis()-SPTool.getLong(getActivity(),LAST_LOAD_TIME,0)+""));
            }
            if(!isHasLoaded){
                onLazyLoad();
                isHasLoaded = true;
                SPTool.putLong(getActivity(),LAST_LOAD_TIME,System.currentTimeMillis());
            }

        }
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(this.getClass().getSimpleName()); //统计页面
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(this.getClass().getSimpleName());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            interactionListener = (OnFragmentInteractionListener) context;
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    protected abstract int getContentId();
    protected abstract void onLazyLoad();
    protected abstract void initViews(View view);
    protected abstract void initToolbar();
    protected abstract long setLoadInterval();
}
