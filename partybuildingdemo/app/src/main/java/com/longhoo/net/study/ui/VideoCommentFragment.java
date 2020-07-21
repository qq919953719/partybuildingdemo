package com.longhoo.net.study.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.study.adapter.VideoCommentAdapter;
import com.longhoo.net.study.adapter.VideoConCommentAdapter;
import com.longhoo.net.study.bean.VideoCommentBean;
import com.longhoo.net.study.bean.VideoConCommentBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.Utils;
import com.longhoo.net.utils.httprequest.HttpRequestPresenter;
import com.longhoo.net.utils.httprequest.HttpRequestView;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseFragment;
import com.longhoo.net.widget.base.BaseLazyFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 视频评论列表
 */
public class VideoCommentFragment extends BaseLazyFragment implements TextView.OnEditorActionListener, HttpRequestView, OnRefreshLoadmoreListener {
    public static final int PARTYCLASS_COMMENT = 0; //党课展示
    public static final int VIDEO_CON_COMMENT = 1; //视频会议
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.smart_refresh_layout)
    SmartRefreshLayout smartRefreshLayout;
    @BindView(R.id.et_comment)
    EditText etComment;

    private int type;
    private String videoId="";
    private VideoCommentAdapter adapter_class;
    private VideoConCommentAdapter adapter_con;
    private HeaderAndFooterWrapper adapterWrapper;
    private List<VideoCommentBean.DataBean> dataList_class = new ArrayList<>();
    private List<VideoConCommentBean.DataBean.ListBean> dataList_con = new ArrayList<>();
    private HttpRequestPresenter requestPresenter;
    private int mPage = 1;

    public static VideoCommentFragment newInstance(int type, String videoId) {
        VideoCommentFragment fragment = new VideoCommentFragment();
        Bundle args = new Bundle();
        args.putInt("type", type);
        args.putString("videoId", videoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            type = getArguments().getInt("type");
            videoId = getArguments().getString("videoId");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_video_comment;
    }

    @Override
    protected void onLazyLoad() {
        smartRefreshLayout.autoRefresh(200);
    }

    @Override
    protected void initViews(View view) {
        etComment.setOnEditorActionListener(this);
        smartRefreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        smartRefreshLayout.setOnRefreshLoadmoreListener(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        if (type == PARTYCLASS_COMMENT) {
            adapter_class = new VideoCommentAdapter(getActivity(), dataList_class);
            adapterWrapper = new HeaderAndFooterWrapper(adapter_class);
        } else {
            adapter_con = new VideoConCommentAdapter(getActivity(), dataList_con);
            adapterWrapper = new HeaderAndFooterWrapper(adapter_con);
        }
        recyclerView.setAdapter(adapterWrapper);
        requestPresenter = new HttpRequestPresenter(getActivity(), this);

        InputFilter inputFilter = new InputFilter() {
            Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_,.?!:;…~_\\-\"\"/@*+'()<>{}/[/]()<>{}\\[\\]=%&$|\\/♀♂#¥£¢€\"^` ，。？！：；……～“”、“（）”、（——）‘’＠‘·’＆＊＃《》￥《〈〉》〈＄〉［］￡［］｛｝｛｝￠【】【】％〖〗〖〗／〔〕〔〕＼『』『』＾「」「」｜﹁﹂｀．]");

            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                Matcher matcher = pattern.matcher(charSequence);
                if (!matcher.find()) {
                    return null;
                } else {
                    ToastUtils.getInstance().showToast(getActivity(), "不允许输入非法字符！");
                    return "";
                }

            }
        };
        etComment.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(120)});
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected long setLoadInterval() {
        return 0;
    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_SEND) {
            String content = etComment.getText().toString().trim();
            if (TextUtils.isEmpty(content)) {
                ToastUtils.getInstance().showToast(getActivity(), "请输入评论内容！");
                return true;
            }
            if (type == PARTYCLASS_COMMENT) {
                doAddClassComment(content);
            }else if(type==VIDEO_CON_COMMENT){
                doAddConComment(content);
            }
        }
        return true;
    }

    @Override
    public void onNetworkError() {
        finishRefreshLoad();
        ToastUtils.getInstance().showToast(getActivity(), "网络连接异常，请检查网络设置~");
    }

    @Override
    public void onRefreshSuccess(String response) {
        if (type == PARTYCLASS_COMMENT) {
            List<VideoCommentBean.DataBean> tempList = null;
            Gson gson = new Gson();
            try {
                VideoCommentBean listBean = gson.fromJson(response, VideoCommentBean.class);
                String code = listBean.getCode();
                if (!TextUtils.equals(code, "0")) {
                    ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                    smartRefreshLayout.finishRefresh(0);
                    return;
                }
                tempList = listBean.getData();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                smartRefreshLayout.finishRefresh(0);
            }
            dataList_class.clear();
            if (tempList != null && tempList.size() > 0) {
                dataList_class.addAll(tempList);
                mPage++;
            }
        } else if (type == VIDEO_CON_COMMENT) {
            //处理数据
            List<VideoConCommentBean.DataBean.ListBean> tempList = null;
            Gson gson = new Gson();
            try {
                VideoConCommentBean listBean = gson.fromJson(response, VideoConCommentBean.class);
                String code = listBean.getCode();
                if (!TextUtils.equals(code, "0")) {
                    ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                    smartRefreshLayout.finishRefresh(0);
                    return;
                }
                tempList = listBean.getData().getList();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                smartRefreshLayout.finishRefresh(0);
            }
            dataList_con.clear();
            if (tempList != null && tempList.size() > 0) {
                dataList_con.addAll(tempList);
                mPage++;
            }
        }

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
        if (type == PARTYCLASS_COMMENT) {
            List<VideoCommentBean.DataBean> tempList2 = null;
            Gson gson = new Gson();
            try {
                VideoCommentBean taiZhangListBean = gson.fromJson(response, VideoCommentBean.class);
                String code = taiZhangListBean.getCode();
                if (!TextUtils.equals(code, "0")) {
                    ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                    smartRefreshLayout.finishLoadmore(0);
                    return;
                }
                tempList2 = taiZhangListBean.getData();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                smartRefreshLayout.finishLoadmore(0);
            }
            if (tempList2 == null) {
                ToastUtils.getInstance().showToast(getActivity(), "没有更多了~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            //没有数据或数据<limit，说明无更多
            if (tempList2.size() <= 0) {
                ToastUtils.getInstance().showToast(getActivity(), "没有更多了~");
                smartRefreshLayout.setEnableLoadmore(false);
            } else {
                dataList_class.addAll(tempList2);
                mPage++;
                adapterWrapper.notifyDataSetChanged();
            }
            smartRefreshLayout.finishLoadmore(0);
        } else if (type == VIDEO_CON_COMMENT) {
            List<VideoConCommentBean.DataBean.ListBean> tempList2 = null;
            Gson gson = new Gson();
            try {
                VideoConCommentBean taiZhangListBean = gson.fromJson(response, VideoConCommentBean.class);
                String code = taiZhangListBean.getCode();
                if (!TextUtils.equals(code, "0")) {
                    ToastUtils.getInstance().showToast(getActivity(), "获取数据失败~");
                    smartRefreshLayout.finishLoadmore(0);
                    return;
                }
                tempList2 = taiZhangListBean.getData().getList();
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                smartRefreshLayout.finishLoadmore(0);
            }
            if (tempList2 == null) {
                ToastUtils.getInstance().showToast(getActivity(), "没有更多了~");
                smartRefreshLayout.finishLoadmore(0);
                return;
            }
            //没有数据或数据<limit，说明无更多
            if (tempList2.size() <= 0) {
                ToastUtils.getInstance().showToast(getActivity(), "没有更多了~");
                smartRefreshLayout.setEnableLoadmore(false);
            } else {
                dataList_con.addAll(tempList2);
                mPage++;
                adapterWrapper.notifyDataSetChanged();
            }
            smartRefreshLayout.finishLoadmore(0);
        }

    }

    @Override
    public void onLoadMoreError() {
        ToastUtils.getInstance().showToast(getActivity(), "加载失败~");
        smartRefreshLayout.finishLoadmore(0);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        String url = "";
        Map<String, String> params = new HashMap<>();
        if (type == PARTYCLASS_COMMENT) {
            url = Consts.BASE_URL + "/Classdisplay/getComList";
            params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
            params.put("did", videoId);
            params.put("page", mPage + "");
            params.put("pagesize", "20");
        } else if (type == VIDEO_CON_COMMENT) {
            url = Consts.BASE_URL + "/live/liveinfo_lists.html";
            params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
            params.put("lid", videoId + "");
            params.put("page", mPage + "");
            params.put("pagesize", "20");
        }
        requestPresenter.doHttpData(url, Consts.REQUEST_METHOD_POST, Consts.REQUEST_LOADMORE, params);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        String url = "";
        mPage = 1;
        Map<String, String> params = new HashMap<>();
        if (type == PARTYCLASS_COMMENT) {
            url = Consts.BASE_URL + "/Classdisplay/getComList";
            params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
            params.put("did", videoId);
            params.put("page", mPage + "");
            params.put("pagesize", "20");
        } else if (type == VIDEO_CON_COMMENT) {
            url = Consts.BASE_URL + "/live/liveinfo_lists.html";
            params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
            params.put("lid", videoId + "");
            params.put("page", mPage + "");
            params.put("pagesize", "20");
        }
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

    /**
     * 添加党课展示评论
     *
     * @param content
     */
    private void doAddClassComment(String content) {
        Utils.showHideSoftInput(getActivity(), etComment, false);
        String url = Consts.BASE_URL + "/Classdisplay/setComment";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("did", videoId + "");
        params.put("content", content);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (TextUtils.equals(code, "0")) {
                        etComment.setText("");
                        smartRefreshLayout.autoRefresh(200);
                    }
                    ToastUtils.getInstance().showToast(getActivity(), msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(getActivity(), "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(getActivity(), "网络异常~");
            }
        });
    }

    /**
     * 添加视频会议评论
     *
     * @param content
     */
    private void doAddConComment(String content) {
        Utils.showHideSoftInput(getActivity(), etComment, false);
        String url = Consts.BASE_URL + "/live/liveinfo_add.html";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("lid", videoId + "");
        params.put("content", content);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String code = jsonObject.optString("code");
                    String msg = jsonObject.optString("msg");
                    if (TextUtils.equals(code, "0")) {
                        etComment.setText("");
                        smartRefreshLayout.autoRefresh(200);
                    }
                    ToastUtils.getInstance().showToast(getActivity(), msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                    ToastUtils.getInstance().showToast(getActivity(), "服务器异常~");
                }
            }

            @Override
            public void onFailure(String errorMsg) {
                ToastUtils.getInstance().showToast(getActivity(), "网络异常~");
            }
        });
    }
}
