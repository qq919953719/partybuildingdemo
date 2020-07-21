package com.longhoo.net.supervision.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.longhoo.net.R;
import com.longhoo.net.supervision.bean.SecretReportContentBean;
import com.longhoo.net.utils.Consts;
import com.longhoo.net.utils.EnterCheckUtil;
import com.longhoo.net.utils.ToastUtils;
import com.longhoo.net.utils.httprequest.OkHttpCallback;
import com.longhoo.net.utils.httprequest.OkHttpUtil;
import com.longhoo.net.widget.HeaderAndFooterWrapper;
import com.longhoo.net.widget.base.BaseActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 书记述职内容
 */
public class SecretReportContentActivity extends BaseActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private String sid;
    private List<SecretReportContentBean.DataBeanX.DataBean.PutDataBean> dataList = new ArrayList<>();
    private HeaderAndFooterWrapper adapterWrapper;
    private MyAdapter adapter;
    //头部
    private TextView tvMainTitle;
    private TextView tvMainTime;
    private TextView tvSETime;
    private TextView tvReportAddress;
    private TextView tvReportType;
    private WebView tvReportRequire;

    public static void goTo(Context context, String sid) {
        Intent intent = new Intent(context, SecretReportContentActivity.class);
        intent.putExtra("sid", sid);
        context.startActivity(intent);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_secret_report_content;
    }

    @Override
    protected void initViews() {
        if (getIntent() != null) {
            sid = getIntent().getStringExtra("sid");
        }
        adapter = new MyAdapter(this);
        adapterWrapper = new HeaderAndFooterWrapper(adapter);
        View header = LayoutInflater.from(this).inflate(R.layout.layout_secret_report_content_header, null);
        tvMainTitle = header.findViewById(R.id.tv_contenttitle);
        tvMainTime = header.findViewById(R.id.tv_time);
        tvSETime = header.findViewById(R.id.tv_s_e_time);
        tvReportAddress = header.findViewById(R.id.tv_report_address);
        tvReportType = header.findViewById(R.id.tv_report_type);
        tvReportRequire = header.findViewById(R.id.tv_report_require);
        adapterWrapper.addHeaderView(header);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapterWrapper);
        WebSettings webSettings = tvReportRequire.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        tvReportRequire.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        getData();
    }

    @Override
    protected void initToolbar() {
        tvTitle.setText("书记述职");
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.left_arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void getData() {
        String url = Consts.BASE_URL + "/Application/secretaryDutyDetails";
        Map<String, String> params = new HashMap<>();
        params.put("token", EnterCheckUtil.getInstance().getUid_Token()[1]);
        params.put("sid", sid);
        OkHttpUtil.getInstance().doAsyncPost(url, params, new OkHttpCallback() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                String msg = "";
                try {
                    SecretReportContentBean bean = gson.fromJson(response, SecretReportContentBean.class);
                    String code = bean.getCode();
                    msg = bean.getMsg();
                    if (!TextUtils.equals(code, "0")) {
                        ToastUtils.getInstance().showToast(SecretReportContentActivity.this, msg);
                        return;
                    }
                    tvMainTitle.setText(bean.getData().getData().getTitle());
                    tvMainTime.setText(bean.getData().getData().getTime());
                    tvSETime.setText(bean.getData().getData().getStarttime() + "-" + bean.getData().getData().getEndtime());
                    tvReportAddress.setText(bean.getData().getData().getAddress());
                    tvReportType.setText(bean.getData().getData().getTypename());
                    tvReportRequire.loadData(bean.getData().getData().getContent()+"", "text/html; charset=UTF-8", null);
                    List<SecretReportContentBean.DataBeanX.DataBean.PutDataBean> list = bean.getData().getData().getPut_data();
                    dataList.clear();
                    if (list != null && list.size() > 0) {
                        dataList.addAll(list);
                    }
                    adapterWrapper.notifyDataSetChanged();
                } catch (JsonSyntaxException e) {
                    ToastUtils.getInstance().showToast(SecretReportContentActivity.this, "服务器异常~");
                    e.printStackTrace();
                }
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(String errorMsg) {
                recyclerView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
                ToastUtils.getInstance().showToast(SecretReportContentActivity.this, "网络异常~");
            }
        });
    }

    public class MyAdapter extends RecyclerView.Adapter {
        private Context context;

        public MyAdapter(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_secret_report_content, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
            if (holder instanceof MyViewHolder) {
                ((MyViewHolder) holder).tvName.setText(dataList.get(position).getRealname());
                ((MyViewHolder) holder).tvWatch.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SecretReportDetailActivity.goTo(context,dataList.get(position).getId()+"");
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_name)
            TextView tvName;
            @BindView(R.id.tv_watch)
            TextView tvWatch;

            public MyViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }
        }
    }
}
