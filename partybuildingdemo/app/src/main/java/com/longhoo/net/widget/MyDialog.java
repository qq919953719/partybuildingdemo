package com.longhoo.net.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.longhoo.net.R;
import com.longhoo.net.utils.DisplayUtil;


public class MyDialog extends Dialog {

	private Context context;
	private TextView title;
	private LinearLayout dialogBox, contView, dialogHeader, dialogFooter;
	public Button dialogOk, dialogCancle;
	public ListView dialogItems;
	public EditText dialogEdit;
	private LayoutInflater layoutInflater;
	private MyDialogClickListener myDialogClickListener;
	private MyDialogItemClickListener myDialogItemClickListener;

	public MyDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
		setContentView(R.layout.layout_my_dialog);

		initViews();
		initWidth();
	}

	/**
	 * 点击接口
	 * 
	 * @author Administrator
	 * 
	 */
	public interface MyDialogClickListener {
		/**
		 * 
		 * @param view
		 *            区分View
		 * @param dialogEdit
		 *            textview，edittext
		 */
		public void onClick(View view, TextView dialogEdit);
	}

	/**
	 * item点击接口
	 * 
	 * @author Administrator
	 * 
	 */
	public interface MyDialogItemClickListener {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id);
	}

	public void setMyDialogClickListener(MyDialogClickListener myDialogClickListener) {
		this.myDialogClickListener = myDialogClickListener;
	}

	public void setMyDialogItemClickListener(MyDialogItemClickListener listener) {
		this.myDialogItemClickListener = listener;
	}

	private void initViews() {
		setCanceledOnTouchOutside(true);
		title = (TextView) findViewById(R.id.dialog_title);
		dialogBox = (LinearLayout) findViewById(R.id.dialog_box);
		dialogHeader = (LinearLayout) findViewById(R.id.dialog_header);
		dialogFooter = (LinearLayout) findViewById(R.id.dialog_footer);
		contView = (LinearLayout) findViewById(R.id.dialog_cont);
		dialogOk = (Button) findViewById(R.id.dialog_ok);
		dialogCancle = (Button) findViewById(R.id.dialog_cancle);
		layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		dialogOk.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if(myDialogClickListener!=null)
					myDialogClickListener.onClick(v, dialogEdit);
			}
		});
		dialogCancle.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
				if(myDialogClickListener!=null)
				myDialogClickListener.onClick(v, dialogEdit);
			}
		});
	}

	private void initWidth() {
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
		int width = wm.getDefaultDisplay().getWidth();
		dialogBox.getLayoutParams().width = (int) (width - DisplayUtil.dp2px(context, 60));
		dialogBox.requestLayout();
	}

	public void setMyTitle(String str) {
		title.setText(str);
	}

	public void setMyView(View view) {
		contView.addView(view);
	}
	
	/**
	 * 确定按钮显示的文字
	 * @param text
	 */
	public void setDialogOkText(String text){
		dialogOk.setText(text);
	}
	/**
	 * 取消按钮显示的文字
	 * @param text
	 */
	public void setDialogCancelText(String text){
		dialogCancle.setText(text);
	}
	/**
	 * 设置默认文字提示
	 * 
	 * @param string
	 *            提示内容
	 */
	public void setMyText(String string) {
		dialogHeader.setVisibility(View.GONE);
		View view = layoutInflater.inflate(R.layout.layout_dialog_text, null);
		TextView dialogText = (TextView) view.findViewById(R.id.dialog_text);
		dialogText.setText(string);
		contView.addView(view);
	}
	
	public void setMyText(String string, boolean showTitle){
		if(showTitle){
			dialogHeader.setVisibility(View.VISIBLE);
		}else{
			dialogHeader.setVisibility(View.GONE);
		}
		View view = layoutInflater.inflate(R.layout.layout_dialog_text, null);
		TextView dialogText = (TextView) view.findViewById(R.id.dialog_text);
		dialogText.setText(string);
		contView.addView(view);
	}

	/**
	 * 设置编辑框
	 * @param content
     */
	public void setMyEditer(String content) {
		View view = layoutInflater.inflate(R.layout.layout_dialog_edit, null);
		dialogEdit = (EditText) view.findViewById(R.id.dialog_edit);
		dialogEdit.setText(content);
		dialogEdit.setSelection(content.length());// 将光标移到最后
		//dialogEdit.addTextChangedListener(textWatcher);
		contView.addView(view);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
	}

	/**
	 * 设置列表显示
	 * 
	 * @param strArr
	 *            列表内容
	 */
	public void setMyItems(String[] strArr) {
		dialogHeader.setVisibility(View.GONE);
		dialogFooter.setVisibility(View.GONE);

		dialogItems = new ListView(context);
		dialogItems.setDivider(context.getResources().getDrawable(R.color.dialog_pressed));
		dialogItems.setDividerHeight(1);
		dialogItems.setAdapter(new ArrayAdapter<String>(context, R.layout.layout_dialog_item, strArr));
		contView.addView(dialogItems);

		dialogItems.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				dismiss();
				if(myDialogItemClickListener!=null){
					myDialogItemClickListener.onItemClick(parent, view, position, id);
				}		
			}
		});
	}
}