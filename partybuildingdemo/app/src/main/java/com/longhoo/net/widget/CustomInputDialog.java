package com.longhoo.net.widget;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.utils.Utils;

import java.util.List;

public class CustomInputDialog extends Dialog implements Spinner.OnItemSelectedListener {
    private Context mContext;
    private TextView tvTitle;
    private EditText etContent;
    private Button btSubmit;
    private ImageView ivClose;


    public static String strContent = "";
    private List<String> list;
    private String title = "";
    private String reason = "";
    private OnSumbitListener mOnSumbitListener;

    protected CustomInputDialog(Context context) {
        super(context);
        mContext = context;
        Window window = getWindow();
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    public CustomInputDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public CustomInputDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_custom_input_dialog);
        tvTitle = findViewById(R.id.tv_title);
        etContent = findViewById(R.id.et_content);
        etContent.setText(strContent);
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                //text  输入框中改变后的字符串信息
                //start 输入框中改变后的字符串的起始位置
                //before 输入框中改变前的字符串的位置 默认为0
                //count 输入框中改变后的一共输入字符串的数量

            }

            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {
                //text  输入框中改变前的字符串信息
                //start 输入框中改变前的字符串的起始位置
                //count 输入框中改变前后的字符串改变数量一般为0
                //after 输入框中改变后的字符串与起始位置的偏移量

            }

            @Override
            public void afterTextChanged(Editable edit) {
                //edit  输入结束呈现在输入框中的信息
                strContent = etContent.getText().toString();
            }
        });
        btSubmit = findViewById(R.id.bt_sumbit);
        ivClose = findViewById(R.id.iv_close);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        tvTitle.setText(title);
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnSumbitListener != null) {
                    mOnSumbitListener.onSubmit();
                }
                //dismiss();
                Utils.showHideSoftInput(mContext, etContent, false);
            }
        });
    }

    public CustomInputDialog setList(List<String> list) {
        this.list = list;
        return this;
    }

    public CustomInputDialog setCustomTitle(String title) {
        this.title = title;
        return this;
    }

    public CustomInputDialog setCustomTitle(int resId) {
        this.title = mContext.getResources().getString(resId);
        return this;
    }

    public CustomInputDialog setOnSubmitListener(OnSumbitListener onSumbitListener) {
        mOnSumbitListener = onSumbitListener;
        return this;
    }

    public String getCustomContent() {
        String content = etContent.getText().toString().trim();
        strContent = etContent.getText().toString().trim();
        return content;
    }

    public String getCustomReason() {
        return reason;
    }

    public static CustomInputDialog Build(Context context) {
        return new CustomInputDialog(context);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if (i < 0 || i > list.size() - 1) {
            return;
        }
        reason = list.get(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnSumbitListener {
        void onSubmit();
    }
}
