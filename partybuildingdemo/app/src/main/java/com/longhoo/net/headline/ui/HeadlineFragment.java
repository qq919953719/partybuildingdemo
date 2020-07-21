package com.longhoo.net.headline.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.longhoo.net.MyApplication;
import com.longhoo.net.R;
import com.longhoo.net.headline.adapter.HeadlineAdapter;
import com.longhoo.net.headline.bean.BannerBean;
import com.longhoo.net.headline.bean.HeadlineBean;
import com.longhoo.net.manageservice.ui.HuaTiContentActivity;
import com.longhoo.net.mine.ui.MeActivity;
import com.longhoo.net.study.ui.ActsEncrollDetailActivity;
import com.longhoo.net.study.ui.CurrentAffairsActivity;
import com.longhoo.net.study.ui.CurrentAffairsDetailActivity;
import com.longhoo.net.study.ui.ExaminationCenterActivity;
import com.longhoo.net.study.ui.VideoConferenceActivity;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.FileUtils;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.UpgradeManager;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.banner.BannerEntity;
import com.longhoo.net.widget.banner.SlideBannerView;
import com.longhoo.net.widget.base.BaseLazyFragment;
import com.longhoo.net.widget.base.WebViewActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

import static android.app.Activity.RESULT_OK;

/**
 * @author CK 主页——首页
 */
