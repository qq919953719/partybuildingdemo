package com.longhoo.net.study.ui;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.BackgroundColorSpan;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.partyaffairs.bean.QuestionBean;
import com.longhoo.net.utils.MessageEvent;
import com.longhoo.net.utils.ULog;
import com.longhoo.net.widget.base.BaseLazyFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class QuestionFragment extends BaseLazyFragment {

    private static final String QUESTION_ORDER_NUMBER = "question_order_number";
    private static final String QUESTION_BEAN = "question_bean";
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.container)
    LinearLayout container;
    private QuestionBean.DataBean.ListsBean mQuestionBean;
    private int mOrderNumber = 0;
    private String[] selectionList;

    public static QuestionFragment newInstance(int orderNumber, QuestionBean.DataBean.ListsBean mQuestionBean) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle args = new Bundle();
        args.putInt(QUESTION_ORDER_NUMBER, orderNumber);
        args.putSerializable(QUESTION_BEAN, mQuestionBean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentId() {
        return R.layout.fragment_question;
    }

    @Override
    protected void onLazyLoad() {
        //ULog.e("ck",mOrderNumber+":order");
        //tvTitle.setText(mOrderNumber + "、" + mQuestionBean.getName());
    }

    @Override
    protected void initViews(View view) {
        if (getArguments() != null) {
            mOrderNumber = getArguments().getInt(QUESTION_ORDER_NUMBER);
            mQuestionBean = (QuestionBean.DataBean.ListsBean) getArguments().getSerializable(QUESTION_BEAN);
        }
        if (mQuestionBean != null) {
            tvTitle.setText(mOrderNumber + "、" + mQuestionBean.getName());
            String[] tempList = mQuestionBean.getSelections().split("\\|");
            //去掉空格
            if (tempList != null) {
                selectionList = new String[tempList.length];
                for (int i = 0; i < tempList.length; i++) {
                    selectionList[i] = tempList[i].trim();
                }
            }
            int type = mQuestionBean.getType();
            if (type==1) {  //单选
                initRadioGroup();
            } else if (type==2) {  //多选
                initCheckBox();
            }

        }

    }

    //    private void setSpanText(String textTag){
//        try {
//            String text = mOrderNumber + "、" + mQuestionBean.getName()+"  "+textTag+" ";
//            ULog.e("ck",text);
//            SpannableString spanText = new SpannableString(text);
//            spanText.setSpan(new TextAppearanceSpan(getActivity(), R.style.colorSpanText_10), text.length()-4, text.length(),
//                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//            spanText.setSpan(new BackgroundColorSpan(ContextCompat.getColor(getActivity(),R.color.colorPrimary)), text.length()-4, text.length(),
//                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
//            tvTitle.setText(spanText);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
    private void setSpanText(String textTag) {
        try {
            String text ="  "+textTag +"  "+mOrderNumber + "、"+ mQuestionBean.getName();
            ULog.e("ck", text);
            SpannableString spanText = new SpannableString(text);
            spanText.setSpan(new TextAppearanceSpan(getActivity(), R.style.colorSpanText_10), 1, 5,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            // spanText.setSpan(new VerticalCenterSpan(10), 1, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            spanText.setSpan(new BackgroundColorSpan(ContextCompat.getColor(getActivity(), R.color.colorPrimary)), 1, 5,
                    Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
            tvTitle.setText(spanText);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initRadioGroup() {
        setSpanText("单选");
        if (selectionList == null) {
            return;
        }
        container.removeAllViews();
        LinearLayout.LayoutParams flp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RadioGroup radioGroup = new RadioGroup(getActivity());
        int size = selectionList.length;
        for (int i = 0; i < size; i++) {
            final int index = i;
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_radio_button, null);
            RadioButton radioButton = (RadioButton) view.findViewById(R.id.radio_button);
            RadioGroup.LayoutParams lp = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            radioButton.setLayoutParams(lp);
            radioButton.setId(index);
            radioButton.setText(selectionList[i].trim());
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //ToastUtils.getInstance().showToast(getActivity(), "this is radioButton  " + index);
                    MessageEvent event = new MessageEvent();
                    event.message = String.valueOf(index + 1);
                    EventBus.getDefault().post(event);
                }
            });
            radioGroup.addView(view);
        }
        container.addView(radioGroup);
    }

    private List<String> values = new ArrayList<>();

    private void initCheckBox() {
        setSpanText("多选");
        if (selectionList == null) {
            return;
        }
        container.removeAllViews();
        int size = selectionList.length;
        for (int i = 0; i < size; i++) {
            final int index = i;
            final View view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_checkbox, null);
            CheckBox checkBox = (CheckBox) view.findViewById(R.id.check_box);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            checkBox.setLayoutParams(lp);
            checkBox.setId(index);
            checkBox.setText(selectionList[i].trim());
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    String value = String.valueOf(index + 1);
                    if (isChecked) {
                        if (!values.contains(value)) {
                            values.add(value);
                        }
                    } else {
                        if (values.contains(value)) {
                            values.remove(value);
                        }
                    }
                    Collections.sort(values);
                    String message = "";
                    for (String string : values) {
                        message += string;
                    }
//                    if (message.length() > 0 && message.contains("|")) {
//                        message = message.substring(0, message.lastIndexOf("|"));
//                    }
                    MessageEvent event = new MessageEvent();
                    event.message = message;
                    EventBus.getDefault().post(event);
                }
            });
            container.addView(view);
        }
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    protected long setLoadInterval() {
        return 0;
    }
}
