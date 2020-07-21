package com.longhoo.net.study.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.longhoo.net.R;
import com.longhoo.net.manageservice.ui.OpenFileActivity;
import com.longhoo.net.widget.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 党课展示/视频会议简介
 */
public class VideoIntroduceFragment extends BaseFragment {

    @BindView(R.id.wb_content)
    WebView wbContent;
    Unbinder unbinder;
    private String content;

    public static VideoIntroduceFragment newInstance(String content) {
        VideoIntroduceFragment fragment = new VideoIntroduceFragment();
        Bundle args = new Bundle();
        args.putString("content", content);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getArguments() != null) {
            content = getArguments().getString("content");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_video_introduce;
    }

    @Override
    protected void initView(View view) {
        WebSettings webSettings = wbContent.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setJavaScriptEnabled(true);
        wbContent.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });
        wbContent.loadData(content, "text/html; charset=UTF-8", null);
        wbContent.setWebViewClient(new WebViewClient() {
            @Override
            // 在点击请求的是链接是才会调用，重写此方法返回true表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边。这个函数我们可以做很多操作，比如我们读取到某些特殊的URL，于是就可以不打开地址，取消这个操作，进行预先定义的其他操作，这对一个程序是非常必要的。
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 判断url链接中是否含有某个字段，如果有就执行指定的跳转（不执行跳转url链接），如果没有就加载url链接

                // 文本文件
//                txt("txt"),
//                        // pdf文件
//                        pdf("pdf"),
//                        // doc文件
//                        doc("doc", "dot", "docx", "dotx", "docm", "dotm"),
//                        // ppt文件
//                        ppt("ppt", "pot", "pps", "ppa", "pptx", "ppsx", "potx", "ppam", "pptm", "potm", "ppsm"),
//                        // excel文件
//                        xls("xls", "xlt", "xla", "xlsx", "xltx", "xlsm", "xltm", "xlam", "xlsb");xlsx
//

                if (url.contains(".doc") || url.contains(".docx") || url.contains(".ppt") || url.contains(".xlsx") || url.contains(".xls") || url.contains(".xlsm") || url.contains(".pdf")) {
                    String timeName = "";
                    String[] strs = url.split("/");
                    timeName = strs[strs.length-1];
                    Intent intent = new Intent(getActivity(), OpenFileActivity.class);
                    intent.putExtra("file_path", url);
                    intent.putExtra("file_name", timeName);
                    intent.putExtra("file_size", "未知");
                    startActivity(intent);
                    return true;
                } else {
                    return false;
                }
            }
        });

        wbContent.setVisibility(View.VISIBLE);
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