public class HeadlineFragment extends BaseLazyFragment implements SlideBannerView.BannerItemCLickListener, OnRefreshLoadmoreListener
        , HeaderAndFooterWrapper.OnItemClickListener, HttpRequestView, View.OnClickListener, UpgradeManager.UpGradeListener {


    SlideBannerView bannerView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    private Activity mActivity;
    private List<BannerBean.DataBean> bannerList = new ArrayList<>();
    private List<HeadlineBean.DataBean> dataList = new ArrayList<>();
    //private List<CurrentAffairsBean.DataBean> dataList = new ArrayList<>();


    private HeaderAndFooterWrapper adapterWrapper;
    private HeadlineAdapter adapter;

    // CurrentAffairsAdapter adapter;
    private View headerView;
    private HttpRequestPresenter requestPresenter;
    private String url = Consts.BASE_URL + "/Index/public_incorruptnews";


    //private String url = Consts.BASE_URL + "/index/public_articles1";

    private String bannerUrl = Consts.BASE_URL + "/Index/advers_list";
    private int mPage = 1;
    //签到提示框
    private AlertDialog signDialog;
    private TextView tvSignTitle;
    private TextView tvSignContent;
    private TextView tvSignCommit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_headline;
    }

    static int micount = 1;

    @Override
    protected void onLazyLoad() {
        smartRefreshLayout.autoRefresh(800);
    }

    @Override
    public void OnUpgradeResult(int iRetCode, int iUpgradeType) {
        if (UpgradeManager.UPGRADEMANAGER_UNCONNECTED == iUpgradeType) {
            return;
        }
        if (UpgradeManager.UPGRADEMANAGER_FORCEUPDATE == iUpgradeType
                || UpgradeManager.FINISH == iRetCode) {
            System.exit(0);
        }
    }


    @Override
    protected void initViews(View view) {
        /* EventBus.getDefault().register(this);*/
        MyApplication app = (MyApplication) getContext().getApplicationContext();
        mActivity = this.getActivity();
        dataList.clear();
        //dataList.addAll((ArrayList<? extends CurrentAffairsBean.DataBean>) FileUtils.readList(FileUtils.createCacheFile(mActivity, "data", "headline_list_cache.txt")));
        bannerList.clear();
        bannerList.addAll((ArrayList<? extends BannerBean.DataBean>) FileUtils.readList(FileUtils.createCacheFile(mActivity, "data", "headline_banner_cache.txt")));
        ULog.e(bannerList.size() + "数量");
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new HeadlineAdapter(mActivity, dataList);
        adapterWrapper = new HeaderAndFooterWrapper(adapter);
        adapterWrapper.setOnItemClickListener(this);
        recyclerView.setAdapter(adapterWrapper);
        requestPresenter = new HttpRequestPresenter(mActivity, this);
        addRefreshHeader();
        //if (micount == 1) {
        new UpgradeManager(this, getActivity(), true);
        //}
        //micount = 2;
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected long setLoadInterval() {
        return 0;
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", mPage + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
        getBanner();
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", mPage + "");
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onNetworkError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(mActivity, "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        if (!isPrepared) {
            return;
        }
        ULog.e(Consts.TAG, "headline:" + response);
        //处理数据
        List<HeadlineBean.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            HeadlineBean headlineBean = gson.fromJson(response, HeadlineBean.class);
            String code = headlineBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = headlineBean.getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        } catch (NullPointerException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }
        if (tempList == null) {
            ToastUtils.getInstance().showToast(getActivity(), "服务器异常~");
            smartRefreshLayout.finishRefresh(0);
            return;
        }
        dataList.clear();
        if (tempList.size() > 0) {
            dataList.addAll(tempList);
            mPage++;
            //缓存数据
            FileUtils.saveList(mActivity, (ArrayList) tempList, FileUtils.createCacheFile(mActivity, "data", "headline_list_cache.txt"));
        }
        addRefreshHeader();
        adapterWrapper.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(getActivity(), "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        if (!isPrepared) {
            return;
        }
        ULog.e(Consts.TAG, "headline:" + mPage + ":" + response);
        //处理数据
        List<HeadlineBean.DataBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            HeadlineBean headlineBean = gson.fromJson(response, HeadlineBean.class);
            String code = headlineBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = headlineBean.getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishLoadmore(0);
        } catch (NullPointerException e) {
            e.printStackTrace();
            smartRefreshLayout.finishLoadmore(0);
        }
        if (tempList2 == null) {
            ToastUtils.getInstance().showToast(getActivity(), "解析错误！");
            smartRefreshLayout.finishLoadmore(0);
            return;
        }
        //没有数据或数据<limit，说明无更多
        if (tempList2.size() <= 0) {
            ToastUtils.getInstance().showToast(mActivity, "没有更多了~");
            smartRefreshLayout.setEnableLoadmore(false);
        } else {
            dataList.addAll(tempList2);
            mPage++;
            adapterWrapper.notifyDataSetChanged();
        }
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(getActivity(), "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    private void addRefreshHeader() {
        if (headerView == null) {
            headerView = LayoutInflater.from(getActivity()).inflate(R.layout.layout_headline_tabs, recyclerView, false);
            bannerView = (SlideBannerView) headerView.findViewById(R.id.banner_view);
            headerView.findViewById(R.id.rl_header_gzdt).setOnClickListener(this);
            headerView.findViewById(R.id.rl_header_jcfc).setOnClickListener(this);
            headerView.findViewById(R.id.rl_header_dflz).setOnClickListener(this);
            headerView.findViewById(R.id.rl_header_tzgg).setOnClickListener(this);
            adapterWrapper.addHeaderView(headerView);
        }
        List<BannerEntity> bannerEntities = new ArrayList<>();
        for (int i = 0; i < bannerList.size(); i++) {
            bannerEntities.add(new BannerEntity(bannerList.get(i).getAid() + "", bannerList.get(i).getTitle(), bannerList.get(i).getPic(), bannerList.get(i).getType() + ""));
        }
        bannerView.setImagePath(bannerEntities);
        bannerView.setOnBannerItemClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (!EnterCheckUtil.getInstance().isLogin(true)) {
            return;
        }
        Intent intent = null;
        switch (view.getId()) {
            case R.id.iv_scan:
                IntentIntegrator.forSupportFragment(this).setPrompt("扫描条码或二维码").setCaptureActivity(MyCaptureActivity.class).initiateScan();
                break;
            case R.id.rl_header_gzdt: // 工作动态.......工作动态
                PartyflzActivity.goTo(mActivity, "2");
                break;
            case R.id.rl_header_jcfc:  //基层风采.......基层党建
                PartyflzActivity.goTo(mActivity, "3");
                break;
            case R.id.rl_header_dflz:  //党风廉政.......干部工作
                PartyflzActivity.goTo(mActivity, "5");
                break;
            case R.id.rl_header_tzgg:  //通知公告.......党风廉政

                // PartyflzActivity.goTo(mActivity, "4");

                Intent intent9 = new Intent(getActivity(), CurrentAffairsActivity.class);
                startActivity(intent9);
                break;
            case R.id.me_panel:
                intent = new Intent(mActivity, MeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBannerItemClick(int position) {
        if (position < 0 || position > bannerList.size() - 1) {
            return;
        }
        if (!EnterCheckUtil.getInstance().isLogin(true)) {
            return;
        }
        int type = bannerList.get(position).getType();
        String url = bannerList.get(position).getUrl();
        switch (type) {
            case 0: //静态图片
                break;
            case 1: //站外广告
                WebViewActivity.goToWebView(getActivity(), url, "", false);
                break;
            case 2: //通知公告
                NewsDetailActivity.goTo(getActivity(), url);
                break;
            case 3: //活动报名
                ActsEncrollDetailActivity.goTo(getActivity(), url);
                break;
            case 4: //工作动态
                NewsDetailActivity.goTo(getActivity(), url);
                break;
            case 5: //在线答题
                ExaminationCenterActivity.goTo(getActivity(), "1");
                break;
            case 6: //视频会议
                VideoConferenceActivity.goTo(getActivity());
                break;
            case 7: //党员社区
                HuaTiContentActivity.goTo(getActivity(), url, -1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ULog.e("ck", requestCode + " " + resultCode);
        String scanResult = "";
        if (resultCode == RESULT_OK) {
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                scanResult = result.getContents();
            }
        } else if (resultCode == MyCaptureActivity.RESULT_CODE_PICK_CODE) {
            // 从本地图片二维码获取链接
            if (data != null) {
                scanResult = data.getStringExtra("result");
            }
        }
        if (TextUtils.isEmpty(scanResult)) {
            ToastUtils.getInstance().showToast(mActivity, "取消扫描~");
        } else {
            ULog.e(Consts.TAG, "扫描地址：" + scanResult);
            //doSign(scanResult);
        }
    }

    @Override
    public void onDestroy() {
        finishRefreshLoad();
        hideSignDialog();
        super.onDestroy();
        /*EventBus.getDefault().unregister(this);*/
    }

    private void finishRefreshLoad() {
        if (smartRefreshLayout != null) {
            if (smartRefreshLayout.isRefreshing()) {
                smartRefreshLayout.finishRefresh(0);
            }
            if (smartRefreshLayout.isLoading()) {
                smartRefreshLayout.finishLoadmore(0);
            }
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position >= dataList.size()) {
            return;
        }

        if (dataList.get(position).getType() == 6) {

            if (TextUtils.isEmpty(dataList.get(position).getUrl())) {
                String nid = dataList.get(position).getNid() + "";
                CurrentAffairsDetailActivity.goTo(getActivity(), nid);
            } else {
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
//                Uri content_url = Uri.parse(dataList.get(position).getUrl());
//                intent.setData(content_url);
//                startActivity(intent);

                WebViewActivity.goToWebView(getActivity(), dataList.get(position).getUrl(), dataList.get(position).getTitle(), false);

            }

        } else {
            String nid = dataList.get(position).getNid() + "";
            NewsDetailActivity.goTo(getActivity(), nid);
        }

    }

    /**
     * 获取轮播图数据
     */
    private void getBanner() {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        OkHttpUtil.getInstance().doAsyncPost(bannerUrl, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                try {
                    BannerBean bannerBean = gson.fromJson(response, BannerBean.class);
                    List<BannerBean.DataBean> tempList = bannerBean.getData();
                    if (tempList != null) {
                        bannerList.clear();
                        bannerList.addAll(tempList);
                        //缓存数据
                        FileUtils.saveList(mActivity, (ArrayList) bannerList, FileUtils.createCacheFile(mActivity, "data", "headline_banner_cache.txt"));
                        addRefreshHeader();
                    }
                } catch (JsonSyntaxException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(String errorMsg) {

            }
        });
    }


    private void hideSignDialog() {
        if (signDialog != null) {
            if (signDialog.isShowing()) {
                signDialog.dismiss();
            }
        }
    }

    /**
     * 刷新列表点赞数
     *
     * @param
     */
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onMessageEvent(MessageEvent event) {
//        String msg = event.message;
//        if(TextUtils.equals(msg,"refresh")){
//            smartRefreshLayout.autoRefresh(0);
//        }else{
//            int position = event.position;
//            if (position < 0 || position > dataList.size() - 1) {
//                return;
//            }
//            String sub[] = event.message.split("\\|");
//            if (sub.length >= 3) {
//                dataList.get(position).setIszan(sub[0]);
//                dataList.get(position).setZan(sub[1]);
//                dataList.get(position).setCom(sub[2]);
//            }
//            int browse = 0;
//            try {
//                browse = Integer.parseInt(dataList.get(position).getBrowse());
//            } catch (NumberFormatException e) {
//                e.printStackTrace();
//            }
//            dataList.get(position).setBrowse(String.valueOf(++browse));
//            adapterWrapper.notifyItemChanged(position + adapterWrapper.getHeadersCount());
//        }
//    }

}
