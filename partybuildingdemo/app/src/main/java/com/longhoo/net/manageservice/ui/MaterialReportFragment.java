package com.longhoo.net.manageservice.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.longhoo.net.R;
import com.longhoo.net.manageservice.adapter.MaterialReportAdapter;
import com.longhoo.net.manageservice.bean.MaterialReportListBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseLazyFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 材料上报子页面
 */
public class MaterialReportFragment extends BaseLazyFragment implements OnRefreshLoadmoreListener, HttpRequestView,
        HeaderAndFooterWrapper.OnItemClickListener, MaterialReportAdapter.OnGridItemClickListener {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.tv_report)
    TextView tvReport;
    private String type;
    //private String title;
    private HttpRequestPresenter requestPresenter;
    private Activity mActivity;
    private int page;
    private HeaderAndFooterWrapper adapterWrapper;
    private List<MaterialReportListBean.DataBeanX.DataBean> dataList = new ArrayList<>();
    private MaterialReportAdapter adapter;
    private String url;
    private String curTime="2020";
    public static final String TYPE_SPECIAL = "1"; //专项材料上报
    public static final String TYPE_GROUP = "2"; //集体材料上报
    public static final String TYPE_PERSONAL = "3"; //个人材料上报

    public static MaterialReportFragment newInstance(String type) {
        MaterialReportFragment fragment = new MaterialReportFragment();
        Bundle args = new Bundle();
        args.putString("menu_type", type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_material_report;
    }

    @Override
    protected void onLazyLoad() {
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initViews(View view) {
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
            type = getArguments().getString("menu_type");
        }
        ULog.e("ck--", type + ":ffff");
        if (TextUtils.equals(type, TYPE_SPECIAL)) {
            tvReport.setVisibility(View.GONE);
        } else if (TextUtils.equals(type, TYPE_GROUP)) {
            tvReport.setVisibility(View.VISIBLE);
            tvReport.setText("上报集体获奖");
        } else if (TextUtils.equals(type, TYPE_PERSONAL)) {
            tvReport.setVisibility(View.VISIBLE);
            tvReport.setText("上报个人获奖");
        }
        url = Consts.BASE_URL + "/Inforeporting/infoReportingList";
        mActivity = this.getActivity();
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(mActivity));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(mActivity));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new MaterialReportAdapter(mActivity, dataList, type);
        adapter.setOnGridItemClickListemer(this);
        adapterWrapper = new HeaderAndFooterWrapper(adapter);
        adapterWrapper.setOnItemClickListener(this);
        recyclerView.setAdapter(adapterWrapper);
        requestPresenter = new HttpRequestPresenter(mActivity, this);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected long setLoadInterval() {
        return 0;
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
        //处理数据
        List<MaterialReportListBean.DataBeanX.DataBean> tempList = null;
        Gson gson = new Gson();
        try {
            MaterialReportListBean listBean = gson.fromJson(response, MaterialReportListBean.class);
            String code = listBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishRefresh(0);
                return;
            }
            tempList = listBean.getData().getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }catch (NullPointerException e){
            e.printStackTrace();
            smartRefreshLayout.finishRefresh(0);
        }
        if (tempList == null) {
            //ToastUtils.getInstance().showToast(mActivity, "暂无数据~");
            smartRefreshLayout.finishRefresh(0);
            return;
        }
        dataList.clear();
        if (tempList.size() > 0) {
            dataList.addAll(tempList);
            page++;
        }
        adapterWrapper.notifyDataSetChanged();
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onRefreshError() {
        ToastUtils.getInstance().showToast(mActivity, "刷新失败~");
        smartRefreshLayout.finishRefresh(0);
    }

    @Override
    public void onLoadMoreSuccess(String response) {
        if (!isPrepared) {
            return;
        }
        //处理数据
        List<MaterialReportListBean.DataBeanX.DataBean> tempList2 = null;
        Gson gson = new Gson();
        try {
            MaterialReportListBean listBean = gson.fromJson(response, MaterialReportListBean.class);
            String code = listBean.getCode();
            if (!TextUtils.equals(code, "0")) {
                ToastUtils.getInstance().showToast(mActivity, "获取数据失败~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            tempList2 = listBean.getData().getData();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            smartRefreshLayout.finishLoadmore(0);
        }catch (NullPointerException e){
            e.printStackTrace();
            smartRefreshLayout.finishLoadmore(0);
        }
        if (tempList2 == null) {
            //ToastUtils.getInstance().showToast(mActivity, "暂无数据！");
            smartRefreshLayout.finishLoadmore(0);
            return;
        }
        //没有数据或数据<limit，说明无更多
        if (tempList2.size() <= 0) {
            ToastUtils.getInstance().showToast(mActivity, "没有更多了~");
            smartRefreshLayout.setEnableLoadmore(false);
        } else {
            dataList.addAll(tempList2);
            page++;
            adapterWrapper.notifyDataSetChanged();
        }
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(mActivity, "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    @OnClick({R.id.tv_report})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_report:
                if(TextUtils.equals(type, TYPE_GROUP)&&EnterCheckUtil.getInstance().IS_MEMBER()){
                    ToastUtils.getInstance().showToast(getActivity(),"您当前没有此权限！");
                    return;
                }
                ReportAwardActivity.goTo(getActivity(), type);
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        if (position >= dataList.size()) {
            return;
        }
        if (TextUtils.equals(type, TYPE_SPECIAL)) {
            SpecialMeterialReportDetailActivity.goTo(getActivity(), dataList.get(position).getSid());
        }else{
            AwardDetailsActivity.goTo(getActivity(),dataList.get(position).getSid());
        }
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", page + "");
        params.put("type", type);
        params.put("time",curTime);
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smartRefreshLayout.setEnableLoadmore(true);
        page = 1;
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("page", page + "");
        params.put("type", type);
        params.put("time",curTime);
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_REFRESH, params);
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
    public void onDestroy() {
        finishRefreshLoad();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        String msg = event.message;
        if (TextUtils.equals(msg, "report_award_refresh")) {
            smartRefreshLayout.autoRefresh(200);
        }
    }

    @Override
    public void onGridItemClick(int listPosition, int position) {
//        List<LocalMedia> selectList = new ArrayList<>();
//        for (String picPath : dataList.get(listPosition).getFiles()) {
//            selectList.add(new LocalMedia(picPath, 0, PictureMimeType.ofImage(), ""));
//        }
//        if (selectList.size() > 0) {
//            LocalMedia media = selectList.get(position);
//            String pictureType = media.getPictureType();
//            int mediaType = PictureMimeType.pictureToVideo(pictureType);
//            switch (mediaType) {
//                case 1:
//                    PictureSelector.create(this).externalPicturePreview(position, selectList);
//                    break;
//            }
//        }
        if (TextUtils.equals(type, TYPE_SPECIAL)) {
            SpecialMeterialReportDetailActivity.goTo(getActivity(), dataList.get(listPosition).getSid());
        }else{
            AwardDetailsActivity.goTo(getActivity(),dataList.get(listPosition).getSid());
        }
    }

    public void doRefresh(String time){
        this.curTime = time;
        smartRefreshLayout.autoRefresh(200);
    }
}
