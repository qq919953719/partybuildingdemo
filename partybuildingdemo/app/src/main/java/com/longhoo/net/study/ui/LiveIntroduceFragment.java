package com.longhoo.net.study.ui;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.widget.base.BaseLazyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class LiveIntroduceFragment extends BaseLazyFragment {
    private static final String LIVE_DESCRIPTION = "live_description";
    @BindView(R.id.tv_des)
    TextView tvDes;
    Unbinder unbinder;
    private String description = "";

    public static LiveIntroduceFragment newInstance(String description) {
        LiveIntroduceFragment fragment = new LiveIntroduceFragment();
        Bundle args = new Bundle();
        args.putString(LIVE_DESCRIPTION, description);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_live_introduce;
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initViews(View view) {
        if (getArguments() != null) {
            description = getArguments().getString(LIVE_DESCRIPTION);
        }
        tvDes.setText(Html.fromHtml(description+""));
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected long setLoadInterval() {
        return 0;
    }
}
