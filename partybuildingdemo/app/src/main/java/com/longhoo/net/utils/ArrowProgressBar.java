package com.longhoo.net.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.longhoo.net.R;


public class ArrowProgressBar extends RelativeLayout {
	public int Iperten=0;
	public ArrowProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initArrowProgressBar(context);
	}

	public ArrowProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		initArrowProgressBar(context);
	}

	public ArrowProgressBar(Context context) {
		super(context);
		initArrowProgressBar(context);
	}

	public void initArrowProgressBar(Context context) {
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		View view = layoutInflater.inflate(R.layout.arrow_progress_bar_layout,
				null);

		mProgressBar = (ProgressBar) view.findViewById(R.id.downloadProgressId);
		mProgressTxt = (TextView) view.findViewById(R.id.progressTxtId);
		mArrowImg = (ImageView) view.findViewById(R.id.arrowImgId);
		mArrowImg.setVisibility(ImageView.GONE);
		mProgressTxt.setVisibility(View.GONE);
		addView(view);
	}

	public void setProgress(int progress) {
		if (progress < 100.0f) {
			LayoutParams arrowParams = (LayoutParams) mArrowImg
					.getLayoutParams();
			float leftPosition = ((mProgressBar.getWidth() / PROGRESS_MAX) * (progress - 6))
					+ mProgressBar.getLeft();
			arrowParams.leftMargin = (int) Math.ceil(leftPosition);

			mArrowImg.setLayoutParams(arrowParams);
		} else {
			mArrowImg.setVisibility(ImageView.VISIBLE);
			// mArrowImg.setPadding(10, 0, 0, 0);
		}

		mProgressBar.setProgress(progress);
		Iperten=progress;
		mProgressTxt.setText(progress + "%");
	}

	private ProgressBar mProgressBar = null;
	private TextView mProgressTxt = null;
	private ImageView mArrowImg = null;
	private static final float PROGRESS_MAX = 105.0f;
}
